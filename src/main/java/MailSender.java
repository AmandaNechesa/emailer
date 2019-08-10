import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MailSender {
    private Properties props = new Properties();
    String type;

    public MailSender(String provider) {
//for multiple providers
        if (Settings.userDetails.get("provider").equalsIgnoreCase("gmail")) {
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
        }
    }

    public MailSender() {

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

    }

    public void initializeMail(String to, String password, String from, String subject, String type, File attachment, File passed) {

        String pathname = null;
        if (attachment != null) {


            System.out.println("there is a file attachment");
            if (!Settings.mailDetails.get("txt").isEmpty()) {
                type = Settings.mailDetails.get("type");
                pathname = Settings.mailDetails.get("txt");

            } else if (!Settings.mailDetails.get("word").isEmpty()) {
                type = Settings.mailDetails.get("type");
                pathname = Settings.mailDetails.get("word");
            } else if (!Settings.mailDetails.get("html").isEmpty()) {
                type = Settings.mailDetails.get("type");
                pathname = Settings.mailDetails.get("html");
            }
            if (!pathname.isEmpty()) {
                //send attachment with readings from a file
                MailSender mailer = new MailSender();
                StringBuilder msg = null;

                File file = new File(pathname);
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int i = 0;
                    while (true) {
                        try {

                            if ((i = fileInputStream.read()) == -1) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        System.out.print((char) i);
                        msg = (msg == null ? new StringBuilder("") : msg).append((char) i);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    mailer.send(from, password, to, subject, msg == null ? null : msg.toString(), type, attachment);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            } else {
                //send attachment without file readings
                MailSender mailer = new MailSender();
                try {
                    mailer.send(from, password, to, subject, null, type, attachment);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }

        } else {
            System.out.println("no attachment");
            if (Settings.mailDetails.containsKey("txt")) {
                type = Settings.mailDetails.get("type");
                pathname = Settings.mailDetails.get("txt");

            } else if (Settings.mailDetails.containsKey("word")) {
                type = Settings.mailDetails.get("type");
                pathname = Settings.mailDetails.get("word");
            } else {
                type = Settings.mailDetails.get("type");
                pathname = Settings.mailDetails.get("html");
            }


            MailSender mailer = new MailSender();
            StringBuilder msg = null;

            File file = new File(pathname);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                int i = 0;
                while (true) {
                    try {

                        if ((i = fileInputStream.read()) == -1) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    System.out.print((char) i);
                    msg = (msg == null ? new StringBuilder("") : msg).append((char) i);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                mailer.send(from, password, to, subject, msg == null ? null : msg.toString(), type, null);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }
    }

    private void send(final String from, final String password, String to, String sub, String msg, String type, File attachment) throws MessagingException {
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        MimeMessage message = new MimeMessage(session);
        try {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        } catch (AddressException ignored) {

        }
        message.setSubject(sub);
        if (attachment != null) {
            //send with attachment
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(msg);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = attachment.getAbsolutePath();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

        } else {
            try {

                //send message
                message.setContent(
                        msg,
                        type);

                Transport.send(message);
                System.out.println("message sent successfully");
                System.out.println();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
