import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class wordDocument {
    static String temp = "";
    static String cellValue;

    public static void openFile() throws IOException {


        File file = new File(Settings.pathname.get("file"));
        FileInputStream fis = new FileInputStream(file);
        XWPFDocument doc = new XWPFDocument(fis);
        List<XWPFTable> tables = doc.getTables();

        for (XWPFTable table : tables) {
            int counter = 0;
            for (XWPFTableRow row : table.getRows()) {
                if (counter != 0) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        System.out.println(cell.getText());
                        String sFieldValue = cell.getText();
                        System.out.println(sFieldValue);
                        System.out.println("\t\t");
                    }
                }
                counter++;
                System.out.println(" ");
            }
        }

    }

}

