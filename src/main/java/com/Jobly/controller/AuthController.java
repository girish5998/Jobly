package com.Jobly.controller;

import com.Jobly.enums.Role;
import com.Jobly.service.AuthService;
import com.Jobly.dto.LoginRequestDTO;
import com.Jobly.dto.LoginResponseDTO;
import com.Jobly.dto.RegisterRequestDTO;
import com.Jobly.dto.RegisterResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register/admin")
    public ResponseEntity<RegisterResponseDTO> registerAdmin(@RequestBody RegisterRequestDTO dto){
        return new ResponseEntity<>(authService.register(dto, Role.ADMIN),
                HttpStatus.CREATED);
    }

    @PostMapping("/register/owner")
    public ResponseEntity<RegisterResponseDTO> registerOwner(@RequestBody RegisterRequestDTO dto){
        return new ResponseEntity<>(authService.register(dto, Role.OWNER),
                HttpStatus.CREATED);
    }

    @PostMapping("/register/worker")
    public ResponseEntity<RegisterResponseDTO> registerWorker(@RequestBody RegisterRequestDTO dto){
        return new ResponseEntity<>(authService.register(dto, Role.WORKER),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }
}
