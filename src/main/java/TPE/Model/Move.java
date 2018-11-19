package TPE.Model;

import java.util.LinkedList;
import java.util.List;

public class Move {
    Point selected;
    private Player player;
    private List<Point> moveTabs;
    int points;

    public Move(Player player, int x, int y){
        points=1;
        selected = new Point(x,y);
        this.player=player;
        moveTabs= new LinkedList<>();
    }
    public void addTab(int x, int y){
        points++;
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
    public int getPoints(){return points;};
}
