package api;

public class Quest {

    int id;
    int energy;
    int xp;
    int coins;
    int item;
    int chips;

    public Quest(int id, int energy, int xp, int coins, int item, int chips){
        this.id = id;
        this.energy = energy;
        this.xp = xp;
        this.coins = coins;
        this.item = item;
        this.chips = chips;
    }

    public int getId() {
        return id;
    }

    public int getEnergy() {
        return energy;
    }

    public int getXp() {
        return xp;
    }

    public int getCoins() {
        return coins;
    }

    public int getItem() {
        return item;
    }

    public int getChips() {
        return chips;
    }
}
