package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MonsterGameEngine extends AbstractGameEngine {
    private Location boatLocation;
    private int boatPassengerCount;
    private int munchkinStartCount;
    private int munchkinFinishCount;
    private int monsterStartCount;
    private int monsterFinishCount;

    private Map<Item, GameObject> gameObjectMap = new HashMap<>();

    public MonsterGameEngine() {
        addObjectsToMap();
        boatLocation = Location.START;
        boatPassengerCount = 0;
        monsterStartCount = 0;
        monsterFinishCount = 0;
        munchkinFinishCount = 0;
        munchkinStartCount = 0;
    }

    private void addObjectsToMap() {
        gameObjectMap.put(Item.ITEM_0, new GameObject("Monster", Location.START, Color.PINK));
        gameObjectMap.put(Item.ITEM_1, new GameObject("Munchkin", Location.START, Color.BLUE));
        gameObjectMap.put(Item.ITEM_2, new GameObject("Monster", Location.START, Color.PINK));
        gameObjectMap.put(Item.ITEM_3, new GameObject("Munchkin", Location.START, Color.BLUE));
        gameObjectMap.put(Item.ITEM_4, new GameObject("Monster", Location.START, Color.PINK));
        gameObjectMap.put(Item.ITEM_5, new GameObject("Munchkin", Location.START, Color.BLUE));
    }


    @Override
    public String getItemLabel(Item id) {
        return gameObjectMap.get(id).getLabel();
    }

    @Override
    public Location getItemLocation(Item id) {
        return gameObjectMap.get(id).getLocation();
    }

    @Override
    public Color getItemColor(Item id) {
        return gameObjectMap.get(id).getColor();
    }

    @Override
    public Location getBoatLocation() {
        return boatLocation;
    }

    @Override
    public void loadBoat(Item id) {
        if (getItemLocation(id) != boatLocation) return; // item and boat different locales
        if (boatPassengerCount >= 2) return; // boat passengers over capacity
        gameObjectMap.get(id).setLocation(Location.BOAT);
        boatPassengerCount++;
    }

    @Override
    public void unloadBoat(Item id) {
        if (boatLocation == Location.START) gameObjectMap.get(id).setLocation(Location.START);
        else if (boatLocation == Location.FINISH) gameObjectMap.get(id).setLocation(Location.FINISH);
        boatPassengerCount--;
    }

    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (boatPassengerCount == 0) return; // cannot row without passengers
        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    @Override
    public boolean gameIsWon() {
        for (Item item : Item.values()) {
            if (getItemLocation(item) == Location.BOAT || getItemLocation(item) == Location.START) {
                return false;
            }
        }
        return true;
    }

    private void countMunchkins() {
        for (Item item : Item.values()) {
            if (item.ordinal() % 2 == 0) {
                // do nothing since this is a monster
            } else if (getItemLocation(item) == Location.START) {
                munchkinStartCount++;
                if (munchkinFinishCount > 0) {
                    munchkinFinishCount--;
                }
            } else if (getItemLocation(item) == Location.FINISH) {
                munchkinFinishCount++;
                munchkinStartCount--;
            }
        }
    }

    private void countMonsters() {
        for (Item item : Item.values()) {
            if (item.ordinal() % 2 == 1) {
                // do nothing since this is a munchkin
            } else if (getItemLocation(item) == Location.START) {
                monsterStartCount++;
                if (monsterFinishCount > 0) {
                    monsterFinishCount--;
                }
            } else if (getItemLocation(item) == Location.FINISH) {
                monsterFinishCount++;
                monsterStartCount--;
            }
        }
    }

    @Override
    public boolean gameIsLost() {
        countMonsters();
        countMunchkins();
        if (monsterStartCount > munchkinStartCount && munchkinStartCount > 0 && boatLocation != Location.START) {
            return true;
        }
        if (monsterFinishCount > munchkinFinishCount && munchkinFinishCount > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void resetGame() {
        addObjectsToMap();
        boatLocation = Location.START;
        boatPassengerCount = 0;
        monsterStartCount = 0;
        monsterFinishCount = 0;
        munchkinFinishCount = 0;
        munchkinStartCount = 0;
    }

    @Override
    public int numberOfItems() {
        return 6;
    }
}
