package TPE.Model;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Table {
    private GameTab[][] table; // tablero
    private int playerTurn; // indica el id del player que le toca jugar
    private Stack<Move> undoMoves; // voy pusheando als movidas aca para poder ir reseteandolas
    private Queue<Player> players; // los jugadores
    int size;
    public Table(int n, boolean IA){  // inicia el tablero
        undoMoves = new Stack<>();
        table = new GameTab[n][n];
        players = new LinkedList<>();
        players.add(new Player(Color.WHITE,0,true));
        players.add(new Player(Color.BLACK,1,false));
        size=n;
        setInitialPos();
    }
    private void setInitialPos(){
        table[size-1][size-1]=new GameTab();
        table[size][size-1]=new GameTab();
        table[size-1][size]=new GameTab();
        table[size][size]=new GameTab();
    }
    public void applyMove(Move move){
        for(Directions dir: Directions.values()){
            for(int i=move.; i;)
        }
    }
}


