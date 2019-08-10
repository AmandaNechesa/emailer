import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class WordDoc {
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

        for (XWPFTable table : tables) {
            int counter = 0;
            for (XWPFTableRow row : table.getRows()) {
                if (counter != 0) {
                    int i = 0;
                    for (XWPFTableCell cell : row.getTableCells()) {
                        if (i == 1 && !getContacts().isFile()) {
                            String sFieldValue = cell.getText();
                            System.out.println(sFieldValue);
                            MailSender mailSender = new MailSender();
                            mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), Settings.mailDetails.get("subject"), Settings.mailDetails.get("type"), null, getPassed());


                        } else if (i == 1 && getContacts().exists()) {
                            String sFieldValue = cell.getText();
                            System.out.println(sFieldValue);
                            MailSender mailSender = new MailSender();
                            mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), Settings.mailDetails.get("subject"), Settings.mailDetails.get("type"), getAttachment(), getPassed());
//attach file to email

                        }
                        i++;
                    }

//                    System.out.println("row "+row.getTableCells().get(0));
                }
                counter++;
//                System.out.println(" ");
            }
        }

    }

}

