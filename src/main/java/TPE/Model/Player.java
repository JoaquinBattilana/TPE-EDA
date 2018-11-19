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
    public void incrementNPoints(int n){
        points+=n;
    }
    public void decrementNPoints(int n){
        points-=n;
    }
    public int getId(){
        return playerId;
    }
    public void incrementPoints(){
        points++;
    }
    public void decrementPoints(){
        points--;
    }
    public int getPoints(){
        return points;
    }
    public boolean isIa(){return ia;}
    public void setPoints(int n){
        this.points=n;
    }
    public Color getColor(){return color;};
}
