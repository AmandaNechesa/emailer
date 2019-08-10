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
    private File file, fileread;
    private int length;

    public void initialize(URL location, ResourceBundle resources) {
        LabelEmail.setText(Settings.userDetails.get("email"));
        LabelProvider.setText(Settings.userDetails.get("provider"));
        LabelName.setText(Settings.userDetails.get("name"));
        buttonListeners();

    }

    private void buttonListeners() {
        //completed functionality
        WordDoc wordDoc = new WordDoc();

        clearSession.setOnAction(event -> {
            Settings.userDetails.clear();
            Settings.mailDetails.clear();
            try {
                panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("home.fxml")))));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
//        *************************************************************************************************************************//

        addEmailFile.setOnAction(event -> {
            Settings.mailDetails.put("subject", subject.getText());
            FileChooser fileChooser = getWordFiles();
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
            try {
                wordDoc.setContacts(file);
                wordDoc.openFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readHtml.setOnAction(event -> {
            //open html
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter htmlFiles = new FileChooser.ExtensionFilter("HTML files", "*.html", "*.htm");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            fileChooser.getExtensionFilters().addAll(htmlFiles);
            fileChooser.setTitle("SELECT HTML FILE");
            //Show open file dialog
            File chosenHtml = fileChooser.showOpenDialog(panel.getScene().getWindow());
            path = chosenHtml.getAbsolutePath();
            System.out.println(path);
            Settings.mailDetails.put("html", path);
            Settings.mailDetails.put("type", "text/html");
            wordDoc.setPassed(new File(path));
//                wordDoc.openFile();
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
            Settings.mailDetails.put("type", "text/plain");
            wordDoc.setPassed(new File(path));

//                wordDoc.openFile();
        });
        readWordDocument.setOnAction(event -> {
            //read word document
            FileChooser fileChooser = getWordFiles();
            fileChooser.setTitle("SELECT WORD DOCUMENT ");
            //Show open file dialog
            File chosenWord = fileChooser.showOpenDialog(panel.getScene().getWindow());
            path = chosenWord.getAbsolutePath();
            Settings.mailDetails.put("word", path);
            Settings.mailDetails.put("type", "text");
            wordDoc.setPassed(new File(path));

//                wordDoc.openFile();
        });
        attachFileToMail.setOnAction(event -> {
            //attach file
            File f;
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilterDocs = new FileChooser.ExtensionFilter("Document files", "*.pdf", "*.txt", "*.doc", "*.docx", "*.docm", "*.dotm", "*.dotx", "*.dot");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFiltermp3 = new FileChooser.ExtensionFilter("Mp3 files", "*.mp3");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            FileChooser.ExtensionFilter extensionFilterSQL = new FileChooser.ExtensionFilter("SQL files", "*.sql", "*.sqlite");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
            fileChooser.getExtensionFilters().addAll(extensionFilterDocs, extensionFiltermp3, extensionFilterSQL);
            fileChooser.setTitle("ATTACH FILE TO EMAIL");
            f = fileChooser.showOpenDialog(panel.getScene().getWindow());

            wordDoc.setContacts(new File(path));
            wordDoc.setAttachment(f);

//                wordDoc.openFile();
        });
    }

    private FileChooser getWordFiles() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilterDocs = new FileChooser.ExtensionFilter("WORD files", "*.doc", "*.docx", "*.docm", "*.dotm", "*.dotx", "*.dot");//, ".docx", ".docm", ".dot", ".dotm", ".dotx");
        fileChooser.getExtensionFilters().addAll(extensionFilterDocs);
        return fileChooser;
    }
}
