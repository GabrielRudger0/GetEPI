package com.senai.GetEPI.DTOs;

import lombok.Data;

@Data
public class LoginDTO {

    private String email;

    private String senha;

    public LoginDTO() {
    }
}
