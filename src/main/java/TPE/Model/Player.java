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
}
