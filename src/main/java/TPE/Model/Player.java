package TPE.Model;

import java.awt.*;

public class Player {
    int points; // cantidad de fichas
    boolean ia; // es ia o jugador
    int playerId;
    Color color;
    public Player(Color color, int playerId, boolean ia){
        this.ia=ia;
        this.playerId=playerId;
        this.color=color;
        points=0;
    }
    @Override
    public boolean equals(Object o){
        if(this==o)
            return true;
        if(!(o instanceof Player))
            return false;
        Player aux = (Player) o;
        return aux.playerId==this.playerId;
    }
    public int getId(){
        return playerId;
    }
}
