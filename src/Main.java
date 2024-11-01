import api.*;
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) {
        String email = "dionusiavruna11@gmail.com";
        String password = "1";
        String server = "gr22";
        LogIn login = new LogIn();
        login.logingIn(email, password, server);
        JsonObject dataJson = login.getDataJson();
        QuestActions action = new QuestActions(server, dataJson, "xp", 6);
        action.startQuest();
    }
}
