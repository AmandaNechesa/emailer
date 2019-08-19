import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class WordDoc extends Super {
    static String temp = "";
    static String cellValue;
    private File contacts, attachment, passed;

    public WordDoc() {
        this.contacts = null;
        this.attachment = null;
    }

    public File getPassed() {
        return passed;
    }

    public WordDoc setPassed(File passed) {
        this.passed = passed;
        return this;
    }

    public File getContacts() {
        return contacts;
    }

    public WordDoc setContacts(File contacts) {
        this.contacts = contacts;
        return this;
    }

    public File getAttachment() {
        return attachment;
    }

    public WordDoc setAttachment(File attachment) {
        this.attachment = attachment;
        return this;
    }

    public void openFile() throws IOException {

//files
        //passed
        //attachment
        //contacts
        FileInputStream fis = new FileInputStream(getContacts());
        XWPFDocument doc = new XWPFDocument(fis);
        List<XWPFTable> tables = doc.getTables();
//        if (tables.isEmpty()) {
//            System.out.println("no tables present");
            try {
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                System.out.println(extractor.getText());

                String[] lines = extractor.getText().split("\r\n|\r|\n");
                Pattern pattern = Pattern.compile(Settings.mailRegex);
                //                    Matcher matcher = pattern.matcher(email);
                //                    System.out.println(email +" : "+ matcher.matches());
                Set<String> mailset = new TreeSet<>(Arrays.asList(lines));
                for (String email : mailset) {
                    try {
                        if (!getContacts().isFile()) {
                            String sFieldValue = email;
                            System.out.println(sFieldValue);
                            MailSender mailSender = new MailSender();
                            mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), Settings.mailDetails.get("subject"), Settings.mailDetails.get("type"), null);


                        } else if (getContacts().exists()) {
                            String sFieldValue = email;
                            System.out.println(sFieldValue);
                            MailSender mailSender = new MailSender();
                            mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), Settings.mailDetails.get("subject"), Settings.mailDetails.get("type"), getAttachment());
//attach file to email

                        } else if (!getContacts().isFile() && !Settings.message.isEmpty()) {
                            String sFieldValue = email;
                            System.out.println(sFieldValue);
                            MailSender mailSender = new MailSender();
                            mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), Settings.mailDetails.get("subject"), null, null);


                        } else if (getContacts().exists() && !Settings.message.isEmpty()) {
                            String sFieldValue = email;
                            System.out.println(sFieldValue);
                            MailSender mailSender = new MailSender();
                            mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), Settings.mailDetails.get("subject"), null, getAttachment());
//attach file to email

                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                endSession();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

//        } else {
//            for (XWPFTable table : tables) {
//                int counter = 0;
//                for (XWPFTableRow row : table.getRows()) {
//
//                    if (counter != 0) {
//                        int i = 0;
//                        for (XWPFTableCell cell : row.getTableCells()) {
//                            if (i == 1 && !getContacts().isFile()) {
//                                String sFieldValue = cell.getText();
//                                System.out.println(sFieldValue);
//                                MailSender mailSender = new MailSender();
//                                mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), Settings.mailDetails.get("subject"), Settings.mailDetails.get("type"), null);
//
//
//                            } else if (i == 1 && getContacts().exists()) {
//                                String sFieldValue = cell.getText();
//                                System.out.println(sFieldValue);
//                                MailSender mailSender = new MailSender();
//                                mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), Settings.mailDetails.get("subject"), Settings.mailDetails.get("type"), getAttachment());
////attach file to email
//
//                            } else if (!getContacts().isFile() && !Settings.message.isEmpty()) {
//                                String sFieldValue = cell.getText();
//                                System.out.println(sFieldValue);
//                                MailSender mailSender = new MailSender();
//                                mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), Settings.mailDetails.get("subject"), null, null);
//
//
//                            } else if (getContacts().exists() && !Settings.message.isEmpty()) {
//                                String sFieldValue = cell.getText();
//                                System.out.println(sFieldValue);
//                                MailSender mailSender = new MailSender();
//                                mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), Settings.mailDetails.get("subject"), null, getAttachment());
////attach file to email
//
//                            }
//                            i++;
//                        }
//
////                    System.out.println("row "+row.getTableCells().get(0));
//                    }
//                    counter++;
////                System.out.println(" ");
//
//                }
//
//            }
//            endSession();
//        }
    }

    private void endSession() {
        Settings.pathname.clear();
        Settings.mailDetails.clear();
    }

}

