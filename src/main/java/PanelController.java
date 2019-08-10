import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

public class PanelController implements Initializable {
    public Button readtextfile;

    public Button addEmailFile;
    public Button clearSession;
    public AnchorPane panel;
    public Label LabelEmail;
    public Label LabelProvider;
    public Label LabelName;
    public Button readHtml;
    public Button readWordDocument;
    public Button attachFileToMail;
    public Button sendPlainText;
    public FileInputStream fileInputStream;
    public TextField subject;
    public TextArea message;
    String path = "";
    private File file;
    private int length;

    public void initialize(URL location, ResourceBundle resources) {
        LabelEmail.setText(Settings.userDetails.get("email"));
        LabelProvider.setText(Settings.userDetails.get("provider"));
        LabelName.setText(Settings.userDetails.get("name"));
        buttonListeners();

    }

    private void buttonListeners() {
        clearSession.setOnAction(event -> {
            Settings.userDetails.clear();
            Settings.mailDetails.clear();
            try {
                panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("home.fxml")))));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        addEmailFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extensionFilterDocs = new FileChooser.ExtensionFilter("WORD files", "*.doc");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterDocx = new FileChooser.ExtensionFilter("WORD files", "*.docx");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterDocm = new FileChooser.ExtensionFilter("WORD files", "*.docm");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterDotm = new FileChooser.ExtensionFilter("WORD files", "*.dotm");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterDotx = new FileChooser.ExtensionFilter("WORD files", "*.dotx");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterDot = new FileChooser.ExtensionFilter("WORD files", "*.dot");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            fileChooser.getExtensionFilters().addAll(extensionFilterDocs, extensionFilterDocm, extensionFilterDocx, extensionFilterDot, extensionFilterDotm, extensionFilterDotx);
            fileChooser.setTitle("SELECT WORD DOCUMENT WITH TABLE FOR USER NAME AND EMAIL ADDRESSES");
            //Show open file dialog
            file = fileChooser.showOpenDialog(panel.getScene().getWindow());
            Settings.pathname.put("file", file.getAbsolutePath());
            length = (int) file.length();

            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        readHtml.setOnAction(event -> {
            //open html
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter htmlFiles = new FileChooser.ExtensionFilter("HTML files", "*.html");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter htmfiles = new FileChooser.ExtensionFilter("HTML files", "*.htm");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            fileChooser.getExtensionFilters().addAll(htmlFiles, htmfiles);
            fileChooser.setTitle("SELECT HTML FILE");
            //Show open file dialog
            File chosenHtml = fileChooser.showOpenDialog(panel.getScene().getWindow());
            path = chosenHtml.getAbsolutePath();
            WordDoc wordDoc = new WordDoc();
            try {
                Settings.mailDetails.put("html", path);
                wordDoc.openFile(file, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readtextfile.setOnAction(event -> {
            //read text file
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter textFiles = new FileChooser.ExtensionFilter("TEXT files", "*.txt");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            fileChooser.getExtensionFilters().addAll(textFiles);
            fileChooser.setTitle("SELECT TEXT FILE");
            //Show open file dialog
            File chosenTxt = fileChooser.showOpenDialog(panel.getScene().getWindow());
            path = chosenTxt.getAbsolutePath();
            Settings.mailDetails.put("txt", path);

            WordDoc wordDoc = new WordDoc();
            try {
                wordDoc.openFile(file, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readWordDocument.setOnAction(event -> {
            //read word document
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilterDocs = new FileChooser.ExtensionFilter("WORD files", "*.doc");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterDocx = new FileChooser.ExtensionFilter("WORD files", "*.docx");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterDocm = new FileChooser.ExtensionFilter("WORD files", "*.docm");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterDotm = new FileChooser.ExtensionFilter("WORD files", "*.dotm");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterDotx = new FileChooser.ExtensionFilter("WORD files", "*.dotx");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterDot = new FileChooser.ExtensionFilter("WORD files", "*.dot");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            fileChooser.getExtensionFilters().addAll(extensionFilterDocs, extensionFilterDocm, extensionFilterDocx, extensionFilterDot, extensionFilterDotm, extensionFilterDotx);
            fileChooser.setTitle("SELECT WORD DOCUMENT ");
            //Show open file dialog
            File chosenWord = fileChooser.showOpenDialog(panel.getScene().getWindow());
            path = chosenWord.getAbsolutePath();
            Settings.mailDetails.put("word", path);

            WordDoc wordDoc = new WordDoc();
            try {
                wordDoc.openFile(file, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        attachFileToMail.setOnAction(event -> {
            //attach file
            File f = null;
            WordDoc wordDoc = new WordDoc();
            try {
                wordDoc.openFile(file, f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
