package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameEngineTest {
    private GameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new GameEngine();
    }

    @Test
    public void testObjectsCallThrough() {
        Assert.assertEquals("Farmer", engine.getItemName(Item.ITEM_3));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_3));
        Assert.assertEquals("", engine.getItemColor(Item.ITEM_3));

        Assert.assertEquals("Wolf", engine.getItemName(Item.ITEM_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_2));
        Assert.assertEquals("Howl", engine.getItemColor(Item.ITEM_2));

        Assert.assertEquals("Goose", engine.getItemName(Item.ITEM_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_1));
        Assert.assertEquals("Honk", engine.getItemColor(Item.ITEM_1));

        Assert.assertEquals("Beans", engine.getItemName(Item.ITEM_0));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_0));
        Assert.assertEquals("", engine.getItemColor(Item.ITEM_0));
    }

    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_1));
        transport(Item.ITEM_1);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(Item.ITEM_1));
    }

    private void transport(Item id) {
        engine.loadBoat(id);
        engine.rowBoat();
        engine.unloadBoat(id);
    }

    @Test
    public void testWinningGame() {
        // transport the goose
        engine.loadBoat(Item.ITEM_3);
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport bottom (beans)
        transport(Item.ITEM_0);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back with Goose
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport top (wolf)
        transport(Item.ITEM_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // travel to finish with goose and unload
        transport(Item.ITEM_1);
        engine.unloadBoat(Item.ITEM_3);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {
        // transport the goose
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // row back alone and pick up wolf (top)
        engine.rowBoat();
        transport(Item.ITEM_2);
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {
        // transport the goose
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getItemLocation(Item.ITEM_2);
        Location midLoc = engine.getItemLocation(Item.ITEM_1);
        Location bottomLoc = engine.getItemLocation(Item.ITEM_0);
        Location playerLoc = engine.getItemLocation(Item.ITEM_3);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.ITEM_2);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getItemLocation(Item.ITEM_2));
        Assert.assertEquals(midLoc, engine.getItemLocation(Item.ITEM_1));
        Assert.assertEquals(bottomLoc, engine.getItemLocation(Item.ITEM_0));
        Assert.assertEquals(playerLoc, engine.getItemLocation(Item.ITEM_3));
    }
}
