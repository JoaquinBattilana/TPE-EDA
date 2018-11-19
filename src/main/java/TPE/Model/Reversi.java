package TPE.Model;

import javafx.scene.paint.Color;

import java.util.*;

public class Reversi {
    int size;
    Board board;
    private int playerQty=0;
    private int playerTurn=0;
    private Stack<Move> undoMoves; // voy pusheando als movidas aca para poder ir reseteandolas
    private Player[] players; // los jugadores
    private Map<Point, Move> turnMoves;

    public Reversi(int n){  // inicia el tablero
        undoMoves = new Stack<>();
        players = new Player[2];
        board = new Board(n);
        size=n;
    }
    public boolean gameFinished(){
        boolean flag=true;
        for(int i=0; i<playerQty && flag; i++){
            if(!board.getMoves(players[i]).isEmpty())
                flag=false;
        }
        return flag;
    }

    public void setInitialPos(){
        board.setTab((size/2)-1,(size/2)-1,0);
        board.setTab(size/2,(size/2)-1,0);
        board.setTab((size/2)-1,size/2,1);
        board.setTab(size/2,size/2,1);
        players[0].incrementPoints();
        players[0].incrementPoints();
        players[1].incrementPoints();
        players[1].incrementPoints();
    }

    public int addPlayer(Color color, boolean ia){
        players[playerQty]=new Player(color,playerQty,ia);
        return playerQty++;
    }

    public void start(){
        turnMoves=board.getMoves(players[playerTurn]);
    }

    public void nextTurn(){
        playerTurn=(playerTurn+1)%playerQty;
        turnMoves=board.getMoves(players[playerTurn]);
    }

    public boolean applyMove(int x,  int y) {
        Point p=new Point(x,y);
        if(!turnMoves.containsKey(p))
            return false;
        Move aux = turnMoves.get(p);
        board.applyMove(aux);
        aux.getPlayer().incrementNPoints(aux.getPoints());
        players[(aux.getPlayer().getId()+1)%playerQty].decrementNPoints(aux.getPoints()-1);
        undoMoves.push(aux);
        return true;
    }
    public boolean undoMove(){
        if(undoMoves.isEmpty())
            return false;
        Move aux = undoMoves.pop();
        board.applyReverseMove(aux);
        aux.getPlayer().decrementNPoints(aux.getPoints());
        players[(aux.getPlayer().getId() + 1) % playerQty].incrementNPoints(aux.getPoints()-1);
        return true;
    }


    public int getSize(){
        return size;
    }
/*
    public Move getBestDeepthMove(){
        int aux;
        int min=Integer.MIN_VALUE;
        Point bestMove;
        for(Point move: turnMoves.keySet()){
            if(aux=deepthMinimax(board))
        }
    }
*/

    public Player[] getPlayers(){
        return players;
    }
    public int getPlayerQty(){
        return playerQty;
    }
    public Board getBoard(){
        return board;
    }

    public Map<Point,Move> getMoves(){
        return turnMoves;
    }

    public Stack<Move> getUndoMoves(){return undoMoves;}
}


