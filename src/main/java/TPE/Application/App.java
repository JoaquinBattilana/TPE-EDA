package TPE.Application;

import TPE.Model.Table;

import java.awt.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Table t = new Table(4);
        t.addPlayer(Color.WHITE,false);
        t.addPlayer(Color.black, false);
        t.setInitialPos();
        System.out.println(t);
        t.printTestMoves();
    }
}
