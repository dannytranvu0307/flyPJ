package com.example.flyPJ.Service;

public interface EmailService {
    void sendRegistrationUserConfirm(String email, String token);

    void sendResetPasswordViaEmail(String email, String token);
}

