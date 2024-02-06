package com.example.flyPJ.Payload;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegisterPayload {
    @NotBlank
    @Size(min = 6, max = 128)
    private String fullName;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    private String password;

    public RegisterPayload() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}