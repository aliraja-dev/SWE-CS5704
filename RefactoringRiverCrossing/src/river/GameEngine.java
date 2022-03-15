package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameEngine {


    public static final Item BEANS = Item.ITEM_0;
    public static final Item WOLF = Item.ITEM_2;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item FARMER = Item.ITEM_3;
    private Location boatLocation;
    private Map<Item, GameObject> gameObjectMap = new HashMap<>();


    public GameEngine() {
        gameObjectMap.put(WOLF, new GameObject("Wolf", Location.START, Color.CYAN));
        gameObjectMap.put(GOOSE, new GameObject("Goose", Location.START, Color.CYAN));
        gameObjectMap.put(BEANS, new GameObject("Beans", Location.START, Color.CYAN));
        gameObjectMap.put(FARMER, new GameObject("Farmer", Location.START, Color.MAGENTA));
        boatLocation = Location.START;
    }



    public String getItemLabel(Item id) {
        return gameObjectMap.get(id).getLabel();
    }

    public Location getItemLocation(Item id) {
        return gameObjectMap.get(id).getLocation();
    }

    public Color getItemColor(Item id) {
        return gameObjectMap.get(id).getColor();
    }

    public Location getBoatLocation() {
        return boatLocation;
    }

    public void loadBoat(Item id) {

        switch (id) {
            case ITEM_2:
                if (getItemLocation(id) == boatLocation && gameObjectMap.get(GOOSE).getLocation() != Location.BOAT
                        && gameObjectMap.get(BEANS).getLocation() != Location.BOAT) {
                    gameObjectMap.get(WOLF).setLocation(Location.BOAT);
                }
                break;
            case ITEM_1:
                if (getItemLocation(id) == boatLocation && gameObjectMap.get(WOLF).getLocation() != Location.BOAT
                        && gameObjectMap.get(BEANS).getLocation() != Location.BOAT) {
                    gameObjectMap.get(GOOSE).setLocation(Location.BOAT);
                }
                break;
            case ITEM_0:
                if (getItemLocation(id) == boatLocation && getItemLocation(id) != Location.BOAT
                        && gameObjectMap.get(GOOSE).getLocation() != Location.BOAT) {
                    gameObjectMap.get(BEANS).setLocation(Location.BOAT);
                }
                break;
            case ITEM_3:
                if (getItemLocation(id) == boatLocation) {
                    gameObjectMap.get(FARMER).setLocation(Location.BOAT);
                }
            default: // do nothing
        }
    }

    public void unloadBoat(Item id) {
        switch (id) {
            case ITEM_2:
                if (getItemLocation(id) == Location.BOAT) {
                    gameObjectMap.get(WOLF).setLocation(boatLocation);
                }
                break;
            case ITEM_1:
                if (getItemLocation(id) == Location.BOAT) {
                    gameObjectMap.get(GOOSE).setLocation(boatLocation);
                }
                break;
            case ITEM_0:
                if (getItemLocation(id) == Location.BOAT) {
                    gameObjectMap.get(BEANS).setLocation(boatLocation);
                }
                break;
            case ITEM_3:
                if (getItemLocation(id) == Location.BOAT) {
                    gameObjectMap.get(FARMER).setLocation(boatLocation);
                }
            default: // do nothing
        }
    }

    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    public boolean gameIsWon() {
        return getItemLocation(WOLF) == Location.FINISH && getItemLocation(GOOSE) == Location.FINISH
                && getItemLocation(BEANS) == Location.FINISH && getItemLocation(FARMER) == Location.FINISH;
    }

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

    public void resetGame() {
        gameObjectMap.get(WOLF).setLocation(Location.START);
        gameObjectMap.get(GOOSE).setLocation(Location.START);
        gameObjectMap.get(BEANS).setLocation(Location.START);
        gameObjectMap.get(FARMER).setLocation(Location.START);
        boatLocation = Location.START;
    }

    public int numberOfItems() {
        return 4;
    }
}
