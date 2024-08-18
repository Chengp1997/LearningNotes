package river;

import java.awt.*;

public class FarmerGameEngine extends AbstractGameEngine{

    private final Item BEANS = Item.ITEM_0;
    private final Item GOOSE = Item.ITEM_1;
    private final Item WOLF = Item.ITEM_2;
    private final Item FARMER = Item.ITEM_3;

    public FarmerGameEngine() {
        GameObject wolf = new GameObject("W", Location.START, Color.CYAN);
        GameObject goose = new GameObject("G", Location.START, Color.CYAN);
        GameObject beans = new GameObject("B", Location.START, Color.CYAN);
        GameObject farmer = new GameObject("", Location.START, Color.MAGENTA);
        items.put(WOLF, wolf);
        items.put(GOOSE, goose);
        items.put(BEANS, beans);
        items.put(FARMER, farmer);
    }

    public int numberOfItems(){
        return items.size();
    }

    public void rowBoat() {
        if (getItemLocation(FARMER)!=Location.BOAT)return;
        super.rowBoat();
    }

    public boolean gameIsLost() {
        if (getItemLocation(GOOSE) == Location.BOAT
                || getItemLocation(GOOSE) == getItemLocation(FARMER)
                || getItemLocation(GOOSE) == getBoatLocation()) {
            return false;
        }

        if (getItemLocation(GOOSE) == getItemLocation(WOLF)
                || getItemLocation(GOOSE) == getItemLocation(BEANS)) {
            return true;
        }
        return false;
    }
}
