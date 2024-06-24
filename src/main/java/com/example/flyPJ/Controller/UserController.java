package com.example.flyPJ.Controller;

import com.example.flyPJ.DTO.ResponseData;
import com.example.flyPJ.Payload.UserActiveTokenPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.flyPJ.Service.UserServiceImpl;

import javax.validation.Valid;
import com.example.flyPJ.Exception.FlyException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/active")
    public ResponseEntity<?> activeUser(@Valid @RequestBody UserActiveTokenPayload payload) throws FlyException {
        userService.activeUser(payload.getVerifyCode());
        return ResponseEntity.ok().body(
                ResponseData.builder()
                        .type(ResponseData.ResponseType.INFO)
                        .code("")
                        .message("Account verify successfully")
                        .build());
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";

    }
}
