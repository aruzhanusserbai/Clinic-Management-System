package com.example.demo.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/registration").permitAll()
                        .requestMatchers("/test").authenticated()
                        .requestMatchers("/change-password", "/change-profile").authenticated()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/get-my-appointments").hasAuthority("PATIENT")
                        .requestMatchers("/get-doctor-appointments").hasAuthority("DOCTOR")
                        .requestMatchers("/get-all-appointments").hasAuthority("ADMIN")
                        .requestMatchers("/patient/**").hasAuthority("PATIENT")
                        .requestMatchers("/doctor/**").hasAuthority("DOCTOR")
                        .requestMatchers("/get-my-payments").hasAuthority("PATIENT")
                        .requestMatchers("/get-all-payments").hasAuthority("ADMIN")
                        .requestMatchers("/get-my-medRecords").hasAuthority("PATIENT")
                        .requestMatchers("/get-doctor-medRecords").hasAuthority("DOCTOR")
                        .requestMatchers("/get-all-medRecords").hasAuthority("ADMIN")
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
