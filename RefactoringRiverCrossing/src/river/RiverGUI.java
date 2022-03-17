package river;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Graphical interface for the River application
 *
 * @author Seth Brown 16 Mar 2022
 */
public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Fields (hotspots)
    // ==========================================================
    private final int baseY = 275;
    private final int startBoatX = 140;
    private final int finishBoatX = 550;

    private final Rectangle farmerEngineStartButton = new Rectangle(120, 420, 200, 30);
    private final Rectangle monsterEngineStartButton = new Rectangle(440, 420, 200, 30);


    private Item seat1Passenger;
    private Item seat2Passenger;
    private Map<Item, Rectangle> itemRectMap = new HashMap<>();
    private GameEngine engine; // Model
    private final Rectangle boatRectangle;
    private boolean restart = false;
    private Graphics g; // Graphics object for point methods
    private boolean restartMonster = false;

    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {
        engine = new FarmerGameEngine();
        addMouseListener(this);
        boatRectangle = new Rectangle();
        seat2Passenger = null;
        seat1Passenger = null;
    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics g_arg) {
        this.g = g_arg;

        for (Item item : Item.values()) {
            if (!(item.ordinal() < engine.numberOfItems())) break;
            updateItemRectangles(item); // based on model
        }

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Item item : Item.values()) {
            if ((item.ordinal() >= engine.numberOfItems())) break;
            Rectangle rect = itemRectMap.get(item);
            paintRectangle(engine.getItemColor(item), engine.getItemLabel(item), rect);
        }
        paintBoat();

        String message = "";
        if (engine.gameIsLost()) {
            message = "You Lost!";
            restart = true;
        }
        if (engine.gameIsWon()) {
            message = "You Won!";
            restart = true;
        }
        paintMessage(message, g);
        if (restart) {
            paintEngineButtons(g);
            seat2Passenger = null;
            seat1Passenger = null;
        }

    }

    private void paintBoat() {
        int boatWidth = 110;
        int boatHeight = 50;
        int boatY = 335;
        switch (engine.getBoatLocation()) {
            case START -> {
                g.setColor(Color.ORANGE);
                g.fillRect(startBoatX, boatY, boatWidth, boatHeight);
                boatRectangle.setBounds(startBoatX, boatY, boatWidth, boatHeight);
            }
            case FINISH -> {
                g.setColor(Color.ORANGE);
                g.fillRect(finishBoatX, boatY, boatWidth, boatHeight);
                boatRectangle.setBounds(finishBoatX, boatY, boatWidth, boatHeight);
            }
        }
    }

    private void updateItemRectangles(Item item) {
        Rectangle updatedRectangle = new Rectangle();
        int startBaseX = 20;
        int finishBaseX = 670;
        switch (engine.getItemLocation(item)) {
            case START:
                updatedRectangle = updatedShoreRectangle(startBaseX, item);
                break;
            case FINISH:
                updatedRectangle = updatedShoreRectangle(finishBaseX, item);
                break;
            case BOAT:
                if (engine.getBoatLocation() == Location.START) {
                    updatedRectangle = updatedBoatItems(startBoatX, item);
                } else if (engine.getBoatLocation() == Location.FINISH) {
                    updatedRectangle = updatedBoatItems(finishBoatX, item);
                }
                break;
            default:
        }
        itemRectMap.put(item, updatedRectangle);
    }

    private Rectangle updatedShoreRectangle(int location, Item item) {
        Rectangle rect = new Rectangle();
        int itemWidth = 50;
        int itemHeight = 50;
        if (item.equals(Item.ITEM_5)) {
            rect.setBounds(location, baseY - 120, itemWidth, itemHeight);
        }
        if (item.equals(Item.ITEM_4)) {
            rect.setBounds(location + 60, baseY - 120, itemWidth, itemHeight);
        }
        if (item.equals(Item.ITEM_3)) {
            rect.setBounds(location + 60, baseY - 60, itemWidth, itemHeight);
        }
        if (item.equals(Item.ITEM_2)) {
            rect.setBounds(location, baseY - 60, itemWidth, itemHeight);
        }
        if (item.equals(Item.ITEM_1)) {
            rect.setBounds(location, baseY, itemWidth, itemHeight);
        }
        if (item.equals(Item.ITEM_0)) {
            rect.setBounds(location + 60, baseY, itemWidth, itemHeight);
        }
        return rect;
    }

    private Rectangle updatedBoatItems(int location, Item item) {
        Rectangle rect = new Rectangle();
        if (item.equals(seat1Passenger)) {
            rect.setBounds(location, baseY, 50, 50);
        } else if (item.equals(seat2Passenger)) {
            rect.setBounds(location + 60, baseY, 50, 50);
        }
        return rect;
    }


    private void paintRectangle(Color color, String str, Rectangle rect) {
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.BLACK);

        String displayLabel = str.substring(0, 2);
        int fontSize = 20;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoordinate = rect.x + rect.width / 2 - fm.stringWidth(displayLabel) / 2;
        int strYCoordinate = rect.y + rect.height / 2 + fontSize / 2 - 4;
        g.drawString(displayLabel, strXCoordinate, strYCoordinate);
    }


    public void paintStringInRectangle(String str, int x, int y, int width, int height, Graphics g) {
        g.setColor(Color.BLACK);
        int fontSize = (height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoordinate = x + width / 2 - fm.stringWidth(str) / 2;
        int strYCoordinate = y + height / 2 + fontSize / 2 - 4;
        g.drawString(str, strXCoordinate, strYCoordinate);
    }


    public void paintMessage(String message, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoordinate = 400 - fm.stringWidth(message) / 2;
        int strYCoordinate = 100;
        g.drawString(message, strXCoordinate, strYCoordinate);
    }

    public void paintEngineButtons(Graphics g) {
        g.setColor(Color.BLACK);
        paintBorder(monsterEngineStartButton, 3, g);
        g.setColor(Color.YELLOW);
        paintRectangle(monsterEngineStartButton, g);
        paintStringInRectangle("Play Monster", monsterEngineStartButton.x, monsterEngineStartButton.y, monsterEngineStartButton.width,
                monsterEngineStartButton.height, g);
        restartMonster = false;

        g.setColor(Color.BLACK);
        paintBorder(farmerEngineStartButton, 3, g);
        g.setColor(Color.RED);
        paintRectangle(farmerEngineStartButton, g);
        paintStringInRectangle("Play Farmer", farmerEngineStartButton.x, farmerEngineStartButton.y, farmerEngineStartButton.width,
                farmerEngineStartButton.height, g);
    }


    public void paintBorder(Rectangle r, int thickness, Graphics g) {
        g.fillRect(r.x - thickness, r.y - thickness, r.width + (2 * thickness), r.height + (2 * thickness));
    }

    public void paintRectangle(Rectangle r, Graphics g) {
        g.fillRect(r.x, r.y, r.width, r.height);
    }

    // ==========================================================
    // Startup Methods
    // ==========================================================

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window
        JFrame frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        RiverGUI newContentPane = new RiverGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(RiverGUI::createAndShowGUI);
    }

    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================

    @Override
    public void mouseClicked(MouseEvent e) {

        if (restart) {
            if (this.farmerEngineStartButton.contains(e.getPoint())) {
                engine = new FarmerGameEngine();
                restart = false;
                repaint();
            } else if (this.monsterEngineStartButton.contains(e.getPoint())) {
                engine = new MonsterGameEngine();
                restart = false;
                restartMonster = true;
                repaint();
            }
            return;
        }
        for (Item item : Item.values()) {
            if ((item.ordinal() >= engine.numberOfItems())) break;
            if (item != null && itemRectMap.get(item).contains(e.getPoint())) {
                if (item == Item.ITEM_0) updateRectangleLocation(item);
                else if (item == Item.ITEM_1) updateRectangleLocation(item);
                else if (item == Item.ITEM_2) updateRectangleLocation(item);
                else if (item == Item.ITEM_3) updateRectangleLocation(item);
                else if (item == Item.ITEM_4) updateRectangleLocation(item);
                else if (item == Item.ITEM_5) updateRectangleLocation(item);
            }
        }
        if (boatRectangle.contains(e.getPoint())) engine.rowBoat();
        repaint();
    }

    private void updateRectangleLocation(Item item) {
        if (engine.getItemLocation(item) == Location.START) {
            if (seat1Passenger == null) {
                engine.loadBoat(item);
                seat1Passenger = item;
            } else if (seat2Passenger == null) {
                engine.loadBoat(item);
                seat2Passenger = item;
            }
        } else if (engine.getItemLocation(item) == Location.FINISH) {
            if (seat1Passenger == null) {
                engine.loadBoat(item);
                seat1Passenger = item;
            } else if (seat2Passenger == null) {
                engine.loadBoat(item);
                seat2Passenger = item;
            }
        } else if (engine.getBoatLocation() == Location.FINISH && engine.getItemLocation(item) == Location.BOAT) {
            if (seat1Passenger != null && seat1Passenger.equals(item)) {
                engine.unloadBoat(item);
                seat1Passenger = null;
            } else if (seat2Passenger.equals(item)) {
                engine.unloadBoat(item);
                seat2Passenger = null;
            }
        } else if (engine.getBoatLocation() == Location.START && engine.getItemLocation(item) == Location.BOAT) {
            if (seat1Passenger != null && seat1Passenger.equals(item)) {
                engine.unloadBoat(item);
                seat1Passenger = null;
            } else if (seat2Passenger.equals(item)) {
                engine.unloadBoat(item);
                seat2Passenger = null;
            }
        }

    }

    // ----------------------------------------------------------
    // None of these methods will be used
    // ----------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
