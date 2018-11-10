package TPE.Model;

public enum Directions { // voy a tratar las direcciones como coordenadas para mas comodidad

    NORTH(-1,0),
    EAST(0,1),
    WEST(0,-1),
    SOUTH(1,0),
    NORTHEAST(-1,1),
    NORTWEST(-1,-1),
    SOUTHEAST(1,1),
    SOUTHWEST(1,-1);

    private int x;
    private int y;

    private Directions(int x, int y){
        this.x=x;
        this.y=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
