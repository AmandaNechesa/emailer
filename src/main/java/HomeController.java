import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController extends Super implements Initializable {
    public AnchorPane panel;
    public Label labelAppName;
    public TextField userName;
    public Button createSession;
    public TextField userEmail;
    public TextField passwordField;
    public TextField mailProvider;

    /**
     * Called to initialize
     * a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    public void initialize(URL location, ResourceBundle resources) {
        labelAppName.setText(Settings.appName + " System");
        createSession.setOnAction(event -> {
            if (userEmail.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "FILL FIELD", "NULL EMAIL ADDRESS");
            } else if (passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "FILL FIELD", "NULL PASSWORD FIELD");
            } else if (mailProvider.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "FILL FIELD", "NULL EMAIL PROVIDER");
            } else if (userName.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("No Name!!");
                alert.setHeaderText(null);
                alert.setContentText("You have a null name.Are you sure you want to proceed?");
                alert.initOwner(panel.getScene().getWindow());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        createSession();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    createSession();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createSession() throws IOException {

        Settings.userDetails.put("name", userName.getText());
        Settings.userDetails.put("email", userEmail.getText());
        Settings.userDetails.put("password", passwordField.getText());
        Settings.userDetails.put("provider", mailProvider.getText());
        userName.clear();
        userEmail.clear();
        passwordField.clear();
        mailProvider.clear();
        panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("panel.fxml")))));

    }
}

