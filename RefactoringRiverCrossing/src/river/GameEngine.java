package river;

import java.util.HashMap;
import java.util.Map;

public class GameEngine {

    public enum Item {
        WOLF, GOOSE, BEANS, FARMER;
    }

//    private GameObject wolf;
//    private GameObject goose;
//    private GameObject beans;
//    private GameObject farmer;
    private Location currentLocation;
    private Map<Item, GameObject> gameObjectMap = new HashMap<>();


    public GameEngine() {
        gameObjectMap.put(Item.WOLF,new GameObject("Wolf", Location.START));
        gameObjectMap.put(Item.GOOSE,new GameObject("Goose", Location.START));
        gameObjectMap.put(Item.BEANS,new GameObject("Beans", Location.START));
        gameObjectMap.put(Item.FARMER,new GameObject("Farmer", Location.START));
        currentLocation = Location.START;
    }

    public String getName(Item id) {
        return gameObjectMap.get(id).getName();
    }

    public Location getLocation(Item id) {
        return gameObjectMap.get(id).getLocation();
    }

    public String getSound(Item id) {
        return gameObjectMap.get(id).getSound();
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void loadBoat(Item id) {

        switch (id) {
        case WOLF:
            if (gameObjectMap.get(Item.WOLF).getLocation() == currentLocation && gameObjectMap.get(Item.GOOSE).getLocation() != Location.BOAT
                    && gameObjectMap.get(Item.BEANS).getLocation() != Location.BOAT) {
                gameObjectMap.get(Item.WOLF).setLocation(Location.BOAT);
            }
            break;
        case GOOSE:
            if (gameObjectMap.get(Item.GOOSE).getLocation() == currentLocation && gameObjectMap.get(Item.WOLF).getLocation() != Location.BOAT
                    && gameObjectMap.get(Item.BEANS).getLocation() != Location.BOAT) {
                gameObjectMap.get(Item.GOOSE).setLocation(Location.BOAT);
            }
            break;
        case BEANS:
            if (gameObjectMap.get(Item.BEANS).getLocation() == currentLocation && gameObjectMap.get(Item.BEANS).getLocation() != Location.BOAT
                    && gameObjectMap.get(Item.GOOSE).getLocation() != Location.BOAT) {
                gameObjectMap.get(Item.BEANS).setLocation(Location.BOAT);
            }
            break;
        case FARMER:
            if (gameObjectMap.get(Item.FARMER).getLocation() == currentLocation) {
                gameObjectMap.get(Item.FARMER).setLocation(Location.BOAT);
            }
        default: // do nothing
        }
    }

    public void unloadBoat(Item id) {
        switch (id) {
        case WOLF:
            if (gameObjectMap.get(Item.WOLF).getLocation() == Location.BOAT) {
                gameObjectMap.get(Item.WOLF).setLocation(currentLocation);
            }
            break;
        case GOOSE:
            if (gameObjectMap.get(Item.GOOSE).getLocation() == Location.BOAT) {
                gameObjectMap.get(Item.GOOSE).setLocation(currentLocation);
            }
            break;
        case BEANS:
            if (gameObjectMap.get(Item.BEANS).getLocation() == Location.BOAT) {
                gameObjectMap.get(Item.BEANS).setLocation(currentLocation);
            }
            break;
        case FARMER:
            if (gameObjectMap.get(Item.FARMER).getLocation() == Location.BOAT) {
                gameObjectMap.get(Item.FARMER).setLocation(currentLocation);
            }
        default: // do nothing
        }
    }

    public void rowBoat() {
        assert (currentLocation != Location.BOAT);
        if (currentLocation == Location.START) {
            currentLocation = Location.FINISH;
        } else {
            currentLocation = Location.START;
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
        if (gameObjectMap.get(Item.GOOSE).getLocation() == currentLocation) {
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
        currentLocation = Location.START;
    }

}
