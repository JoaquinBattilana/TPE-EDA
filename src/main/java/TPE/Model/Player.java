package TPE.Model;


import javafx.scene.paint.Color;

public class Player {
    private int points; // cantidad de fichas
    private boolean ia; // es ia o jugador
    private int playerId;
    private Color color;
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
    public Color getColor(){return color;};
}
