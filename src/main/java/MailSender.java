import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MailSender {
    private Properties props = new Properties();

    public MailSender() {
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
    }

    private static void initializeMail() {
        MailSender mailer = new MailSender();
        StringBuilder msg = null;
        String pathname = "src/URBAN LANDLORDS.htm";
        File file = new File(pathname);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int i = 0;
            while (true) {
                try {

                    if (!((i = fileInputStream.read()) != -1)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.print((char) i);
                msg = (msg == null ? new StringBuilder("") : msg).append((char) i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String type = "text/html";
        String subject = "Hello";
        String to = "mwendemich@gmail.com";
        String password = "Kapsabet010";
        String from = "muemasnyamai@gmail.com";
        mailer.send(from, password, to, subject, msg == null ? null : msg.toString(), type);

    }

    private void send(final String from, final String password, String to, String sub, String msg, String type) {
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            message.setContent(
                    msg,
                    type);

            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
