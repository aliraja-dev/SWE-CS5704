package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MonsterGameEngine extends AbstractGameEngine {
    private Location boatLocation;
    private int boatPassengerCount;

    private Map<Item, GameObject> gameObjectMap = new HashMap<>();

    public MonsterGameEngine() {
        addObjectsToMap();
        boatLocation = Location.START;
        boatPassengerCount = 0;
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
        setItemLocation(id, Location.BOAT);
        boatPassengerCount++;
    }

    @Override
    public void unloadBoat(Item id) {
        if (boatLocation == Location.START) setItemLocation(id, Location.START);
        else if (boatLocation == Location.FINISH) gameObjectMap.get(id).setLocation(Location.FINISH);
        boatPassengerCount--;
    }

    @Override
    public void setItemLocation(Item item, Location location) {
        gameObjectMap.get(item).setLocation(location);
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

    public int monstersOnLeftShore() {
        int monsterStartCount = 0;
        for (Item item : Item.values()) {
            if (item.ordinal() % 2 == 1) {
                // do nothing
            } else if (Location.START == getItemLocation(item) && monsterStartCount <= 3) {
                monsterStartCount++;

            } else if (getItemLocation(item) == Location.BOAT && boatLocation == Location.START) {
                monsterStartCount++;
            }
        }
        return monsterStartCount;
    }

    public int monstersOnRightShore() {
        int monsterFinishCount = 0;
        for (Item item : Item.values()) {
            if (item.ordinal() % 2 == 1) {
                // do nothing
            } else if (Location.FINISH == getItemLocation(item) && monsterFinishCount <= 3) {
                monsterFinishCount++;

            } else if (getItemLocation(item) == Location.BOAT && boatLocation == Location.FINISH) {
                monsterFinishCount++;
            }
        }
        return monsterFinishCount;
    }

    public int munchkinsOnLeftShore() {
        int munchkinStartCount = 0;
        for (Item item : Item.values()) {
            if (item.ordinal() % 2 == 0) {
                // do nothing
            } else if (Location.START == getItemLocation(item) && munchkinStartCount <= 3) {
                munchkinStartCount++;

            } else if (getItemLocation(item) == Location.BOAT && boatLocation == Location.START) {
                munchkinStartCount++;
            }
        }
        return munchkinStartCount;
    }

    public int munchkinsOnRightShore() {
        int munchkinFinishCount = 0;
        for (Item item : Item.values()) {
            if (item.ordinal() % 2 == 0) {
                // do nothing
            } else if (Location.FINISH == getItemLocation(item) && munchkinFinishCount <= 3) {
                munchkinFinishCount++;

            } else if (getItemLocation(item) == Location.BOAT && boatLocation == Location.FINISH) {
                munchkinFinishCount++;
            }
        }
        return munchkinFinishCount;
    }


    @Override
    public boolean gameIsLost() {
        if (monstersOnLeftShore() > munchkinsOnLeftShore() && munchkinsOnLeftShore() > 0) return true;
        if (monstersOnRightShore() > munchkinsOnRightShore() && munchkinsOnRightShore() > 0) return true;
        return false;
    }

    @Override
    public void resetGame() {
        addObjectsToMap();
        boatLocation = Location.START;
        boatPassengerCount = 0;
    }

    @Override
    public int numberOfItems() {
        return 6;
    }
}