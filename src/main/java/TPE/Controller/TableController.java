package TPE.Controller;

import TPE.Model.Table;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {
    private Table model;

    @FXML
    private GridPane board;

    public void initModel(Table model){
        this.model=model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
    }

    public void setView(){
        for (int i = 0; i < model.getSize()-1; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / model.getSize());
            board.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < model.getSize()-1; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / model.getSize());
            board.getRowConstraints().add(rowConst);
        }
        for (int i = 0 ; i < model.getSize() ; i++) {
            for (int j = 0; j < model.getSize(); j++) {
                addCircle(i,j);
            }
        }
    }

    private void addCircle(int i, int j) {
        Circle circle = new Circle(20);
        circle.fillProperty().bind(Bindings.isNull());
        circle.setOnMouseClicked(e -> {
            model.applyMove(i,j);
        });
        board.add(circle, i, j);
    }


    @FXML
    protected void paneAction(){
        System.out.println("hola");
    }
}
