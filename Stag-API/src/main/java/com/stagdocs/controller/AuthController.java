package com.stagdocs.controller;

import com.stagdocs.model.User;
import com.stagdocs.service.UserDetailsAuthorizeService;
import com.stagdocs.service.UserService;
import com.stagdocs.utils.AuthRequest;
import com.stagdocs.utils.AuthResponse;
import com.stagdocs.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsAuthorizeService userDetailsAuthorizeService;
    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          UserDetailsAuthorizeService userDetailsAuthorizeService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsAuthorizeService = userDetailsAuthorizeService;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestBody AuthRequest request) throws Exception {
        LOGGER.info("Authenticating user: {}", request.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            LOGGER.error("Authentication failed for user: {}", request.getUsername(), e);
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsAuthorizeService.loadUserByUsername(request.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        LOGGER.info("JWT Token generated: {}", jwt);

        return new AuthResponse(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (!userService.isUsernameAvailable(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}