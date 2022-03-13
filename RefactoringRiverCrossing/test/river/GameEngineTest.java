package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import river.GameEngine.Item;

public class GameEngineTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testObjects() {
        GameObject farmer = new GameObject("Farmer", Location.START);
        Assert.assertEquals("Farmer", farmer.getName());
        Assert.assertEquals(Location.START, farmer.getLocation());
        Assert.assertEquals("", farmer.getSound());

        GameObject wolf = new GameObject("Wolf", Location.START);
        Assert.assertEquals("Wolf", wolf.getName());
        Assert.assertEquals(Location.START, wolf.getLocation());
        Assert.assertEquals("Howl", wolf.getSound());

        GameObject goose = new GameObject("Goose", Location.START);
        Assert.assertEquals("Goose", goose.getName());
        Assert.assertEquals(Location.START, goose.getLocation());
        Assert.assertEquals("Honk", goose.getSound());

        GameObject beans = new GameObject("Beans", Location.START);
        Assert.assertEquals("Beans", beans.getName());
        Assert.assertEquals(Location.START, beans.getLocation());
        Assert.assertEquals("", beans.getSound());
    }

    @Test
    public void testMidTransport() {
        GameEngine engine = new GameEngine();
        Assert.assertEquals(Location.START, engine.getLocation(Item.GOOSE));
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getLocation(Item.GOOSE));

    }

    @Test
    public void testWinningGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.loadBoat(Item.FARMER);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport bottom (beans)
        engine.loadBoat(Item.BEANS);
        engine.rowBoat();
        engine.unloadBoat(Item.BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back with Goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport top (wolf)
        engine.unloadBoat(Item.GOOSE);
        engine.loadBoat(Item.WOLF);
        engine.rowBoat();
        engine.unloadBoat(Item.WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // travel to finish with goose and unload
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
        engine.unloadBoat(Item.FARMER);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());

    }

    @Test
    public void testLosingGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // row back alone and pick up wolf (top)
        engine.rowBoat();
        engine.loadBoat(Item.WOLF);
        engine.rowBoat();
        engine.unloadBoat(Item.WOLF);
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getLocation(Item.WOLF);
        Location midLoc = engine.getLocation(Item.GOOSE);
        Location bottomLoc = engine.getLocation(Item.BEANS);
        Location playerLoc = engine.getLocation(Item.FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.WOLF);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getLocation(Item.WOLF));
        Assert.assertEquals(midLoc, engine.getLocation(Item.GOOSE));
        Assert.assertEquals(bottomLoc, engine.getLocation(Item.BEANS));
        Assert.assertEquals(playerLoc, engine.getLocation(Item.FARMER));
    }
}
