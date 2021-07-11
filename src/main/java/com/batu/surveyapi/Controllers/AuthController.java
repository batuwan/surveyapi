package com.batu.surveyapi.Controllers;

import com.batu.surveyapi.Data.Models.AuthRequest;
import com.batu.surveyapi.Data.Models.AuthToken;
import com.batu.surveyapi.Data.UserDetails.SurveyApiUserDetailsService;
import com.batu.surveyapi.Services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private TokenService tokenService;

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
}
