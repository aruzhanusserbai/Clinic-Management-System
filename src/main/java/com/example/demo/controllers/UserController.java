package com.example.demo.controllers;

import com.example.demo.dtos.*;
import com.example.demo.services.DepartmentService;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DepartmentService departmentService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto registeredUser = userService.signUp(userRequestDto);

        if (Objects.isNull(registeredUser)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return ResponseEntity.ok(registeredUser);
        }
    }


    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequestDto passwords) {
        Boolean check = userService.updatePassword(passwords);

        if (check) {
            return ResponseEntity.ok("Password changed successfully!");
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    @PatchMapping("/change-profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changeProfile(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto changedProfile = userService.updateProfile(userRequestDto);

        if (Objects.isNull(changedProfile)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(changedProfile);
        }
    }


    @PostMapping("/admin/create-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody AdminCreateUserRequestDto userRequestDto) {
        UserResponseDto newUser = userService.createUserByAdmin(userRequestDto);

        if (Objects.nonNull(newUser)) {
            return ResponseEntity.ok(newUser);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @DeleteMapping("/admin/delete-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestBody UserRequestDto userRequestDto) {
        Boolean check = userService.deleteUserByAdmin(userRequestDto.getId());

        if (check) {
            return ResponseEntity.ok("User Deletes Successfully!");
        } else {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }


    @GetMapping("/admin/get-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getUsers() {
        List<UserResponseDto> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(users);
        }
    }


    @PatchMapping("/admin/update-user/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.updateUserByAdmin(id, userRequestDto);

        if (Objects.isNull(updatedUser)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(updatedUser);
        }
    }


    @PostMapping("/admin/assign-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> assignRole(@RequestBody AssignRemoveRoleByAdmin assignRoleToUser) {
        UserResponseDto user = userService.assignRoleByAdmin(assignRoleToUser.getUserId(), assignRoleToUser.getRoleId());

        if (Objects.nonNull(user)) {
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/admin/remove-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> removeRole(@RequestBody AssignRemoveRoleByAdmin assignRoleToUser){
        UserResponseDto user = userService.removeRoleByAdmin(assignRoleToUser.getUserId(), assignRoleToUser.getRoleId());

        if(Objects.nonNull(user)){
            return ResponseEntity.ok(user);
        }else{
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}