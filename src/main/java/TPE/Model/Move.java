package TPE.Model;

import java.util.LinkedList;
import java.util.List;

public class Move {
    private int x;
    private int y;
    private Player player;
    private List<GameTab> moveTabs;

    public Move(Player player, int x, int y){
        this.x=x;
        this.y=y;
        this.player=player;
        moveTabs= new LinkedList<>();
    }
    public void addTab(GameTab tab){
        moveTabs.add(tab);
    }
    public boolean isValid(){
        return !moveTabs.isEmpty();
    }
    public Player getPlayer(){
        return player;
    }
    public Iterable<GameTab> getTabs(){
        return moveTabs;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
