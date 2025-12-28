package com.example.demo.services.impl;

import com.example.demo.dtos.*;
import com.example.demo.entities.Permission;
import com.example.demo.entities.User;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(username);

        if(Objects.nonNull(user)){
            return user;
        }

        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            return user;
        }

        return null;
    }



    public UserResponseDto signUp(UserRequestDto userRequestDto){
        User user = userMapper.toEntity(userRequestDto);

        User u = userRepository.findByEmail(user.getEmail());

        if(Objects.isNull(u)){
            List<Permission> roles = new ArrayList<>();
            Permission simplePermission = permissionRepository.findByPermission("PATIENT");
            roles.add(simplePermission);

            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setFullName(user.getFullName());
            newUser.setRoles(roles);

            userRepository.save(newUser);

            return userMapper.toDto(newUser);
        }
        return null;
    }


    @Override
    public Boolean updatePassword(ChangePasswordRequestDto passwords){
        User currentUser = getCurrentUser();

        if (Objects.nonNull(currentUser)) {
            if (passwordEncoder.matches(passwords.getOldPassword(), currentUser.getPassword())) {
                currentUser.setPassword(passwordEncoder.encode(passwords.getNewPassword()));
                userRepository.save(currentUser);
                return true;
            }
            return false;
        }
        return null;
    }

    @Override
    public UserResponseDto updateProfile(UserRequestDto userRequestDto){
        User currentUser = getCurrentUser();

        if(Objects.isNull(currentUser)){
            return null;
        }

        User updated = userMapper.toEntity(userRequestDto);
        if (updated.getEmail() != null) {
            currentUser.setEmail(updated.getEmail());
        }
        if(updated.getFullName()!=null){
            currentUser.setFullName(updated.getFullName());
        }

        userRepository.save(currentUser);
        return userMapper.toDto(currentUser);
    }

    @Override
    public UserResponseDto createUserByAdmin(AdminCreateUserRequestDto userRequestDto){
        User newUser = new User();
        User check = userRepository.findByEmail(userRequestDto.getEmail());

        List<Permission> roles = new ArrayList<>();
        roles.add(permissionRepository.findByPermission(userRequestDto.getRole()));

        if(check==null){
            newUser.setFullName(userRequestDto.getFullName());
            newUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            newUser.setEmail(userRequestDto.getEmail());
            newUser.setRoles(roles);

            userRepository.save(newUser);
            return userMapper.toDto(newUser);
        }
        return null;
    }

    @Override
    public Boolean deleteUserByAdmin(Long id){
        userRepository.deleteById(id);

        User check = userRepository.findById(id).orElse(null);
        return Objects.isNull(check);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponseDto> dtoUsers = new ArrayList<>();
        users.forEach(user -> dtoUsers.add(userMapper.toDto(user)));

        return dtoUsers;
    }

    @Override
    public UserResponseDto updateUserByAdmin(Long id, UserRequestDto userRequestDto){
        User user = userRepository.findById(id).orElse(null);

        if(Objects.isNull(user)){
            return null;
        }

        User updated = userMapper.toEntity(userRequestDto);
        if (updated.getEmail() != null) {
            user.setEmail(updated.getEmail());
        }
        if(updated.getFullName()!=null){
            user.setFullName(updated.getFullName());
        }
        if(updated.getRoles()!=null){
            user.setRoles(updated.getRoles());
        }

        userRepository.save(user);
        return userMapper.toDto(user);
    }


    @Override
    public UserResponseDto assignRoleByAdmin(Long userId, Long roleId){
        User user = userRepository.findById(userId).orElse(null);
        Permission role = permissionRepository.findById(roleId).orElse(null);

        if (Objects.nonNull(user) && Objects.nonNull(role)) {
            user.getRoles().add(role);
            userRepository.save(user);

            return userMapper.toDto(user);
        }
        return null;
    }


    @Override
    public UserResponseDto removeRoleByAdmin(Long userId, Long roleId){
        User user = userRepository.findById(userId).orElse(null);
        Permission role = permissionRepository.findById(roleId).orElse(null);

        if (Objects.nonNull(user) && Objects.nonNull(role)) {
            user.getRoles().remove(role);
            userRepository.save(user);

            return userMapper.toDto(user);
        }
        return null;
    }



}
