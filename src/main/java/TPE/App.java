package TPE;

import TPE.Controller.TableController;
import TPE.Model.Reversi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class App extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Reversi t = new Reversi(8);
        t.addPlayer(Color.WHITE,false);
        t.addPlayer(Color.BLACK, false);
        t.setInitialPos();
        t.start();
        System.out.println(t);
        primaryStage.setTitle("Reversi");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Table.fxml"));
        Parent root=loader.load();
        TableController controler= loader.getController();
        controler.initModel(t);
        controler.setView();
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
