package com.example.flyPJ.Exception;
import com.example.flyPJ.Exception.FlyException;
public class UserException extends FlyException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, String message) {
        super(code, message);
    }
}
