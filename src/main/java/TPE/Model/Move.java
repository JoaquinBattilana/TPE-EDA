package TPE.Model;

import java.util.LinkedList;
import java.util.List;

public class Move {
    Point selected;
    private Player player;
    private List<Point> moveTabs;

    public Move(Player player, int x, int y){
        selected = new Point(x,y);
        this.player=player;
        moveTabs= new LinkedList<>();
    }
    public void addTab(int x, int y){
        moveTabs.add(new Point(x,y));
    }
    public boolean isValid(){
        return !moveTabs.isEmpty();
    }
    public Player getPlayer(){
        return player;
    }
    public Iterable<Point> getTabs(){
        return moveTabs;
    }
    public Point getSelected(){
        return selected;
    }
}
