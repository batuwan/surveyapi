package com.batu.surveyapi.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class HelloController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/hello")
    public ResponseEntity<String> Hello(){
        return ResponseEntity.ok("Hello");
    }
}
