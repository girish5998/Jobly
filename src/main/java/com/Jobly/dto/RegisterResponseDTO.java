package com.Jobly.dto;

import com.Jobly.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {
    private long id;
    private String name;
    private String email;
    private Role role;
    private String message;
}
