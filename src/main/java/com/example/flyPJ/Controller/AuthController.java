package com.example.flyPJ.Controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

//import com.example.flyPJ.Authenication.jwt.CookieUtils;
//import com.example.flyPJ.Authenication.jwt.JwtUtils;
import com.example.flyPJ.Authenication.jwt.CookieUtils;
import com.example.flyPJ.Authenication.jwt.JwtUtils;
import com.example.flyPJ.Authenication.service.RefreshTokenService;
import com.example.flyPJ.Authenication.service.UserDetailsImpl;
import com.example.flyPJ.DTO.ResponseData;
import com.example.flyPJ.Entity.RefreshToken;
import com.example.flyPJ.Entity.User;
import com.example.flyPJ.Exception.FlyException;
import com.example.flyPJ.Payload.LoginPayload;
import com.example.flyPJ.Payload.RegisterPayload;
import com.example.flyPJ.Service.UserServiceImpl;
import com.example.flyPJ.exception.AuthenticationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.flyPJ.Exception.UserException;
@RestController
@RequestMapping("/auth/")
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;
//
    @Autowired
    private CookieUtils cookieUtils;
    @GetMapping("/test")
    public String test(){
        return "messeage:hello world";
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterPayload payload) throws FlyException {
        User savedUser = userService.saveUser(payload);
        return ResponseEntity.ok()
                .body(ResponseData.builder()
                        .code("").type(ResponseData.ResponseType.INFO)
                        .message("Register successfull")
                        .build());

    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginPayload loginRequest, HttpServletResponse httpServletResponse) throws FlyException {

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        } catch (Exception e) {
            throw new AuthenticationFailedException(loginRequest.getEmail());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userService.isActiveUserAccount(userDetails.getEmail())) {
            throw new UserException("API001_ER02", "This account is not active yet");

        }

        String jwt = jwtUtils.generateJwtToken(userDetails);

        if (loginRequest.isRemember()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
            cookieUtils.createAccessTokenCookie(httpServletResponse, jwt);
            cookieUtils.createRefreshTokenCookie(httpServletResponse, refreshToken.getToken());
        } else {
            cookieUtils.createAccessTokenCookie(httpServletResponse, jwt);
        }
        log.info("{} was logined", userDetails.getEmail());

        return ResponseEntity.ok().body(ResponseData.builder()
                .code("")
                .type(ResponseData.ResponseType.INFO)
                .message("Authentication successfull")
                .build());

    }

//    @GetMapping("/refreshToken")
//    public ResponseEntity<?> refreshtoken(HttpServletRequest request, HttpServletResponse httpServletResponse) {
//        String requestRefreshToken = cookieUtils.getRefreshTokenFromCookie(request);
//        if(requestRefreshToken == null) {
//            return ResponseEntity.ok()
//                    .body(ResponseData.builder()
//                            .type(ResponseType.WARINING).code("")
//                            .message("refresh token is null")
//                            .build());
//        }
//        log.info("{} of access token was re-create");
//        return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUser).map(user -> {
//                    String token = jwtUtils.generateTokenFromEmail(user.getEmail());
//                    cookieUtils.createAccessTokenCookie(httpServletResponse, token);
//
//                    return ResponseEntity.ok().body(ResponseData.builder().type(ResponseType.INFO).code("")
//                            .message("Jwt Token recreated").build());
//
//                })
//                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
//    }



}
