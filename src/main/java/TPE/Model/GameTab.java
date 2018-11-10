package TPE.Model;

import java.awt.*;

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
    void flip(){} // funcion que flipea la ficha

    @Override
    public String toString(){
        if(owner==null)
            return "vacio";
        return "("+owner.getId()+","+x+","+y+")";
    }
}
