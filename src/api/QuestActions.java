package api;
import com.google.gson.JsonObject;

import java.net.http.HttpResponse;

public class QuestActions {

    String server;
    String questPriority;
    int maxEnergyCost;
    JsonObject dataJson;

    public QuestActions(String server, JsonObject dataJson, String questPriority, int maxEnergyCost){
        this.server = server;
        this.questPriority = questPriority;
        this.maxEnergyCost = maxEnergyCost;
        this.dataJson = dataJson;
    }

    public void startQuest(){
        if (this.dataJson.entrySet().isEmpty()){
            LogIn login = new LogIn();
            this.dataJson = login.getDataJson();
        }
        Quests quests = new Quests(this.dataJson, this.maxEnergyCost);

        String questId = "";
        switch(questPriority){
            case "xp":
                questId = String.valueOf(quests.xpPriority());
                break;
            case "coins":
                questId = String.valueOf(quests.coinsPriority());
                break;
            case "items":
                questId = String.valueOf(quests.itemsPriority());
                break;
            case "shortest":
                questId = String.valueOf(quests.shortestPriority());
                break;
            case "chips":
                questId = String.valueOf(quests.chipsPriority());
                break;
        }
        JsonObject user = dataJson.getAsJsonObject("user");
        String userId = user.get("id").getAsString();
        String appVersion = user.get("app_version").getAsString();
        String sessionId = user.get("session_id").getAsString();
        //597166baec6fb54232e6ad8d63de598d
        String data = String.format("quest_id=%s&action=startQuest&user_id=%s&user_session_id=%s&client_version=html5_%s&auth=1&rct=1&keep_active=true&device_type=web",
                questId, userId, sessionId, appVersion);
        System.out.println(userId);
        System.out.println(questId);
        System.out.println("session: "+sessionId);
        ApiRequest startMission = new ApiRequest();
        System.out.printf("-\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(startMission.createRequest(server, data).body());
    }
}
