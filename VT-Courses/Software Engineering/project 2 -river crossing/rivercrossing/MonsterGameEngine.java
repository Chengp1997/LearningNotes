package river;

import java.awt.*;

public class MonsterGameEngine extends AbstractGameEngine{

    private final Item MONSTER1 = Item.ITEM_0;
    private final Item MONSTER2 = Item.ITEM_2;
    private final Item MONSTER3 = Item.ITEM_4;
    private final Item MUNCHKIN1 = Item.ITEM_1;
    private final Item MUNCHKIN2 = Item.ITEM_3;
    private final Item MUNCHKIN3 = Item.ITEM_5;

    public MonsterGameEngine() {
        GameObject monster1 = new GameObject("M1", Location.START, Color.RED);
        GameObject monster2 = new GameObject("M2", Location.START, Color.RED);
        GameObject monster3 = new GameObject("M3", Location.START, Color.RED);
        GameObject munchkin1 = new GameObject("K1", Location.START, Color.GREEN);
        GameObject munchkin2 = new GameObject("K2", Location.START, Color.GREEN);
        GameObject munchkin3 = new GameObject("K3", Location.START, Color.GREEN);
        items.put(MONSTER1, monster1);
        items.put(MONSTER2, monster2);
        items.put(MONSTER3, monster3);
        items.put(MUNCHKIN1, munchkin1);
        items.put(MUNCHKIN2, munchkin2);
        items.put(MUNCHKIN3, munchkin3);
    }

    @Override
    public void rowBoat() {
        if (getNumberOfPassengers()==0)return;
        super.rowBoat();
    }

    @Override
    public boolean gameIsLost() {
        if (munchkinsAtSTART()>0&& monstersAtSTART()>munchkinsAtSTART())return true;
        if (munchkinsAtFINISH()>0&& monstersAtFINISH()>munchkinsAtFINISH())return true;
        return false;
    }

    private boolean itemsAtStart(Item item){
        //on the shore
        if (getItemLocation(item)==Location.START)return true;
        //on the boat, boat on the left shore
        if (getItemLocation(item)==Location.BOAT&&getBoatLocation()==Location.START)return true;
        return false;
    }

    private boolean itemsAtFinish(Item item){
        if (getItemLocation(item)==Location.FINISH)return true;
        if (getItemLocation(item)==Location.BOAT&& getBoatLocation()==Location.FINISH)return true;
        return false;
    }

    private int munchkinsAtSTART(){
        int count=0;
        for (Item item:Item.values()){
            if (!(item.ordinal()<numberOfItems()))break;
            if (item.ordinal()%2==0) continue;
            if (itemsAtStart(item)) count++;
        }
        return count;
    }

    private int monstersAtSTART(){
        int count=0;
        for (Item item:Item.values()){
            if (!(item.ordinal()<numberOfItems()))break;
            if (item.ordinal()%2==1) continue;
            if (itemsAtStart(item)) count++;
        }
        return count;
    }

    private int munchkinsAtFINISH(){
        int count=0;
        for (Item item:Item.values()){
            if (!(item.ordinal()<numberOfItems()))break;
            if (item.ordinal()%2==0) continue;
            if (itemsAtFinish(item)) count++;
        }
        return count;
    }

    private int monstersAtFINISH(){
        int count=0;
        for (Item item:Item.values()){
            if (!(item.ordinal()<numberOfItems()))break;
            if (item.ordinal()%2==1) continue;
            if (itemsAtFinish(item)) count++;
        }
        return count;
    }

    @Override
    public int numberOfItems() {
        return items.size();
    }
}
