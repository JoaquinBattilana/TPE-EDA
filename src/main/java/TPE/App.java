package TPE;

import TPE.Controller.TableController;
import TPE.Exceptions.ExceptionPopup;
import TPE.Model.Board;
import TPE.Model.Reversi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.util.List;


public class App extends Application
{
    private static String[] parameters1 = {"-ai", "-mode", "-param", "-prune", "-load"};
    private static String[] parameters2 = {"-size","-ai", "-mode", "-param", "-prune"};
    public enum SEARCHTYPE{ TIME, DEEPTH};
    private int size=8;
    private int ai=3;
    private SEARCHTYPE searchtype = SEARCHTYPE.DEEPTH;
    private int param=5;
    boolean prune=false;
    private String savegame=null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parameters p = getParameters();
        List<String> parameters = p.getRaw();
        if(!loadParams(parameters)) {
            System.out.println("Los parametros son invalidos");
            System.exit(1);
        }
        Reversi t;
        Board b;
        if(savegame!=null) {
            try {
                FileInputStream fileIn = new FileInputStream("C:/Users/J/Desktop/ready");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                b = (Board) in.readObject();
                t=new Reversi(b);
                in.close();
                fileIn.close();
            } catch (Exception e) {
                System.out.println("Hubo un error, el archivo no existe o esta corrupto");
                return;
            }
        }
        else {
            t=new Reversi(size);
            t.setInitialPos();
        }
        switch(ai){
            case 0:
                t.addPlayer(Color.BLACK,false);
                t.addPlayer(Color.WHITE, false);
                break;
            case 1:
                t.addPlayer(Color.BLACK,true);
                t.addPlayer(Color.WHITE, false);
                break;
            case 2:
                t.addPlayer(Color.BLACK,false);
                t.addPlayer(Color.WHITE, true);
                break;
            case 3:
                t.addPlayer(Color.BLACK, true);
                t.addPlayer(Color.WHITE, true);
        }
        switch(searchtype){
            case TIME:
                t.setParam(false, param);
            case DEEPTH:
                t.setParam(true, param);
        }
        t.setPrune(prune);
        t.setPlayerPoints();
        t.start();
        primaryStage.setTitle("Reversi");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Table.fxml"));
        Parent root=loader.load();
        TableController controller= loader.getController();
        controller.initModel(t);
        controller.setView();
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public boolean loadParams(List <String> parameters){
        if(parameters.size()!=10)
            return false;
        if(parameters.get(0).equals("-size")) {
            int aux = Integer.parseInt(parameters.get(1));
            if(!(aux==4 || aux==6 || aux==8 || aux==10))
                return false;
            size=aux;
            return loadParams1(parameters);
        }
        else if(parameters.get(0).equals("-ai")){
            int aux = Integer.parseInt(parameters.get(1));
            if(!(aux==0 || aux==1 || aux==2))
                return false;
            else
                ai=aux;
            return loadParams2(parameters);
        }
        return false;
}

    public boolean loadParams2(List<String>parameters){
        if (parameters.get(2).equals("-mode")) {
            if (parameters.get(3).equals("time"))
                searchtype = SEARCHTYPE.TIME;
            else if (parameters.get(3).equals("depth"))
                searchtype = SEARCHTYPE.DEEPTH;
            else
                return false;
        } else
            return false;
        if (parameters.get(4).equals("-param")) {
            int aux = Integer.parseInt(parameters.get(5));
            param = aux;
        }
        else
            return false;
        if (parameters.get(6).equals("-prune")){
            if(parameters.get(7).equals("on"))
                prune=true;
            else if(parameters.get(7).equals("off"))
                prune=false;
            else
                return false;
        }
        else
            return false;
        if(parameters.get(8).equals("-load")){
            savegame=parameters.get(9);
        }
        else
            return false;
        return true;
    }


    public boolean loadParams1(List<String> parameters) {
        if (parameters.get(2).equals("-ai")){
            int aux = Integer.parseInt(parameters.get(3));
            if(!(aux==0 || aux==1 || aux==2))
                return false;
            else
                ai=aux;
        }
        else
            return false;
        if (parameters.get(4).equals("-mode")) {
            if (parameters.get(5).equals("time"))
                searchtype = SEARCHTYPE.TIME;
            else if (parameters.get(5).equals("depth"))
                searchtype = SEARCHTYPE.DEEPTH;
            else
                return false;
        } else
            return false;
        if (parameters.get(6).equals("-param")) {
            int aux = Integer.parseInt(parameters.get(7));
            param = aux;
        }
        else
            return false;
        if (parameters.get(8).equals("-prune")){
            if(parameters.get(9).equals("on"))
                prune=true;
            else if(parameters.get(9).equals("off"))
                prune=false;
            else
                return false;
        }
        else
            return false;
        return true;
    }
    public static void main(String[] args){
        launch(args);
    }
}
