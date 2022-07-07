package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.config.MailConfig;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.service.api.MailService;
import ru.irlix.learnit.service.helper.UserHelper;

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

    private final UserHelper userHelper;
    final String text = "Для восстановления доступа к Вашей учетной записи скопируйте код:\n%s";
    private final MailConfig mailConfig;

    @Override
    @Transactional
    public void sendMessage(String email) {
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        String digit = String.format("%04d", number);
        UserData userData = userHelper.findUserByEmail(email);
        userData.setRestoreCode(digit);
        userHelper.saveUser(userData);
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", mailConfig.getHost());
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", mailConfig.getPort());
        props.setProperty("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.getSenderAddress(), mailConfig.getSenderPassword());
            }
        });
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailConfig.getSenderAddress()));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.setSubject("restore password");
            msg.setText(String.format(text, digit));
            Transport.send(msg);
            log.info("Email sent successfully");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
