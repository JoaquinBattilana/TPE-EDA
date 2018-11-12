package TPE.Controller;
import TPE.Model.Move;
import TPE.Model.Point;
import TPE.Model.Table;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class TableController implements Initializable {
    private Table model;
    private Circle[][] tabs;

    @FXML
    private GridPane board;

    public void initModel(Table model){

        this.model=model;
        tabs = new Circle[model.getSize()][model.getSize()];
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        board.setStyle("-fx-background-color: #008000;");
    }

    public void setView(){
        for (int i = 0; i < model.getSize()-1; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / model.getSize());
            board.getRowConstraints().add(rowConst);
        }
        for (int i = 0; i < model.getSize()-1; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / model.getSize());
            board.getColumnConstraints().add(colConst);
        }
        for (int i = 0 ; i < model.getSize() ; i++) {
            for (int j = 0; j < model.getSize(); j++) {
                addCircle(i,j);
            }
        }
    }

    private void addCircle(int i, int j) {
        System.out.println(i+" "+j);
        Circle circle = new Circle(20);
        if(model.getBoard()[i][j].hasOwner()) {
            circle.setFill(model.getBoard()[i][j].getOwner().getColor());
            circle.setStroke(Color.BLACK);
        }
        else {
            circle.setFill(Color.GREEN);
            circle.setStroke(Color.GREEN);
        }
        circle.setOnMouseClicked(e -> {
            if(model.applyMove(i,j)) {
                model.nextTurn();
                refreshCircleColors();
            }
        });
        board.add(circle, j, i);
        tabs[i][j] = circle;

    }
    private void refreshCircleColors(){
        for (int i = 0 ; i < model.getSize() ; i++) {
            for (int j = 0; j < model.getSize(); j++) {
                if (model.getBoard()[i][j].hasOwner()) {
                    tabs[i][j].setFill(model.getBoard()[i][j].getOwner().getColor());
                    tabs[i][j].setStroke(Color.BLACK);
                }
                else{
                    tabs[i][j].setStroke(Color.GREEN);
                }
            }
        }
        for(Point aux: model.getMoves().keySet()){
            tabs[aux.getX()][aux.getY()].setStroke(Color.BLACK);
        }
    }
}
