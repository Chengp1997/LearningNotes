package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class FarmerGameEngineTest {

    private GameEngine engine;
    private final Item BEANS = Item.ITEM_0;
    private final Item GOOSE=Item.ITEM_1;
    private final Item WOLF=Item.ITEM_2;
    private final Item FARMER=Item.ITEM_3;

    @Before
    public void setUp() throws Exception {
         engine=new FarmerGameEngine();
    }

    @Test
    public void testObjectCallThroughs() {
        Assert.assertEquals("", engine.getItemLabel(FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(FARMER));

        Assert.assertEquals("W", engine.getItemLabel(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(WOLF));

        Assert.assertEquals("G", engine.getItemLabel(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(GOOSE));

        Assert.assertEquals("B", engine.getItemLabel(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(BEANS));
    }

    private void transport(Item item){
        engine.loadBoat(item);
        engine.rowBoat();
        engine.unloadBoat(item);
    }

    @Test
    public void testGOOSETransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));

        engine.loadBoat(FARMER);
        transport(GOOSE);
        Assert.assertEquals(Location.FINISH,engine.getItemLocation(GOOSE));

    }

    @Test
    public void testWinningGame() {

        // transport the goose
        engine.loadBoat(FARMER);
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //transport the bean
        transport(BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //go back with goose
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //transport wolf
        transport(WOLF);
        Assert.assertFalse(engine.gameIsWon());
        Assert.assertFalse(engine.gameIsLost());

        //go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //transport goose
        transport(GOOSE);
        engine.unloadBoat(FARMER);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {

        // transport the goose
        engine.loadBoat(FARMER);
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //transport wolf
        transport(WOLF);
        Assert.assertFalse(engine.gameIsWon());
        Assert.assertFalse(engine.gameIsLost());

        //go back alone
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {

        // transport the goose
        engine.loadBoat(FARMER);
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location wolfLoc = engine.getItemLocation(WOLF);
        Location goosedLoc = engine.getItemLocation(GOOSE);
        Location beansLoc = engine.getItemLocation(BEANS);
        Location farmerLoc = engine.getItemLocation(FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(WOLF);

        // check that the state has not changed
        Assert.assertEquals(wolfLoc, engine.getItemLocation(WOLF));
        Assert.assertEquals(goosedLoc, engine.getItemLocation(GOOSE));
        Assert.assertEquals(beansLoc, engine.getItemLocation(BEANS));
        Assert.assertEquals(farmerLoc, engine.getItemLocation(FARMER));
    }

    @Test
    public void testResetGame(){
        engine.resetGame();
        Assert.assertEquals(Location.START,engine.getItemLocation(WOLF));
        Assert.assertEquals(Location.START,engine.getItemLocation(GOOSE));
        Assert.assertEquals(Location.START,engine.getItemLocation(BEANS));
        Assert.assertEquals(Location.START,engine.getItemLocation(FARMER));
    }

    @Test
    public void testGetBoatLocation(){
        //at beginning at START
        Assert.assertEquals(Location.START,engine.getBoatLocation());
        engine.loadBoat(FARMER);
        transport(WOLF);
        Assert.assertEquals(Location.FINISH,engine.getBoatLocation());
        engine.rowBoat();
        Assert.assertEquals(Location.START,engine.getBoatLocation());
    }

    @Test
    public void testFullBoatCanNotLoad(){

        Assert.assertEquals(0,engine.getNumberOfPassengers());
        engine.loadBoat(FARMER);
        engine.loadBoat(GOOSE);
        Assert.assertEquals(2,engine.getNumberOfPassengers());
        engine.loadBoat(WOLF);
        Assert.assertEquals(Location.START,engine.getItemLocation(WOLF));
    }
}
