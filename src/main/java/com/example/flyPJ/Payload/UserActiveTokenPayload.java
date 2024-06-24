package com.example.flyPJ.Payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserActiveTokenPayload {
    @NotBlank
    private String verifyCode;
}
