package river;

import java.awt.*;

public class GameObject {

    protected String label;
    protected Location location;
    protected Color color;

    public GameObject(String characterName, Location startLocation, Color color) {
        label = characterName;
        location = startLocation;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public Color getColor() {
        return color;
    }
}


