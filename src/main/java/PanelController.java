import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public Button addEmailFile;
    public Button clearSession;
    public AnchorPane panel;
    public Label LabelEmail;
    public Label LabelProvider;
    public Label LabelName;
    FileInputStream fileInputStream;
    private File file;
    private int length;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    public void initialize(URL location, ResourceBundle resources) {
        LabelEmail.setText(Settings.userDetails.get("email"));
        LabelProvider.setText(Settings.userDetails.get("provider"));
        LabelName.setText(Settings.userDetails.get("name"));
        clearSession.setOnAction(event -> {
            Settings.userDetails.clear();
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
            WordDoc wordDoc = new WordDoc();
            try {
                wordDoc.openFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
