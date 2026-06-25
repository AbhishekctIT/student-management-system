package com.platformcommons.student_management_system.studentms.service;

import com.platformcommons.student_management_system.studentms.dto.request.LoginRequest;
import com.platformcommons.student_management_system.studentms.dto.response.LoginResponse;
import com.platformcommons.student_management_system.studentms.entity.Admin;
import com.platformcommons.student_management_system.studentms.exception.InvalidCredentialsException;
import com.platformcommons.student_management_system.studentms.repository.AdminRepository;
import com.platformcommons.student_management_system.studentms.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public LoginResponse login(LoginRequest request) {
        log.info("Login attempt for user: {}", request.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);

            return LoginResponse.builder()
                    .token(token)
                    .username(request.getUsername())
                    .role("ADMIN")
                    .expiresIn(tokenProvider.getTokenExpiration())
                    .build();

        } catch (Exception e) {
            log.error("Login failed for user: {}", request.getUsername(), e);
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    public boolean createAdmin(String username, String email, String password) {
        if (adminRepository.findByUsername(username).isPresent()) {
            return false;
        }

        Admin admin = Admin.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        adminRepository.save(admin);
        log.info("Admin created: {}", username);
        return true;
    }
}
