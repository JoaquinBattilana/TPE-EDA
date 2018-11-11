package TPE.Model;

import java.util.LinkedList;
import java.util.List;

public class Move {
    private Player player;
    private List<GameTab> moveTabs;

    public Move(Player player){
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
}
