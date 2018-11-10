package TPE.Model;

import com.sun.org.glassfish.gmbal.GmbalException;


import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Table {
    private int playerId=0;
    private int playerTurn=0;
    private GameTab[][] table; // tablero
    private Stack<Move> undoMoves; // voy pusheando als movidas aca para poder ir reseteandolas
    private Player[] players; // los jugadores
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
    public void addPlayer(Color color, boolean ia){
        players[playerId]=new Player(color,playerId,ia);
        playerId++;
    }
    public void setInitialPos(){;
        table[(size/2)-1][(size/2)-1]=new GameTab(size/2-1,size/2-1, players[0]);
        table[size/2][size/2-1]=new GameTab(size/2,size/2-1, players[0]);
        table[size/2-1][size/2]=new GameTab(size/2-1,size/2,players[1]);
        table[size/2][size/2]=new GameTab(size/2,size/2,players[1]);
    }
    public Iterable<Move> getMoves(){
        Move aux;
        List<Move> list = new LinkedList<>();
        for(int i=0; i<size;i++){
            for(int j=0; j<size; j++){
                aux=isAMove(players[playerTurn], i, j);
                if(aux!=null)
                    list.add(aux);
            }
        }
        return list;
    }
    private Move isAMove(Player player, int x, int y){
        if(table[x][y].hasOwner())
            return null;
        Move aux = new Move(player, x, y);
        for(Directions dir: Directions.values()){
            int auxX=x+dir.getX();
            int auxY=y+dir.getY();
            // si hacia esa direccion esta fuera de los limites o hacia esa direccion la pieza es del jugador
            // o no hay pieza salteamos la direccion
            if (!isIn(auxX,auxY) || table[auxX][auxY].isOwner(player) || !table[auxX][auxY].hasOwner())
                continue;
            goInDir(dir,player,x,y,aux);
        }
        if(aux.isValid())
            return aux;
        return null;
    }
    private boolean goInDir(Directions dir, Player player, int x, int y, Move move){
        if (!table[x][y].hasOwner() || !isIn(x,y))
            return false;
        if (table[x][y].isOwner(player))
            return true;
        if(goInDir(dir, player, x+dir.getX(), y+dir.getY(), move))
            move.addTab(table[x][y]);
        return true;
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
        return (x<size && x > 0) && (y<size && y>0);
    }
}


