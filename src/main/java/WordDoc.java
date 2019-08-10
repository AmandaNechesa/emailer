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

    public void openFile(File filepassed, File attachment) throws IOException {


        FileInputStream fis = new FileInputStream(filepassed);
        XWPFDocument doc = new XWPFDocument(fis);
        List<XWPFTable> tables = doc.getTables();

        for (XWPFTable table : tables) {
            int counter = 0;
            for (XWPFTableRow row : table.getRows()) {
                if (counter != 0) {
                    int i = 0;
                    for (XWPFTableCell cell : row.getTableCells()) {
                        if (i == 1 && !attachment.isFile()) {
                            String sFieldValue = cell.getText();
                            System.out.println(sFieldValue);
                            MailSender mailSender = new MailSender();
                            mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), "testing", Settings.mailDetails.get("type"), null);


                        } else if (i == 1 && attachment.isFile()) {
                            String sFieldValue = cell.getText();
                            System.out.println(sFieldValue);
                            MailSender mailSender = new MailSender();
                            mailSender.initializeMail(sFieldValue, Settings.userDetails.get("password"), Settings.userDetails.get("email"), "testing", Settings.mailDetails.get("type"), attachment);
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

