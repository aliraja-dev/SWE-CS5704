package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FarmerGameEngine extends AbstractGameEngine {

    public static final Item BEANS = Item.ITEM_0;
    public static final Item WOLF = Item.ITEM_2;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item FARMER = Item.ITEM_3;
    private Location boatLocation;

    private int boatPassengerCount;
    private Map<Item, GameObject> gameObjectMap = new HashMap<>();

    public FarmerGameEngine() {
        gameObjectMap.put(WOLF, new GameObject("Wolf", Location.START, Color.CYAN));
        gameObjectMap.put(GOOSE, new GameObject("Goose", Location.START, Color.CYAN));
        gameObjectMap.put(BEANS, new GameObject("Beans", Location.START, Color.CYAN));
        gameObjectMap.put(FARMER, new GameObject("Farmer", Location.START, Color.MAGENTA));
        boatLocation = Location.START;
        boatPassengerCount = 0;
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
        if (getItemLocation(FARMER) != Location.BOAT) boatLocation = getBoatLocation();
        else if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    @Override
    public boolean gameIsWon() {
        return getItemLocation(WOLF) == Location.FINISH && getItemLocation(GOOSE) == Location.FINISH
                && getItemLocation(BEANS) == Location.FINISH && getItemLocation(FARMER) == Location.FINISH;
    }

    @Override
    public boolean gameIsLost() {
        if (getItemLocation(GOOSE) == Location.BOAT) {
            return false;
        }
        if (getItemLocation(GOOSE) == getItemLocation(FARMER)) {
            return false;
        }
        if (getItemLocation(GOOSE) == boatLocation) {
            return false;
        }
        if (getItemLocation(GOOSE) == getItemLocation(WOLF)) {
            return true;
        }
        if (getItemLocation(GOOSE) == getItemLocation(BEANS)) {
            return true;
        }
        return false;
    }

    @Override
    public void resetGame() {
        gameObjectMap.get(WOLF).setLocation(Location.START);
        gameObjectMap.get(GOOSE).setLocation(Location.START);
        gameObjectMap.get(BEANS).setLocation(Location.START);
        gameObjectMap.get(FARMER).setLocation(Location.START);
        boatLocation = Location.START;
        boatPassengerCount = 0;
    }

    @Override
    public int numberOfItems() {
        return 4;
    }
}
