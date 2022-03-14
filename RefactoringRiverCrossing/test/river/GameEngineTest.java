package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import river.GameEngine.Item;

public class GameEngineTest {
    private GameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new GameEngine();
    }

    @Test
    public void testObjectsCallThrough() {
        Assert.assertEquals("Farmer", engine.getItemName(Item.FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.FARMER));
        Assert.assertEquals("", engine.getItemSound(Item.FARMER));

        Assert.assertEquals("Wolf", engine.getItemName(Item.WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals("Howl", engine.getItemSound(Item.WOLF));

        Assert.assertEquals("Goose", engine.getItemName(Item.GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals("Honk", engine.getItemSound(Item.GOOSE));

        Assert.assertEquals("Beans", engine.getItemName(Item.BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals("", engine.getItemSound(Item.BEANS));
    }

    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));
        transport(Item.GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(Item.GOOSE));
    }

    private void transport(Item id) {
        engine.loadBoat(id);
        engine.rowBoat();
        engine.unloadBoat(id);
    }

    @Test
    public void testWinningGame() {
        // transport the goose
        engine.loadBoat(Item.FARMER);
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport bottom (beans)
        transport(Item.BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back with Goose
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport top (wolf)
        transport(Item.WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // travel to finish with goose and unload
        transport(Item.GOOSE);
        engine.unloadBoat(Item.FARMER);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {
        // transport the goose
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // row back alone and pick up wolf (top)
        engine.rowBoat();
        transport(Item.WOLF);
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {
        // transport the goose
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getItemLocation(Item.WOLF);
        Location midLoc = engine.getItemLocation(Item.GOOSE);
        Location bottomLoc = engine.getItemLocation(Item.BEANS);
        Location playerLoc = engine.getItemLocation(Item.FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.WOLF);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals(midLoc, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals(bottomLoc, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals(playerLoc, engine.getItemLocation(Item.FARMER));
    }
}
