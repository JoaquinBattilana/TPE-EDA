package TPE.Model;

import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Reversi{
    private boolean prune;
    private int param;
    private boolean mode; // si es true es modo de profundidad, si no es modo de tiempo
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

    public Reversi(Board b){
        this.board=b;
        size=board.getSize();
        dotTree = new StringBuilder();
        undoMoves = new Stack<>();
        players = new Player[2];
    }

    public void setParam(boolean type, int n){
        mode=type;
        this.param=n;
    }
    public void setPrune(boolean prune){
        this.prune=prune;
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
        board.setTab(size/2,(size/2)-1,1);
        board.setTab((size/2)-1,size/2,1);
        board.setTab(size/2,size/2,0);
    }
    public void setPlayerPoints(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(board.getTable()[i][j]!=-1)
                    players[board.getTable()[i][j]].incrementPoints();
            }
        }
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


    public Move getBestTimeMove(int time){
        long startTime = System.currentTimeMillis();
        long endTime = startTime + time*1000;
        Move best=null;
        for(int i=0; startTime<endTime; i++){
            best=getBestDeepthMove(i);
        }
        return best;
    }


    public Move getBestDeepthMove(int deepth){
        int actual;
        int min=Integer.MIN_VALUE;
        Point bestMove=null;
        for(Point move: turnMoves.keySet()){
            dotTree.append("\n" + "START--"+ move.toString() + "--");
            board.applyMove(turnMoves.get(move));
            if ((actual = deepthMiniMax(board, players[(playerTurn + 1) % playerQty], false, deepth-1, Integer.MIN_VALUE,Integer.MAX_VALUE)) >= min) {
                min = actual;
                bestMove = move;
            }
            board.applyReverseMove(turnMoves.get(move));
        }
        return turnMoves.get(bestMove);
    }

    public int deepthMiniMax(Board board, Player playerTurn, boolean max, int deepth, int alpha, int beta){
        if(deepth==0) {
            int rta = heuristic(board, playerTurn, max);
            dotTree.append(""+ rta+ "\n");
            return rta;
        }
        List<Move> moves = new ArrayList<>(board.getMoves(playerTurn).values());
        if(moves.isEmpty()){
            if(gameFinished()) {
                int rta = heuristic(board, playerTurn, max);
                dotTree.append(""+rta+"\n");
                return rta;
            }
            return deepthMiniMax(board, players[(playerTurn.getId()+1)%2], !max,deepth-1, alpha, beta);
        }
        int actual;
        int m;
        if(max){
            m=Integer.MIN_VALUE;
            for(Move move: moves){
                dotTree.append(move.toString()+ "--");
                board.applyMove(move);
                if((actual=deepthMiniMax(board, players[(playerTurn.getId()+1)%playerQty],false, deepth-1, alpha, beta))>=m){
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
            dotTree.append(move.toString()+ "--");
            board.applyMove(move);
            if((actual=deepthMiniMax(board, players[(playerTurn.getId()+1)%playerQty],true, deepth-1, alpha, beta))<=m){
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

    public boolean makeIaMove(){
        if (players[playerTurn].isIa()){
            dotTree = new StringBuilder();
            dotTree.append("graph ia_move {");
            if(!turnMoves.isEmpty()){
                Move aux;
                if(mode) {
                    aux = getBestDeepthMove(param);
                }
                else{
                    aux = getBestTimeMove(param);
                }
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


