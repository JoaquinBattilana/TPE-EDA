package TPE.Application;

import TPE.Model.Move;
import TPE.Model.Table;

import java.awt.*;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Table t = new Table(10);
        t.addPlayer(Color.WHITE,false);
        t.addPlayer(Color.BLACK, false);
        t.setInitialPos();
        System.out.println(t);
        Iterable<Move> list = t.getMoves();
        t.applyMove(list.iterator().next());
        System.out.println(t);
    }
}
