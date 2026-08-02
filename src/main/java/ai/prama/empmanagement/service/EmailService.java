package ai.prama.empmanagement.service;

public interface EmailService {

    void sendMail(String to, String subject, String htmlBody);
}
