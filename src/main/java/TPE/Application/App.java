package TPE.Application;

import TPE.Controller.TableController;
import TPE.Model.Table;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class App extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Table t = new Table(10);
        t.addPlayer(Color.WHITE,false);
        t.addPlayer(Color.BLACK, false);
        t.setInitialPos();
        t.start();
        primaryStage.setTitle("Reversi");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("views/TableController.fxml"));
        Parent root=loader.load();
        TableController controler= loader.getController();
        controler.initModel(t);
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args){
        launch(args);
    }
}
