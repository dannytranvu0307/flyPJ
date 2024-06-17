package com.example.flyPJ.Service;

import com.example.flyPJ.Entity.User;
import com.example.flyPJ.Repository.UserRepository;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;


    private String frontEndURL;

    @Override
    @Async
    public void sendRegistrationUserConfirm(String email, String token) {

        SimpleMailMessage message = new SimpleMailMessage();
        String fisrtName = email.split("\\.")[0];
        String confirmationUrl = "front end url"+"/verify/"+ token;
        message.setTo(email);
        message.setSubject("【重要】新規登録手続きのご案内");
        message.setText(fisrtName.toUpperCase() +"さん、　\r\n"
                + "\r\n"
                + "Vtesでは安全にアカウントをご利用いただくために、\r\n"
                + "ご登録いただいたメールアドレスの認証をお願いしております。\r\n"
                + "\r\n"
                + "このメールが到着してから30分以内に下記URLをクリックしてください。\r\n"
                + "\r\n"
                + "認証URL：\r\n"
                + confirmationUrl
                + "\r\n"
                + "\r\n"
                + "認証が完了しない場合、ホームページなどがご利用いただけませんので、\r\n"
                + "ご了承ください。\r\n"
                + "\r\n"
                + "＊メール到着後、30分以上経過した場合は、もう一度最初から手続きをお願い致します。\r\n"
                + "\r\n"
                + "引き続き、よろしくお願いします。\r\n"
                + "ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー\r\n"
                + "ご不明点は下記連絡先までお問い合わせください。\r\n"
                + "開発部 \r\n"
                + "新人2023 \r\n"
                + "Email: pilot@vtv.com.vn");

        mailSender.send(message);
        log.info("Account activity email sent to: {}", email);

    }

    @Override
    @Async
    public void sendResetPasswordViaEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        User user = userRepository.findByEmail(email).get();
        String confirmationUrl = "front end url"+"/confirmresetpassword/"+ token;
        message.setTo(email);
        message.setSubject("「VTES会員」パスワード再設定手順のご案内");
        message.setText(user.getFullName()+"さん、\r\n"
                + "\r\n"
                + "お疲れ様です。\r\n"
                + "\r\n"
                + "パスワード再設定手順をご案内いたします。\r\n"
                + "\r\n"
                + "このメールが到着してから３０分以内に下記URLをクリックしてください。\r\n"
                + "\r\n"
                + confirmationUrl
                + "\r\n　\r\n"
                + "入力画面が表示されますので、新しいパスワードを入力後、「保存」ボタンを押してください。\r\n"
                + "再設定の手続きが完了します。\r\n"
                + "\r\n"
                + "＊メール到着後、30分以上経過した場合は、もう一度最初から手続きをお願い致します。\r\n"
                + "ご不明な点がある場合は、お問い合わせください。\r\n"
                + "\r\n"
                + "引き続き、よろしくお願いします。\r\n"
                + "ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー\r\n"
                + "ご不明点は下記連絡先までお問い合わせください。\r\n"
                + "開発部　\r\n"
                + "新人2023　\r\n"
                + "Email: pilot@vtv.com.vn");

        mailSender.send(message);
        log.info("Reset password email sent to: {}", email);

    }

}