package TPE.Model;

import java.util.LinkedList;
import java.util.List;

public class Move {
    int x;
    int y;
    Player player;
    List<GameTab> moveTabs;

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
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
}
