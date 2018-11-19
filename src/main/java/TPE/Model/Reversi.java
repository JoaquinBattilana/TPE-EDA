package TPE.Model;

import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Reversi {
    private StringBuilder dotTree=null;
    int size;
    Board board;
    private int playerQty=0;
    private int playerTurn=0;
    private Stack<Move> undoMoves; // voy pusheando als movidas aca para poder ir reseteandolas
    private Player[] players; // los jugadores
    private Map<Point, Move> turnMoves;

    public Reversi(int n){  // inicia el tablero
        dotTree = new StringBuilder();
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


    public Move getBestDeepthMove(int deepth, boolean prune){
        int actual;
        int min=Integer.MIN_VALUE;
        Point bestMove=null;
        for(Point move: turnMoves.keySet()){
            dotTree.append("\n" + move.toString());
            board.applyMove(turnMoves.get(move));
            if ((actual = deepthMiniMax(board, players[(playerTurn + 1) % playerQty], false, deepth-1, prune, Integer.MIN_VALUE,Integer.MAX_VALUE)) >= min) {
                min = actual;
                bestMove = move;
            }
            board.applyReverseMove(turnMoves.get(move));
        }
        return turnMoves.get(bestMove);
    }

    public int deepthMiniMax(Board board, Player playerTurn, boolean max, int deepth, boolean prune, int alpha, int beta){
        if(deepth==0)
            return heuristic(board,playerTurn,max);
        List<Move> moves = new ArrayList<>(board.getMoves(playerTurn).values());
        if(moves.isEmpty()){
            if(gameFinished())
                return heuristic(board,playerTurn,max);
            return deepthMiniMax(board, players[(playerTurn.getId()+1)%2], !max,deepth-1, prune, alpha, beta);
        }
        int actual;
        int m;
        if(max){
            m=Integer.MIN_VALUE;
            for(Move move: moves){
                board.applyMove(move);
                if((actual=deepthMiniMax(board, players[(playerTurn.getId()+1)%playerQty],false, deepth-1, prune, alpha, beta))>=m){
                    m = actual;
                }
                alpha = Math.max(alpha, m);
                board.applyReverseMove(move);
                if(prune && alpha >= beta)
                    break; // poda
            }
            return m;
        }
        m=Integer.MAX_VALUE;
        for(Move move: moves){
            board.applyMove(move);
            if((actual=deepthMiniMax(board, players[(playerTurn.getId()+1)%playerQty],true, deepth-1,prune, alpha, beta))<=m){
                m = actual;
            }
            beta = Math.min(beta, m);
            board.applyReverseMove(move);
            if(prune && alpha >= beta)
                break;
        }
        return m;
    }

    public int heuristic(Board b,Player player, boolean max){
        int rta;
        if(gameFinished()){
            if(max)
                rta = Integer.MAX_VALUE;
            else
                rta = Integer.MIN_VALUE;
        }
        else
            rta = b.getMoves(player).size();
        return rta;
    }

    public boolean makeIaMove(int deepth, boolean prune){
        if (players[playerTurn].isIa()){
            dotTree = new StringBuilder();
            dotTree.append("graph ia_move {");
            if(!turnMoves.isEmpty()){
                Move aux = getBestDeepthMove(deepth, prune);
                board.applyMove(aux);
                aux.getPlayer().incrementNPoints(aux.getPoints());
                players[(aux.getPlayer().getId()+1)%playerQty].decrementNPoints(aux.getPoints()-1);
                undoMoves.push(aux);
            }
            dotTree.append("}");
            return true;
        }
        return false;
    }

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

    public String getDotTree(){
        return dotTree.toString();
    }

    public Stack<Move> getUndoMoves(){return undoMoves;}
}


