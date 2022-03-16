package river;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Graphical interface for the River application
 *
 * @author Seth Brown
 */
public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Fields (hotspots)
    // ==========================================================
    private final int baseY = 275;
    private final int boatY = 335;

    private final int startBaseX = 20;
    private final int startBoatX = 140;
    private final int finishBoatX = 550;
    private final int finishBaseX = 670;

    private final int itemWidth = 50;
    private final int itemHeight = 50;
    private final int boatWidth = 110;
    private final int boatHeight = 50;

    private final Rectangle restartButtonRect = new Rectangle(350, 120, 100, 30);

    private Item seat1Passenger;
    private Item seat2Passenger;
    private Map<Item, Rectangle> itemRectMap = new HashMap<>();
    private GameEngine engine; // Model
    private Rectangle boatRectangle;
    private boolean restart = false;
    private Graphics g; // Graphics object for point methods

    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {
        engine = new GameEngine();
        addMouseListener(this);
        boatRectangle = new Rectangle();
        seat2Passenger = null;
        seat1Passenger = null;
    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================
    private int seatsPainted = 0;

    @Override
    public void paintComponent(Graphics g_arg) {
        this.g = g_arg;
        // Start out with no seats painted
        seatsPainted = 0;

        for (Item item : Item.values()) {
            if (!(item.ordinal() < engine.numberOfItems())) break;
            updateItemRectangles(item); // based on model
        }

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Item item : Item.values()) {
            if (!(item.ordinal() < engine.numberOfItems())) break;
            Rectangle rect = itemRectMap.get(item);
            paintRectangle(engine.getItemColor(item), engine.getItemLabel(item), rect);
        }
        paintBoat();
        //item.ordinal() will return the _1 for the ITEM_1
//        paintObjectsOnLeft(g);
//        paintObjectsOnRight(g);
//        paintObjectsOnBoat(g);

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
            paintRestartButton(g);
        }

    }

    private void paintBoat() {
        switch (engine.getBoatLocation()) {
            case START:
                g.setColor(Color.ORANGE);
                g.fillRect(startBoatX, boatY, boatWidth, boatHeight);
                boatRectangle.setBounds(startBoatX, boatY, boatWidth, boatHeight);
                break;
            case FINISH:
                g.setColor(Color.ORANGE);
                g.fillRect(finishBoatX, boatY, boatWidth, boatHeight);
                boatRectangle.setBounds(finishBoatX, boatY, boatWidth, boatHeight);
                break;
        }
    }

    private void updateItemRectangles(Item item) {
        Rectangle updatedRectangle = new Rectangle();
        switch (engine.getItemLocation(item)) {
            case START:
                updatedRectangle = updatedShoreRectangle(startBaseX, item);
                break;
            case FINISH:
                updatedRectangle = updatedShoreRectangle(finishBaseX, item);
                break;
            case BOAT:
                if (engine.getBoatLocation() == Location.START) {
//                    if (seat1Passenger == null) {
//                        seat1Passenger = item;
                    updatedRectangle = updatedBoatItems(startBoatX, item);
//                    } else {
//                        seat2Passenger = item;
//                        updatedRectangle = updatedBoatItems(startBoatX, seat2Passenger);
//                    }
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

        String displayLabel = str.substring(0, 1);
        int fontSize = (rect.height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = rect.x + rect.width / 2 - fm.stringWidth(displayLabel) / 2;
        int strYCoord = rect.y + rect.height / 2 + fontSize / 2 - 4;
        g.drawString(displayLabel, strXCoord, strYCoord);
    }


    public void paintStringInRectangle(String str, int x, int y, int width, int height, Graphics g) {
        g.setColor(Color.BLACK);
        int fontSize = (height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = x + width / 2 - fm.stringWidth(str) / 2;
        int strYCoord = y + height / 2 + fontSize / 2 - 4;
        g.drawString(str, strXCoord, strYCoord);
    }


    public void paintMessage(String message, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    public void paintRestartButton(Graphics g) {
        g.setColor(Color.BLACK);
        paintBorder(restartButtonRect, 3, g);
        g.setColor(Color.PINK);
        paintRectangle(restartButtonRect, g);
        paintStringInRectangle("Restart", restartButtonRect.x, restartButtonRect.y, restartButtonRect.width,
                restartButtonRect.height, g);
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
            if (this.restartButtonRect.contains(e.getPoint())) {
                engine.resetGame();
                restart = false;
                repaint();
            }
            return;
        }
        for (Item item : Item.values()) {
            if (itemRectMap.get(item).contains(e.getPoint())) {
                if (item == Item.ITEM_0) updateRectangleLocation(item);
                else if (item == Item.ITEM_1) updateRectangleLocation(item);
                else if (item == Item.ITEM_2) updateRectangleLocation(item);
                else if (item == Item.ITEM_3) updateRectangleLocation(item);
//            else if(item == Item.ITEM_4) updateRectangleLocation(item, e.getPoint());
//            else if(item == Item.ITEM_5) updateRectangleLocation(item, e.getPoint());
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
//            engine.unloadBoat(item);
            if (seat1Passenger != null && seat1Passenger.equals(item)) {
                engine.unloadBoat(item);
                seat1Passenger = null;
            } else if (seat2Passenger.equals(item)) {
                engine.unloadBoat(item);
                seat2Passenger = null;
            }
        } else if (engine.getBoatLocation() == Location.START && engine.getItemLocation(item) == Location.BOAT) {
//            engine.unloadBoat(item);
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


//    private Map<Item, Integer> itemXOffsetMap = new HashMap<>();
//    private Map<Item, Integer> itemYOffsetMap = new HashMap<>();
//    private final Item[] itemInSeat = new Item[Item.values().length];
//
//    private void clearSeatAssignments() {
//        Arrays.fill(itemInSeat, null);
//    }
//    private int getAssignedSeat(Item item) {
//        for (int i = 0; i < itemInSeat.length; i++) {
//            if (itemInSeat[i] == item) {
//                // add to seat one
//            }
//        }
//    }
//    public void paintObjectsOnLeft(Graphics g_arg) {
//        this.g = g_arg;
//        if (engine.getItemLocation(Item.ITEM_3) == Location.START) {
//            g.setColor(Color.MAGENTA);
//            g.fillRect(80, 215, 50, 50);
//        }
//        if (engine.getItemLocation(Item.ITEM_2) == Location.START) {
//            g.setColor(Color.CYAN);
//            g.fillRect(20, 215, 50, 50);
//            paintStringInRectangle("W", 20, 215, 50, 50, g);
//        }
//        if (engine.getItemLocation(Item.ITEM_1) == Location.START) {
//            g.setColor(Color.CYAN);
//            g.fillRect(20, 275, 50, 50);
//            paintStringInRectangle("G", 20, 275, 50, 50, g);
//        }
//        if (engine.getItemLocation(Item.ITEM_0) == Location.START) {
//            g.setColor(Color.CYAN);
//            g.fillRect(80, 275, 50, 50);
//            paintStringInRectangle("B", 80, 275, 50, 50, g);
//        }
//    }
//
//    public void paintObjectsOnRight(Graphics g_arg) {
//        this.g = g_arg;
//
//        if (engine.getItemLocation(Item.ITEM_3) == Location.FINISH) {
//            g.setColor(Color.MAGENTA);
//            g.fillRect(730, 215, 50, 50);
//        }
//        if (engine.getItemLocation(Item.ITEM_2) == Location.FINISH) {
//            g.setColor(Color.CYAN);
//            g.fillRect(670, 215, 50, 50);
//            paintStringInRectangle("W", 670, 215, 50, 50, g);
//        }
//        if (engine.getItemLocation(Item.ITEM_1) == Location.FINISH) {
//            g.setColor(Color.CYAN);
//            g.fillRect(670, 275, 50, 50);
//            paintStringInRectangle("G", 670, 275, 50, 50, g);
//        }
//        if (engine.getItemLocation(Item.ITEM_0) == Location.FINISH) {
//            g.setColor(Color.CYAN);
//            g.fillRect(730, 275, 50, 50);
//            paintStringInRectangle("B", 730, 275, 50, 50, g);
//        }
//    }
//
//    public void paintObjectsOnBoat(Graphics g) {
//        if (engine.getBoatLocation() == Location.START) {
//            g.setColor(Color.ORANGE);
//            g.fillRect(140, 275, 110, 50);
//            if (engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
//                g.setColor(Color.MAGENTA);
//                g.fillRect(140, 215, 50, 50);
//            }
//            if (engine.getItemLocation(Item.ITEM_2) == Location.BOAT) {
//                g.setColor(Color.CYAN);
//                g.fillRect(200, 215, 50, 50);
//                paintStringInRectangle("W", 200, 215, 50, 50, g);
//            } else if (engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
//                g.setColor(Color.CYAN);
//                g.fillRect(200, 215, 50, 50);
//                paintStringInRectangle("G", 200, 215, 50, 50, g);
//            } else if (engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
//                g.setColor(Color.CYAN);
//                g.fillRect(200, 215, 50, 50);
//                paintStringInRectangle("B", 200, 215, 50, 50, g);
//            }
//        }
//        if (engine.getBoatLocation() == Location.FINISH) {
//            g.setColor(Color.ORANGE);
//            g.fillRect(550, 275, 110, 50);
//            if (engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
//                g.setColor(Color.MAGENTA);
//                g.fillRect(550, 215, 50, 50);
//            }
//            if (engine.getItemLocation(Item.ITEM_2) == Location.BOAT) {
//                g.setColor(Color.CYAN);
//                g.fillRect(610, 215, 50, 50);
//                paintStringInRectangle("W", 610, 215, 50, 50, g);
//            } else if (engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
//                g.setColor(Color.CYAN);
//                g.fillRect(610, 215, 50, 50);
//                paintStringInRectangle("G", 610, 215, 50, 50, g);
//            } else if (engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
//                paintRectangle(engine.getItemColor(Item.ITEM_0), engine.getItemLabel(Item.ITEM_0), itemRectMap.get(Item.ITEM_0));
//                g.setColor(Color.CYAN);
//                g.fillRect(610, 215, 50, 50);
//                paintStringInRectangle("B", 610, 215, 50, 50, g);
//            }
//        }
//    }
    //            if (engine.getBoatLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
//                engine.rowBoat();
//            }
//            if (engine.getBoatLocation() == Location.START && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
//                engine.rowBoat();
//            }
//        }

//        if (leftFarmerRect.contains(e.getPoint())) {
//            if (engine.getItemLocation(Item.ITEM_3) == Location.START) {
//                engine.loadBoat(Item.ITEM_3);
//            }
//        } else if (leftWolfRect.contains(e.getPoint())) {
//            if (engine.getItemLocation(Item.ITEM_2) == Location.START) {
//                engine.loadBoat(Item.ITEM_2);
//            }
//        } else if (leftGooseRect.contains(e.getPoint())) {
//            if (engine.getItemLocation(Item.ITEM_1) == Location.START) {
//                engine.loadBoat(Item.ITEM_1);
//            }
//        } else if (leftBeansRect.contains(e.getPoint())) {
//            if (engine.getItemLocation(Item.ITEM_0) == Location.START) {
//                engine.loadBoat(Item.ITEM_0);
//            }
//        } else if (leftBoatDriverRect.contains(e.getPoint())) {
//            if (engine.getBoatLocation() == Location.START && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
//                engine.unloadBoat(Item.ITEM_3);
//            }
//        } else if (leftBoatPassengerRect.contains(e.getPoint())) {
//            if (engine.getBoatLocation() == Location.START) {
//                if (engine.getItemLocation(Item.ITEM_2) == Location.BOAT) {
//                    engine.unloadBoat(Item.ITEM_2);
//                } else if (engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
//                    engine.unloadBoat(Item.ITEM_1);
//                }
//                else if (engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
//                    engine.unloadBoat(Item.ITEM_0);
//                }
//            }
//        } else if (leftBoatRect.contains(e.getPoint())) {
//            if (engine.getBoatLocation() == Location.START && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
//                engine.rowBoat();
//            }
//        } else if (rightFarmerRect.contains(e.getPoint())) {
//            if (engine.getItemLocation(Item.ITEM_3) == Location.FINISH) {
//                engine.loadBoat(Item.ITEM_3);
//            }
//        } else if (rightWolfRect.contains(e.getPoint())) {
//            if (engine.getItemLocation(Item.ITEM_2) == Location.FINISH) {
//                engine.loadBoat(Item.ITEM_2);
//            }
//        } else if (rightGooseRect.contains(e.getPoint())) {
//            if (engine.getItemLocation(Item.ITEM_1) == Location.FINISH) {
//                engine.loadBoat(Item.ITEM_1);
//            }
//        } else if (rightBeansRect.contains(e.getPoint())) {
//            if (engine.getItemLocation(Item.ITEM_0) == Location.FINISH) {
//                engine.loadBoat(Item.ITEM_0);
//            }
//        } else if (rightBoatDriverRect.contains(e.getPoint())) {
//            if (engine.getBoatLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
//                engine.unloadBoat(Item.ITEM_3);
//            }
//        } else if (rightBoatPassengerRect.contains(e.getPoint())) {
//            if (engine.getBoatLocation() == Location.FINISH) {
//                if (engine.getItemLocation(Item.ITEM_2) == Location.BOAT) {
//                    engine.unloadBoat(Item.ITEM_2);
//                } else if (engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
//                    engine.unloadBoat(Item.ITEM_1);
//                }
//                else if (engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
//                    engine.unloadBoat(Item.ITEM_0);
//                }
//            }
//        } else if (rightBoatRect.contains(e.getPoint())) {
//            if (engine.getBoatLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
//                engine.rowBoat();
//            }
//        } else {
//            return;
//        }
    //        if (itemRectMap.get(Item.ITEM_0).contains(e.getPoint())){
//            if (engine.getItemLocation(Item.ITEM_0) == Location.START) {
//                engine.loadBoat(Item.ITEM_0);
//            }
//           else if (engine.getItemLocation(Item.ITEM_0) == Location.FINISH) {
//                engine.loadBoat(Item.ITEM_0);
//            }
//            else if (engine.getBoatLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
//                engine.unloadBoat(Item.ITEM_0);
//            }
//            else if (engine.getBoatLocation() == Location.START && engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
//                engine.unloadBoat(Item.ITEM_0);
//            }
//        }
//        if (itemRectMap.get(Item.ITEM_1).contains(e.getPoint())){
//
//            if (engine.getItemLocation(Item.ITEM_1) == Location.START) {
//                engine.loadBoat(Item.ITEM_1);
//            }
//            else if (engine.getItemLocation(Item.ITEM_1) == Location.FINISH) {
//                engine.loadBoat(Item.ITEM_1);
//            }
//            else if (engine.getBoatLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
//                engine.unloadBoat(Item.ITEM_1);
//            }
//            else if (engine.getBoatLocation() == Location.START && engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
//                engine.unloadBoat(Item.ITEM_1);
//            }
//        }
//        if (itemRectMap.get(Item.ITEM_2).contains(e.getPoint())){
//            if (engine.getItemLocation(Item.ITEM_2) == Location.START) {
//                engine.loadBoat(Item.ITEM_2);
//            }
//            else if (engine.getItemLocation(Item.ITEM_2) == Location.FINISH) {
//                engine.loadBoat(Item.ITEM_2);
//            }
//            else if (engine.getBoatLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_2) == Location.BOAT ) {
//                engine.unloadBoat(Item.ITEM_2);
//            }
//            else if (engine.getBoatLocation() == Location.START && engine.getItemLocation(Item.ITEM_2) == Location.BOAT ) {
//                engine.unloadBoat(Item.ITEM_2);
//            }
//        }
//        if (itemRectMap.get(Item.ITEM_3).contains(e.getPoint())){
//            if (engine.getItemLocation(Item.ITEM_3) == Location.START) {
//                engine.loadBoat(Item.ITEM_3);
//            }
//            else if (engine.getItemLocation(Item.ITEM_3) == Location.FINISH) {
//                engine.loadBoat(Item.ITEM_3);
//            }
//            else if (engine.getBoatLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_3) == Location.BOAT ) {
//                engine.unloadBoat(Item.ITEM_3);
//            }
//            else if (engine.getBoatLocation() == Location.START && engine.getItemLocation(Item.ITEM_3) == Location.BOAT ) {
//                engine.unloadBoat(Item.ITEM_0);
//            }
//        }
//    private final Rectangle leftBoatRect = new Rectangle(140, 335, 110, 50);
//    private final Rectangle rightBoatRect = new Rectangle(550, 335, 110, 50);
//
//    private final Rectangle farmerRestartButtonRect = new Rectangle(290, 120, 100, 30);
//    private final Rectangle monsterRestartButtonRect = new Rectangle(410, 120, 100, 30);
//
//    private final Rectangle leftFarmerRect = new Rectangle(80, 215, 50, 50);
//    private final Rectangle leftWolfRect = new Rectangle(20, 215, 50, 50);
//    private final Rectangle leftGooseRect = new Rectangle(20, 275, 50, 50);
//    private final Rectangle leftBeansRect = new Rectangle(80, 275, 50, 50);
//
//    private final Rectangle leftBoatDriverRect = new Rectangle(140, 215, 50, 50);
//    private final Rectangle leftBoatPassengerRect = new Rectangle(200, 215, 50, 50);
//
//    private final Rectangle rightFarmerRect = new Rectangle(730, 215, 50, 50);
//    private final Rectangle rightWolfRect = new Rectangle(670, 215, 50, 50);
//    private final Rectangle rightGooseRect = new Rectangle(670, 275, 50, 50);
//    private final Rectangle rightBeansRect = new Rectangle(730, 275, 50, 50);
//    private final Rectangle rightBoatDriverRect = new Rectangle(550, 215, 50, 50);
//    private final Rectangle rightBoatPassengerRect = new Rectangle(610, 215, 50, 50);
}
