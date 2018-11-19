package TPE.Model;


import java.util.HashMap;
import java.util.Map;

public class Board {
    private int[][] table; // tablero
    int size;

    public Board(int n) {  // inicia el tablero
        table = new int[n][n];
        size = n;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = -1;
            }
        }
    }
    public void setTab(int x, int y, int playerId){
        table[x][y] = playerId;
    }

    public Board getCopy(){
        Board aux = new Board(size);
        for(int i=0; i<size ; i++){
            for(int j=0; j<size ; j++){
                aux.table[i][j] = table[i][j];
            }
        }
        return aux;
    }

    public Map<Point,Move> getMoves(Player player){
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

    public int [][] getTable(){
        return table;
    }

    private Move isAMove(Player player, int x, int y){
        if(table[x][y]!=-1)
            return null;
        Move aux = new Move(player,x,y);
        for(Directions dir: Directions.values()){
            int auxX=x+dir.getX();
            int auxY=y+dir.getY();
            if (isIn(auxX,auxY) && table[auxX][auxY]!=-1 && table[auxX][auxY] != player.getId())
                goInDir(dir,player,auxX,auxY,aux);
        }
        if(aux.isValid())
            return aux;
        return null;
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

    private boolean isIn(int x, int y){
        return (x<size && x >= 0) && (y<size && y>=0);
    }

    public void applyMove(Move move) {
            table[move.getSelected().getX()][move.getSelected().getY()] = move.getPlayer().getId();
            for(Point tab: move.getTabs()){
                table[tab.getX()][tab.getY()] = move.getPlayer().getId();
            }
    }

    public void applyReverseMove(Move move){
        table[move.getSelected().getX()][move.getSelected().getY()] = -1;
        for(Point tab: move.getTabs()) {
            table[tab.getX()][tab.getY()]=(table[tab.getX()][tab.getY()] + 1 )% 2;
        }
    }
}

