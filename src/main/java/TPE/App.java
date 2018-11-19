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


public class App extends Application
{
    public enum GAMETYPE{ JVJ, PCVJ , JVPC}
    @Override
    public void start(Stage primaryStage) throws Exception{

        Reversi t;
        Board b;
        String path;
        try {
            FileInputStream fileIn = new FileInputStream("C:/Users/J/Desktop/ready");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            b = (Board) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("Hubo un error, el archivo no existe o esta corrupto");
            return;
        }

        if(false) { // aca entra si no cargamos nadaa
            t.setInitialPos();
        }

        t=new Reversi(b);
        GAMETYPE type = GAMETYPE.JVJ;
        switch(type){
            case JVJ:
                t.addPlayer(Color.WHITE,false);
                t.addPlayer(Color.BLACK, false);

        }
        t.setInitialPos();
        t.setPlayerPoints();
        t.start();
        System.out.println(t);
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
    public static void main(String[] args){
        launch(args);
    }
}
