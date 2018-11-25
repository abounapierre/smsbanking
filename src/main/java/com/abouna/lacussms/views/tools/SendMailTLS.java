/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views.tools;

/**
 *
 * @author SATELLITE
 */
import com.abouna.lacussms.config.SpringMainConfig;
import com.abouna.lacussms.entities.SentMail;
import com.abouna.lacussms.service.LacusSmsService;
import java.util.Date;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SendMailTLS {

   /* public static void main(String[] args) {

        final String username = "lacussms.app@gmail.com";
        final String password = "@dmin123!";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lacussms.appil@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("eabouna@gmail.com"));
            message.setSubject("Application en cours d'exécution");
            Date date = new Date();
            message.setText("Application tourne correctement,"
                    + "\n\n Lacus SMS, please CopyRight 2018 SunSoftware Fundation!"
                    + "\n\n " + "en date du " + date);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        
         ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMainConfig.class);
        LacusSmsService serviceManager = ctx.getBean(LacusSmsService.class);
        
        for (int i = 0; i < 10; i++) {
            Utils.enregistrerMail(serviceManager);
           
        }//getMailByDate(new Date())
        
        for(SentMail mail : serviceManager.getMailByDate(new Date())){
            System.out.println(mail.toString());
        }
        
    }*/
}
