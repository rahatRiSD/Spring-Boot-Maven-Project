package com.javatechie.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class OttSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private final OneTimeTokenGenerationSuccessHandler redirectHandler = new RedirectOneTimeTokenGenerationSuccessHandler("/ott/sent");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        // generate and send magic link (One time token)
        UriComponentsBuilder token = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .path("/login/ott")
                .queryParam("token", oneTimeToken.getTokenValue());

        String magicLink = token.toUriString();
        System.out.println("One time token : " + magicLink);

        sendOttNotification(oneTimeToken, magicLink);
        redirectHandler.handle(request, response, oneTimeToken);
    }

    private void sendOttNotification(OneTimeToken oneTimeToken, String magiclink) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Javatechie <" + sender + ">");
            message.setTo(getEmail().get(oneTimeToken.getUsername()));
            message.setSubject("One Time Token - Magic Link ");

            String messageBody = """
                     Hello %s,
                            \s
                     Use the following link to sign in to the application:
                            \s
                     %s
                            \s
                     This link is valid for a limited time. If you did not request this, please ignore this email.
                            \s
                    \s""".formatted(oneTimeToken.getUsername(), magiclink);

            message.setText(messageBody);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getEmail() {
        Map<String, String> emailMap = new HashMap<>();
        emailMap.put("javatechie", "javatechie4u@gmail.com");
        return emailMap;
    }
}
