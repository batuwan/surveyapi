package com.batu.surveyapi.Controllers;

import com.batu.surveyapi.Core.Entities.User;
import com.batu.surveyapi.Data.Models.AdminCreateUserDto;
import com.batu.surveyapi.Data.Models.AuthRequest;
import com.batu.surveyapi.Data.Models.AuthToken;
import com.batu.surveyapi.Data.Models.UserDto;
import com.batu.surveyapi.Data.UserDetails.SurveyApiUserDetailsService;
import com.batu.surveyapi.Services.TokenService;
import com.batu.surveyapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SurveyApiUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> creteToken(@RequestBody AuthRequest authRequest) throws Exception {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenService.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto){
        userService.register(userDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/createUserAsAdmin")
    public ResponseEntity<?> createUser(@RequestBody AdminCreateUserDto user){
        userService.createUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable long userId){

        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
