package org.motion.motion_api.application.services.observer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import org.motion.motion_api.domain.entities.pitstop.Gerente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


public class AccountCreationNotificationObserver implements Observer {


    @Override
    public void update(Object data) throws MessagingException {
        if (data instanceof AccountCreationData accountCreationData) {
            Gerente gerente = accountCreationData.getGerente();
            String senhaGerada = accountCreationData.getGeneratedPassword();

            String htmlBody = "<html>" +
                    "<head><link href='https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap' rel='stylesheet'></head>" +
                    "<body style='font-family: Roboto, sans-serif;'><h2>Olá! " + gerente.getNome() + "</h2>" +
                    "<p>Sua nova senha é: <strong>" + senhaGerada + "</strong></p>" +
                    "<p>Ela poderá ser utilizada no seu primeiro acesso</p>" +
                    "<p>Atenciosamente,</p>" +
                    "<p>A equipe motion</p>" +
                    "</body></html>";


            try {
                MimeMessage message = accountCreationData.emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setTo(gerente.getEmail());
                helper.setSubject("Sua nova senha");
                helper.setText(htmlBody, true); // Habilita o processamento de HTML
                accountCreationData.emailSender.send(message);
            }catch (MessagingException ex){
                throw new MessagingException("Erro ao enviar email de notificação de criação de conta");
            }
        }
    }


    @Data
    public static class AccountCreationData {
        private Gerente gerente;
        private String generatedPassword;
        private JavaMailSender emailSender;

        public AccountCreationData(Gerente gerente, String senhaGerada, JavaMailSender emailSender) {
            this.gerente = gerente;
            this.generatedPassword = senhaGerada;
            this.emailSender = emailSender;
        }
    }
}

