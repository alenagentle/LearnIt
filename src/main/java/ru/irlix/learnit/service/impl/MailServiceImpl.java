package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.irlix.learnit.security.util.JwtUtils;
import ru.irlix.learnit.service.api.MailService;
import ru.irlix.learnit.util.AESUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JwtUtils jwtUtils;
    final String secretKey = "QRWDCHGSgwuy2bu728674q";
    final String text = "Для восстановления доступа к Вашей учетной записи скопируйте код: \n %s";

    @Override
    public void sendMessage(String mail) {
        System.out.println("sendMessage()...");

//        String link = "http://localhost:8080/api/restore/";

//        String encodedString = Base64.getEncoder().withoutPadding().encodeToString(mail.getBytes());
//        System.out.println("encodedString  " + encodedString);
//        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedString);
//        String decodedUrl = new String(decodedBytes);
//        System.out.println("decodedUrl  " + decodedUrl);

        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String digit = String.format("%06d", number);
        System.out.println("digit = " + digit);

//        String token = AESUtils.encryptt(mail, secretKey);
//        System.out.println("encrypted token " + token);
//        String decryptedMail = AESUtils.decryptt(token, secretKey);
//        System.out.println("decryptedMail = " + decryptedMail);


        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.mail.ru");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("learnit.restore@gmail.com", "admin12#");
                return new PasswordAuthentication("kiseleva_elena92@mail.ru", "oXo5a8kHDygbAT94mrxM");
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
//            msg.setFrom(new InternetAddress("learnit.restore@gmail.com"));
            msg.setFrom(new InternetAddress("kiseleva_elena92@mail.ru"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            msg.setSubject("restore password");
            msg.setText(String.format(text,  digit));
            Transport.send(msg);
            System.out.println("Email Sent successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
//        }
    }
}
