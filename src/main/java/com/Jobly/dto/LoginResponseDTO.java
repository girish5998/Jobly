package com.Jobly.dto;

import com.Jobly.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private long id;
    private String name;
    private String email;
    private Role role;
    private String message;
}
