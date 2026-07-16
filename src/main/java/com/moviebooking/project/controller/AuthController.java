package com.moviebooking.project.controller;



import com.moviebooking.project.security.request.LoginRequest;
import com.moviebooking.project.security.request.SignupRequest;
import com.moviebooking.project.security.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        return authService.signin(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        return authService.signup(signupRequest);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/signout")
    public ResponseEntity<?> signout(){

        return authService.logout();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {

        return authService.getUserDetails(authentication);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/promoteUser")
    public ResponseEntity<?> makeUserTheatreManager(Authentication authentication) {
        String promoted=authService.promoteUser(userId,adminId);
    }


}
