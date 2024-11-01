package api;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class Quests {

    ArrayList<Quest> quests = new ArrayList<>();
    int maxEnergyCost;

    public Quests(JsonObject dataJson, int maxEnergyCost){
        this.maxEnergyCost = maxEnergyCost;
        JsonArray questsJson = dataJson.getAsJsonArray("quests");
        for (JsonElement element : questsJson){
            JsonObject questJson = element.getAsJsonObject();
            int id = questJson.get("id").getAsInt();
            int energy = questJson.get("energy_cost").getAsInt();

            JsonObject rewards = JsonParser.parseString(questJson.get("rewards").getAsString()).getAsJsonObject();
            int xp = rewards.get("xp").getAsInt();
            int coins = rewards.get("coins").getAsInt();
            int item = rewards.get("item").getAsInt();
            int chips;
            if (rewards.has("slotmachine_jetons")){
                chips = rewards.get("slotmachine_jetons").getAsInt();
            } else {
                chips = 0;
            }
//            System.out.println(id+" "+energy+" "+xp+" "+coins+" "+item+" "+chips);
            Quest quest = new Quest(id, energy, xp, coins, item, chips);
            this.quests.add(quest);
        }
    }

    public int xpPriority(){
        float max = 0;
        int maxId = 0;
        for (Quest quest : this.quests){
            if ((float) quest.getXp()/quest.getEnergy() > max && quest.getEnergy() < maxEnergyCost){
                max = (float) quest.getXp()/quest.getEnergy();
                maxId = quest.getId();
            }
        }
        // making sure we return a valid quest id
        if (maxId == 0){
            return this.shortestPriority();
        } else {
            return maxId;
        }

    }

    public int coinsPriority(){
        float max = 0;
        int maxId = 0;
        for (Quest quest : this.quests){
            if ((float) quest.getCoins()/quest.getEnergy() > max){
                max = (float) quest.getCoins()/quest.getEnergy();
                maxId = quest.getId();
            }
        }
        // making sure we return a valid quest id
        if (maxId == 0){
            return this.shortestPriority();
        } else {
            return maxId;
        }
    }

    public int itemsPriority() {
        int minEnergyWithItem = 40;
        int idWithItem = 0;
        int minEnergyOverall = 40;
        int idOverall = 0;
        int count = 0;
        // quest.getItem() is 0 if this quest doesn't give an item
        for (Quest quest : this.quests) {
            if (quest.getEnergy() > maxEnergyCost) {
                if (quest.getItem() != 0 && quest.getEnergy() < minEnergyWithItem) {
                    minEnergyWithItem = quest.getEnergy();
                    idWithItem = quest.getId();
                    count++;
                } else if (quest.getEnergy() < minEnergyOverall) {
                    minEnergyOverall = quest.getEnergy();
                    idOverall = quest.getId();
                }
            }
        }
        // making sure we return a valid quest id
        if (idWithItem==0 && idOverall==0){
            return this.shortestPriority();
        } else if (count == 0){
            return idOverall;
        } else {
            return idWithItem;
        }
    }

    public int shortestPriority(){
        int minEnergy = 40;
        int minId = 0;
        for (Quest quest : this.quests){
            if (quest.getEnergy() < minEnergy){
                minEnergy = quest.getEnergy();
                minId = quest.getId();
            }
        }
        return minId;
    }

    public int chipsPriority(){
        float max = 0;
        int maxId = 0;
        for (Quest quest : this.quests){
            if ((float) quest.getChips()/quest.getEnergy() > max){
                max = (float) quest.getChips()/quest.getEnergy();
                maxId = quest.getId();
            }
        }
        if (maxId == 0){
            return this.shortestPriority();
        } else {
            return maxId;
        }
    }

}
