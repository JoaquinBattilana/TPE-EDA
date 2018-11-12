package TPE.Model;

import javafx.beans.value.ObservableStringValue;
import javafx.scene.paint.Color;

public class GameTab {
    private int x; // posicion que ocupan en el tablero
    private int y;
    private Player owner;

    public GameTab(int x, int y, Player player){
        this.x=x;
        this.y=y;
        this.owner=player;
    }

    public boolean isOwner(Player player){
        return this.owner==player;
    }
    public boolean hasOwner(){
        return owner!=null;
    }
    public void setOwner(Player player){
        this.owner=player;
    }
    @Override
    public String toString(){
        if(owner==null)
            return "(0,0,0)";
        return "("+owner.getId()+","+x+","+y+")";
    }
    public Player getOwner(){
        return owner;
    }
}
