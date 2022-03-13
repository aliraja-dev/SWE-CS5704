package river;

import river.GameEngine.Location;


public class GameObject {

    protected String name;
    protected Location location;
    protected String sound;

    public GameObject(String characterName, Location startLocation) {
        name = characterName;
        location = startLocation;
        if (name.equals("Wolf")) sound = "Howl";
        else if (name.equals("Goose")) sound = "Honk";
        else sound = "";
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public String getSound() {
        return sound;
    }
}


