package com.example.flyPJ.Controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

//import com.example.flyPJ.Authenication.jwt.CookieUtils;
//import com.example.flyPJ.Authenication.jwt.JwtUtils;
import com.example.flyPJ.DTO.ResponseData;
import com.example.flyPJ.Entity.User;
import com.example.flyPJ.Exception.FlyException;
import com.example.flyPJ.Payload.LoginPayload;
import com.example.flyPJ.Payload.RegisterPayload;
import com.example.flyPJ.Service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fly")
@Slf4j
public class AuthController {
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

//    @Autowired
//    private JwtUtils jwtUtils;
//
////    @Autowired
////    private RefreshTokenService refreshTokenService;
//
//    @Autowired
//    private CookieUtils cookieUtils;



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterPayload payload) throws FlyException {
        User savedUser = userService.saveUser(payload);
        return ResponseEntity.ok()
                .body(ResponseData.builder()
                        .code("").type(ResponseData.ResponseType.INFO)
                        .message("Register successfull")
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
