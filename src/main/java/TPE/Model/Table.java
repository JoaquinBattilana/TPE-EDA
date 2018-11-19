package TPE.Model;

import javafx.scene.paint.Color;

import java.util.*;

public class Table {
    private int playerQty=0;
    private int playerTurn=0;
    private int[][] table; // tablero
    private Stack<Move> undoMoves; // voy pusheando als movidas aca para poder ir reseteandolas
    private Player[] players; // los jugadores
    private Map<Point, Move> turnMoves;
    int size;

    public Table(int n){  // inicia el tablero
        undoMoves = new Stack<>();
        table = new int[n][n];
        players = new Player[2];
        size=n;

        for(int i=0; i<size;i++){
            for(int j=0; j<size; j++){
                table[i][j]= -1;
            }
        }
    }
    public boolean gameFinished(){
        boolean flag=true;
        for(int i=0; i<playerQty && flag; i++){
            if(!getPlayerMoves(players[i]).isEmpty())
                flag=false;
        }
        return flag;
    }

    public int addPlayer(Color color, boolean ia){
        players[playerQty]=new Player(color,playerQty,ia);
        return playerQty++;
    }

    public void setInitialPos(){
        table[(size/2)-1][(size/2)-1]=0;
        table[size/2][size/2-1]=0;
        table[size/2-1][size/2]=1;
        table[size/2][size/2]=1;
        players[0].incrementPoints();
        players[0].incrementPoints();
        players[1].incrementPoints();
        players[1].incrementPoints();
    }

    public Map<Point,Move> getPlayerMoves(Player player){
        Move aux;
        Map<Point, Move> map = new HashMap<>();
        for(int i=0; i<size;i++){
            for(int j=0; j<size; j++){
                aux=isAMove(player, i, j);
                if(aux!=null)
                    map.put(new Point(i,j),aux);
            }
        }
        return map;
    }

    public Map<Point,Move> getMoves(){
        return getPlayerMoves(players[playerTurn]);
    }
    private Move isAMove(Player player, int x, int y){
        if(table[x][y]!=-1)
            return null;
        Move aux = new Move(player,x,y);
        for(Directions dir: Directions.values()){
            int auxX=x+dir.getX();
            int auxY=y+dir.getY();
            // si hacia esa direccion esta fuera de los limites o hacia esa direccion la pieza es del jugador
            // o no hay pieza salteamos la direccion
            if (isIn(auxX,auxY) && table[auxX][auxY]!=-1 && table[auxX][auxY] != player.getId())
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
        if (!isIn(x,y) || table[x][y] == -1)
            return false;
        if (table[x][y] == player.getId())
            return true;
        if(goInDir(dir, player, x+dir.getX(), y+dir.getY(), move)) {
            move.addTab(x,y);
            return true;
        }
        return false;
    }
    @Override
    public String toString(){
        StringBuilder aux = new StringBuilder();
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                aux.append(table[i][j]);
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
            return false;
        }
        else{
            Move move=turnMoves.get(p);
            System.out.println(p.getX()+" "+ p.getY());
            table[move.getSelected().getX()][move.getSelected().getY()] = move.getPlayer().getId();
            move.getPlayer().incrementPoints();
            for(Point tab: move.getTabs()){
                players[table[tab.getX()][tab.getY()]].decrementPoints();
                table[tab.getX()][tab.getY()] = move.getPlayer().getId();
                move.getPlayer().incrementPoints();
            }
            undoMoves.push(move);
            return true;
        }
    }
    public boolean undoMove(){
        Move aux = undoMoves.pop();
        table[aux.getSelected().getX()][aux.getSelected().getY()] = -1;
        aux.getPlayer().decrementPoints();
        for(Point tab: aux.getTabs()) {
            players[table[tab.getX()][tab.getY()]].decrementPoints();
            table[tab.getX()][tab.getY()] = players[(aux.getPlayer().getId() + 1) % playerQty].getId();
            players[table[tab.getX()][tab.getY()]].incrementPoints();
        }
        return true;
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
    public int[][] getBoard(){
        return table;
    }
    public Stack<Move> getUndoMoves(){return undoMoves;}
}


