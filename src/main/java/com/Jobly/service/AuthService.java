package com.Jobly.service;

import com.Jobly.entity.User;
import com.Jobly.enums.Role;
import com.Jobly.repository.UserRepository;
import com.Jobly.dto.LoginRequestDTO;
import com.Jobly.dto.LoginResponseDTO;
import com.Jobly.dto.RegisterRequestDTO;
import com.Jobly.dto.RegisterResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public RegisterResponseDTO register(RegisterRequestDTO dto, Role role) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setMobile(dto.getMobile());
        user.setAddress(dto.getAddress());
        user.setRole(role);

        if(role == Role.WORKER){
        user.setApproved(false);
        user.setAvailable(true);
    }
        User SavedUser = userRepository.save(user);
        return new RegisterResponseDTO(
                SavedUser.getId(),
                SavedUser.getName(),
                SavedUser.getEmail(),
                SavedUser.getRole(),
                role.name() + "Registered Successfully"
        );
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));

        if(!user.getPassword().equals(loginRequestDTO.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                "Login Successful"
        );
    }
}
