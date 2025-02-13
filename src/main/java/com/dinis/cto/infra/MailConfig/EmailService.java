package com.dinis.cto.infra.MailConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl; // Importe JavaMailSenderImpl
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarEmail(String para, String assunto, String mensagem) {

       try {
           SimpleMailMessage email = new SimpleMailMessage();
           email.setTo(para);
           email.setSubject(assunto);
           email.setText(mensagem);
           email.setFrom(remetente);
           mailSender.send(email);
           return "Email enviado com sucesso!";
       }catch (Exception e) {
           return "Erro ao tentar enviar email!";
       }
    }
}