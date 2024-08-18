package river;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Fields (hotspots)
    // ==========================================================
    //base (x,y) for items and boat
    private final int leftBaseX=20;
    private final int leftBaseY=275;
    private final int rightBaseX=670;
    private final int rightBaseY=275;
    private final int leftBoatX=140;
    private final int rightBoatX=550;
    private final int itemWidth=50;
    private final int itemHeight=50;
    private final int boatWidth = 110;
    private final int boatHeight = 50;
    private final int boatY = 275;

    //offset for the base (X,Y)
    int[] offsetX = {0, 60, 0, 60, 0, 60};
    int[] offsetY = {0, 0, -60, -60, -120, -120};
    int passengerOffset = 0;

    private Rectangle boatRectangle;
    private final Rectangle farmerButtonRect = new Rectangle(250, 120, 100, 30);
    private final Rectangle monsterButtonRect = new Rectangle(400, 120, 100, 30);

    // ==========================================================
    // Private Fields
    // ==========================================================

    private GameEngine engine; // Model
    private GameEngine[] engines;
    private boolean restart = false;

    // ==========================================================
    // Constructor
    // ==========================================================

    Map<Item,Rectangle> itemRectangleMap;
    public RiverGUI() {
        engines=new GameEngine[]{new FarmerGameEngine(),new MonsterGameEngine()};
        engine = engines[0];
        itemRectangleMap = new HashMap<>();
        addMouseListener(this);
    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    Graphics g;

    @Override
    public void paintComponent(Graphics g) {
        this.g=g;

        //update status
        updateItemRectangle();
        updateBoatRectangle();

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //paint all the rectangle
        for (Item item: Item.values()){
            if (!(item.ordinal()<engine.numberOfItems())) break;
            paintRectangle(itemRectangleMap.get(item),engine.getItemLabel(item),engine.getItemColor(item));
        }
        paintBoat();


        String message = engine.gameIsLost() ? "You Lost!" : engine.gameIsWon()? "You Won!" : "";
        paintMessage(message);

        restart=(engine.gameIsLost() || engine.gameIsWon()) || restart;
        if (gameOver()) {
            paintButton();
        }

    }

    private Boolean gameOver(){
        return (engine.gameIsLost()||engine.gameIsWon());
    }

    //update position
    public void updateItemRectangle(Item item){
        Rectangle temp;
        if (engine.getItemLocation(item)==Location.START){
            temp=new Rectangle(leftBaseX+offsetX[item.ordinal()],leftBaseY+offsetY[item.ordinal()],itemWidth,itemHeight);
        }else if (engine.getItemLocation(item)==Location.FINISH){
            temp=new Rectangle(rightBaseX+offsetX[item.ordinal()],rightBaseY+offsetY[item.ordinal()],itemWidth,itemHeight);
        }else {
            int boatX=(engine.getBoatLocation()==Location.START)? leftBoatX:rightBoatX;

            temp=new Rectangle(boatX+passengerOffset,boatY-60, itemWidth,itemHeight);
            passengerOffset=(passengerOffset+60)%120;
        }
        itemRectangleMap.put(item,temp);
    }

    public void updateItemRectangle(){
        for (Item item: Item.values()){
            if (!(item.ordinal()<engine.numberOfItems()))break;
            updateItemRectangle(item);
        }
    }

    public void updateBoatRectangle(){
        if (engine.getBoatLocation()==Location.START){
            boatRectangle=new Rectangle(leftBoatX,boatY,boatWidth,boatHeight);
        }else {
            boatRectangle=new Rectangle(rightBoatX,boatY,boatWidth,boatHeight);
        }
    }

    //paint rectangle
    public void paintRectangle(Rectangle rectangle,String str, Color color) {

        //paint rectangle
        g.setColor(color);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        if (str==null) return;

        //paint String
        g.setColor(Color.BLACK);
        int fontSize = (rectangle.height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = rectangle.x + rectangle.width / 2 - fm.stringWidth(str) / 2;
        int strYCoord = rectangle.y + rectangle.height / 2 + fontSize / 2 - 4;
        g.drawString(str, strXCoord, strYCoord);
    }

    public void paintBoat(){
        paintRectangle(boatRectangle,"",Color.ORANGE);
    }

    public void paintStringInRectangle(String str, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        int fontSize = (height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = x + width / 2 - fm.stringWidth(str) / 2;
        int strYCoord = y + height / 2 + fontSize / 2 - 4;
        g.drawString(str, strXCoord, strYCoord);
    }

    public void paintMessage(String message) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    public void paintButton() {
        g.setColor(Color.BLACK);
        paintBorder(farmerButtonRect, 3);
        paintBorder(monsterButtonRect,3);
        g.setColor(Color.PINK);
        paintRectangle(farmerButtonRect, "Farmer",Color.PINK);
        paintStringInRectangle("Farmer", farmerButtonRect.x, farmerButtonRect.y, farmerButtonRect.width,
                farmerButtonRect.height);
        paintRectangle(monsterButtonRect,"Monster",Color.PINK);
        paintStringInRectangle("Monster",monsterButtonRect.x,monsterButtonRect.y,monsterButtonRect.width,
                monsterButtonRect.height);

    }

    public void paintBorder(Rectangle r, int thickness) {
        g.fillRect(r.x - thickness, r.y - thickness, r.width + (2 * thickness), r.height + (2 * thickness));
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
            if (this.farmerButtonRect.contains(e.getPoint())) {
                engine=engines[0];
            }else if (this.monsterButtonRect.contains(e.getPoint())){
                engine=engines[1];
            }
            engine.resetGame();
            restart = false;
            repaint();
            return;
        }


        //load items on boat
        for (Item item:Item.values()){
            if (item.ordinal() >= engine.numberOfItems()) {
                break;
            }
            if (itemRectangleMap.get(item).contains(e.getPoint())){
                //on the shore, can be load on boat.
                if (engine.getItemLocation(item)==Location.START||engine.getItemLocation(item)==Location.FINISH){
                    engine.loadBoat(item);
                }
                //on the boat, unload the boat.
                else if (engine.getItemLocation(item)==Location.BOAT){
                    engine.unloadBoat(item);
                }
            }
        }
        //check whether can row the boat
        if (boatRectangle.contains(e.getPoint())){
            engine.rowBoat();
        }
        repaint();
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
