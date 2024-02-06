package com.example.flyPJ.Service;

import com.example.flyPJ.Authenication.jwt.JwtUtils;
import com.example.flyPJ.Entity.User;
import com.example.flyPJ.Exception.UserException;
import com.example.flyPJ.Payload.RegisterPayload;
import com.example.flyPJ.Repository.UserRepository;
import com.example.flyPJ.Service.UserService;
import com.example.flyPJ.Exception.FlyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;



    public User saveUser(RegisterPayload payload) throws FlyException {
//        if (isActiveUserAccount(payload.getEmail())) {
//            log.info("{} is trying to register ", payload.getEmail());
//            throw new UserException("REGIS_ER_01", "This email has already been used");
//        }
//        if (isExistsUserOnCache(payload.getEmail())) {
//            log.info("{} is trying to register", payload.getEmail());
//            throw new UserException("API002_ER2", "Active email already sent to " + payload.getEmail());
//        }
        User preActiveUser = saveNewUser(payload);
        if (preActiveUser != null) {
//            restoreRegisterUser(preActiveUser);
            emailService.sendRegistrationUserConfirm(payload.getEmail(), preActiveUser.getVerifyCode());
        }
        System.out.print(payload);
        return preActiveUser;
    }

    @Override
    public User getUser()  {
        return null;
    }

    @Override
    public void activeUser()  {

    }

    @Override
    public User updateUser()  {
        return null;
    }

    @Override
    public void sendResetPasswordViaEmail()  {

    }

    @Override
    public void resetPassword()  {

    }

    public boolean isActiveUserAccount(String email) {
        return userRepository.findActiveUserByEmail(email) > 0;

    }

//    private boolean isExistsUserOnCache(String email) {
//        return redisTemplate.hasKey(email);
//    }
    private User saveNewUser(RegisterPayload payload) {
        User user = new User(payload.getFullName(), payload.getEmail(), encoder.encode(payload.getPassword()));
        user.setStatus((short) 0);
        String tokenActive = jwtUtils.generateTokenToActiveUser(payload.getEmail());
//        String tokenActive = "abdjkabdjabdjkabnasandkahdkankdankdankdanklanskdjkdnkas" ;
        user.setVerifyCode(tokenActive);
        user.setDeleteFlag(false);
        userRepository.save(user) ;
        log.info("New account {} has been registered", user.getEmail());
        log.info("{} of details has been restored to cache", user.getEmail());
        return user;
    }

//    private void restoreRegisterUser(User user) {
//        redisTemplate.opsForValue().set(user.getEmail(), null, Duration.ofMinutes(USER_CACHE_DURATION));
//        redisTemplate.opsForValue().set(user.getVerifyCode(), user, Duration.ofMinutes(USER_CACHE_DURATION));
//    }

}