package com.example.flyPJ.Service;

import com.example.flyPJ.Entity.User;
import com.example.flyPJ.Exception.FlyException;

public interface UserService {

    User getUser() throws FlyException;

    void activeUser() throws FlyException;

    User updateUser() throws FlyException;

    void sendResetPasswordViaEmail() throws FlyException;

    void resetPassword() throws FlyException ;
}
