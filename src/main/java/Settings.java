import java.util.HashMap;

public interface Settings {
    String APPLICATION_ICON = "logo.png";
    String localDb = "jdbc:sqlite:sessionLocalDb.conf";
    String appName = "Mwendes Mail Manager";
    HashMap<String, String> pathname = new HashMap<>();
    HashMap<String, String> userDetails = new HashMap<>();

}
