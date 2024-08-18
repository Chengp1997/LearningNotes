package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGameEngine implements GameEngine{
    protected final Map<Item, GameObject> items;
    private Location boatLocation;
    private int numbersOfPassengersOnBoat=0;

    protected AbstractGameEngine() {
        items = new HashMap<>();
        boatLocation=Location.START;
    }

    @Override
    public abstract int numberOfItems();

    @Override
    public String getItemLabel(Item item) {
        return items.get(item).getLabel();
    }

    @Override
    public Color getItemColor(Item item) {
        return items.get(item).getColor();
    }

    @Override
    public Location getItemLocation(Item item) {
        return items.get(item).getLocation();
    }

    @Override
    public void setItemLocation(Item item, Location location) {
        items.get(item).setLocation(location);
    }

    @Override
    public Location getBoatLocation() {
        return boatLocation;
    }

    @Override
    public void loadBoat(Item id) {
        //the same shore
        if (getItemLocation(id) == boatLocation) {
            if (getNumberOfPassengers()<2){
                setItemLocation(id,Location.BOAT);
                numbersOfPassengersOnBoat++;
            }
        }
    }

    @Override
    public void unloadBoat(Item id) {
        if (getItemLocation(id) == Location.BOAT) {
            setItemLocation(id,boatLocation);
            numbersOfPassengersOnBoat--;
        }
    }

    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        }
        else {
            boatLocation = Location.START;
        }
    }

    @Override
    public boolean gameIsWon() {
        for (Item item:Item.values()){
            if (!(item.ordinal()<numberOfItems()))break;
            if (getItemLocation(item) != Location.FINISH) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean gameIsLost() {
        return false;
    }

    @Override
    public void resetGame() {
        for (Item item:Item.values()){
            if (!(item.ordinal()<numberOfItems()))break;
            setItemLocation(item,Location.START);
        }
        numbersOfPassengersOnBoat=0;
        boatLocation = Location.START;
    }

    public int getNumberOfPassengers(){
        return numbersOfPassengersOnBoat;
    }

}
