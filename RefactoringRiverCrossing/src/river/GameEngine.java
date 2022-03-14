package river;

import java.util.HashMap;
import java.util.Map;

public class GameEngine {

    public enum Item {
        WOLF, GOOSE, BEANS, FARMER;
    }

    private Location boatLocation;
    private Map<Item, GameObject> gameObjectMap = new HashMap<>();


    public GameEngine() {
        gameObjectMap.put(Item.WOLF,new GameObject("Wolf", Location.START));
        gameObjectMap.put(Item.GOOSE,new GameObject("Goose", Location.START));
        gameObjectMap.put(Item.BEANS,new GameObject("Beans", Location.START));
        gameObjectMap.put(Item.FARMER,new GameObject("Farmer", Location.START));
        boatLocation = Location.START;
    }

    public String getItemName(Item id) {
        return gameObjectMap.get(id).getName();
    }

    public Location getItemLocation(Item id) {
        return gameObjectMap.get(id).getLocation();
    }

    public String getItemSound(Item id) {
        return gameObjectMap.get(id).getSound();
    }

    public Location getBoatLocation() {
        return boatLocation;
    }

    public void loadBoat(Item id) {

        switch (id) {
        case WOLF:
            if (gameObjectMap.get(Item.WOLF).getLocation() == boatLocation && gameObjectMap.get(Item.GOOSE).getLocation() != Location.BOAT
                    && gameObjectMap.get(Item.BEANS).getLocation() != Location.BOAT) {
                gameObjectMap.get(Item.WOLF).setLocation(Location.BOAT);
            }
            break;
        case GOOSE:
            if (gameObjectMap.get(Item.GOOSE).getLocation() == boatLocation && gameObjectMap.get(Item.WOLF).getLocation() != Location.BOAT
                    && gameObjectMap.get(Item.BEANS).getLocation() != Location.BOAT) {
                gameObjectMap.get(Item.GOOSE).setLocation(Location.BOAT);
            }
            break;
        case BEANS:
            if (gameObjectMap.get(Item.BEANS).getLocation() == boatLocation && gameObjectMap.get(Item.BEANS).getLocation() != Location.BOAT
                    && gameObjectMap.get(Item.GOOSE).getLocation() != Location.BOAT) {
                gameObjectMap.get(Item.BEANS).setLocation(Location.BOAT);
            }
            break;
        case FARMER:
            if (gameObjectMap.get(Item.FARMER).getLocation() == boatLocation) {
                gameObjectMap.get(Item.FARMER).setLocation(Location.BOAT);
            }
        default: // do nothing
        }
    }

    public void unloadBoat(Item id) {
        switch (id) {
        case WOLF:
            if (gameObjectMap.get(Item.WOLF).getLocation() == Location.BOAT) {
                gameObjectMap.get(Item.WOLF).setLocation(boatLocation);
            }
            break;
        case GOOSE:
            if (gameObjectMap.get(Item.GOOSE).getLocation() == Location.BOAT) {
                gameObjectMap.get(Item.GOOSE).setLocation(boatLocation);
            }
            break;
        case BEANS:
            if (gameObjectMap.get(Item.BEANS).getLocation() == Location.BOAT) {
                gameObjectMap.get(Item.BEANS).setLocation(boatLocation);
            }
            break;
        case FARMER:
            if (gameObjectMap.get(Item.FARMER).getLocation() == Location.BOAT) {
                gameObjectMap.get(Item.FARMER).setLocation(boatLocation);
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
        return gameObjectMap.get(Item.WOLF).getLocation() == Location.FINISH && gameObjectMap.get(Item.GOOSE).getLocation() == Location.FINISH
                && gameObjectMap.get(Item.BEANS).getLocation() == Location.FINISH && gameObjectMap.get(Item.FARMER).getLocation() == Location.FINISH;
    }

    public boolean gameIsLost() {
        if (gameObjectMap.get(Item.GOOSE).getLocation() == Location.BOAT) {
            return false;
        }
        if (gameObjectMap.get(Item.GOOSE).getLocation() == gameObjectMap.get(Item.FARMER).getLocation()) {
            return false;
        }
        if (gameObjectMap.get(Item.GOOSE).getLocation() == boatLocation) {
            return false;
        }
        if (gameObjectMap.get(Item.GOOSE).getLocation() == gameObjectMap.get(Item.WOLF).getLocation()) {
            return true;
        }
        if (gameObjectMap.get(Item.GOOSE).getLocation() == gameObjectMap.get(Item.BEANS).getLocation()) {
            return true;
        }
        return false;
    }

    public void resetGame() {
        gameObjectMap.get(Item.WOLF).setLocation(Location.START);
        gameObjectMap.get(Item.GOOSE).setLocation(Location.START);
        gameObjectMap.get(Item.BEANS).setLocation(Location.START);
        gameObjectMap.get(Item.FARMER).setLocation(Location.START);
        boatLocation = Location.START;
    }

}
