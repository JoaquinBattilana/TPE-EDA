package TPE.Model;

public class Move {
    int [] startingPos;
    int [] selectedPos;
    int playerId;
    Directions direc;

    public Move(int[] startingPos, int[] selectedPos, int playerId, Directions direc){
        this.startingPos=startingPos;
        this.selectedPos=selectedPos;
        this.playerId=playerId;
        this.direc=direc;
    }
    public boolean checkMove(Player player, GameTab[][] board, int[] startingPos, int size){
        int auxX;
        int auxY;
        for(Directions direc: Directions.values()){
            auxX=startingPos[0] + direc.getX();
            auxY=startingPos[1] + direc.getY();
            while(auxX<size && auxY<size){
                    
            }
        }
        return false;
    }
}
