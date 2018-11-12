package TPE.Model;

import javafx.scene.paint.Color;

import java.util.*;

public class Table {
    private int playerQty=0;
    private int playerTurn=0;
    private GameTab[][] table; // tablero
    private Stack<Move> undoMoves; // voy pusheando als movidas aca para poder ir reseteandolas
    private Player[] players; // los jugadores
    private Map<Point, Move> turnMoves;
    int size;

    public Table(int n){  // inicia el tablero
        undoMoves = new Stack<>();
        table = new GameTab[n][n];
        players = new Player[2];
        size=n;

        for(int i=0; i<size;i++){
            for(int j=0; j<size; j++){
                table[i][j]= new GameTab(i,j,null);
            }
        }
    }
    public int addPlayer(Color color, boolean ia){
        players[playerQty]=new Player(color,playerQty,ia);
        return playerQty++;
    }
    public void setInitialPos(){;
        table[(size/2)-1][(size/2)-1]=new GameTab(size/2-1,size/2-1, players[0]);
        table[size/2][size/2-1]=new GameTab(size/2,size/2-1, players[0]);
        table[size/2-1][size/2]=new GameTab(size/2-1,size/2,players[1]);
        table[size/2][size/2]=new GameTab(size/2,size/2,players[1]);
    }
    public Map<Point,Move> getMoves(){
        Move aux;
        Map<Point, Move> map = new HashMap<>();
        for(int i=0; i<size;i++){
            for(int j=0; j<size; j++){
                aux=isAMove(players[playerTurn], i, j);
                if(aux!=null)
                    map.put(new Point(i,j),aux);
            }
        }
        return map;
    }
    private Move isAMove(Player player, int x, int y){
        if(table[x][y].hasOwner())
            return null;
        Move aux = new Move(player,x,y);
        for(Directions dir: Directions.values()){
            int auxX=x+dir.getX();
            int auxY=y+dir.getY();
            // si hacia esa direccion esta fuera de los limites o hacia esa direccion la pieza es del jugador
            // o no hay pieza salteamos la direccion
            if (isIn(auxX,auxY) && table[auxX][auxY].hasOwner() && !table[auxX][auxY].isOwner(player))
                goInDir(dir,player,auxX,auxY,aux);
        }
        if(aux.isValid())
            return aux;
        return null;
    }
    public void start(){
        turnMoves=getMoves();
    }
    private boolean goInDir(Directions dir, Player player, int x, int y, Move move){
        if (!isIn(x,y) || !table[x][y].hasOwner())
            return false;
        if (table[x][y].isOwner(player))
            return true;
        if(goInDir(dir, player, x+dir.getX(), y+dir.getY(), move))
            move.addTab(table[x][y]);
            return false;
    }
    @Override
    public String toString(){
        StringBuilder aux = new StringBuilder();
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                aux.append(table[i][j].toString());
            }
            aux.append("\n");
        }
        return aux.toString();
    }
    public void printTestMoves(){
        System.out.println(getMoves());
    }
    private boolean isIn(int x, int y){
        return (x<size && x >= 0) && (y<size && y>=0);
    }
    public void nextTurn(){
        playerTurn=(playerTurn+1)%playerQty;
        turnMoves=getMoves();
    }
    public boolean applyMove(int x,  int y) {
        Point p=new Point(x,y);
        if(!turnMoves.containsKey(p)){
            System.out.println("No es una jugada valida");
            return false;
        }
        else{
            Move move=turnMoves.get(p);
            System.out.println(p.getX()+" "+ p.getY());
            table[move.getX()][move.getY()].setOwner(move.getPlayer());
            for(GameTab tab: move.getTabs())
                tab.setOwner(move.getPlayer());
            return true;
        }
    }
    public int getSize(){
        return size;
    }
    public Player[] getPlayers(){
        return players;
    }
    public int getPlayerQty(){
        return playerQty;
    }
    public GameTab[][] getBoard(){
        return table;
    }
}


