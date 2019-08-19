import java.util.HashMap;
import java.util.Map;

public interface Settings {
    String APPLICATION_ICON = "logo.png";
    String localDb = "jdbc:sqlite:sessionLocalDb.conf";
    String appName = "ULTAK Mail Manager";
    HashMap<String, String> pathname = new HashMap<>();
    HashMap<String, String> userDetails = new HashMap<>();
    HashMap<String, String> mailDetails = new HashMap<>();
    HashMap<String, String> fileReaders = new HashMap<>();
    String mailRegex = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";
    Map<String, String> message = new HashMap<>();

}
