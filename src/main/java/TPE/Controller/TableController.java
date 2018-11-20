package TPE.Controller;
import TPE.Exceptions.ExceptionPopup;
import TPE.Model.Point;
import TPE.Model.Reversi;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class TableController implements Initializable {
    private Reversi model;
    private Circle[][] tabs;
    private NumberBinding radioSize;

    @FXML
    private Button undoButton;
    @FXML
    private GridPane board;
    @FXML
    private Button passButton;
    @FXML
    private Text player1Points;
    @FXML
    private Text player2Points;
    @FXML
    private Circle player1Circle;
    @FXML
    private Circle player2Circle;
    @FXML
    private Text winText;
    @FXML
    private Button saveButton;

    public void initModel(Reversi model){

        this.model=model;
        tabs = new Circle[model.getSize()][model.getSize()];
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        board.setStyle("-fx-background-color: #008000;");
    }

    public void setView(){
        player1Circle.setFill(model.getPlayers()[0].getColor());
        player2Circle.setFill(model.getPlayers()[1].getColor());
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
        radioSize = Bindings.min(board.heightProperty().divide(model.getSize()), board.widthProperty().divide(model.getSize()));
        for (int i = 0 ; i < model.getSize() ; i++) {
            for (int j = 0; j < model.getSize(); j++) {
                addCircle(i,j);
            }
        }
        showPosibleMoves();
        refreshPoints();
        long start = System.nanoTime();
        while(model.makeIaMove() && !model.gameFinished()){
            model.nextTurn();
            refreshCircleColors();
            refreshPoints();
        }
        System.out.println(System.nanoTime()-start);
    }

    private void addCircle(int i, int j) {
        Circle circle = new Circle(20);
        circle.radiusProperty().bind(board.widthProperty());
        if(model.getBoard().getTable()[i][j] != -1) {
            circle.setFill(model.getPlayers()[model.getBoard().getTable()[i][j]].getColor());
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
                refreshPoints();
                if(model.gameFinished()) {
                    if(model.getPlayers()[0].getPoints()==model.getPlayers()[1].getPoints())
                        winText.setText("Es un empate");
                    else if (model.getPlayers()[0].getPoints()>model.getPlayers()[1].getPoints())
                        winText.setText("El ganador es el jugador 1");
                    else
                        winText.setText("El ganador es el jugador 2");
                }
                if(model.makeIaMove()){
                    model.nextTurn();
                    refreshCircleColors();
                    refreshPoints();
                }
            }
        });
        GridPane.setHalignment(circle, javafx.geometry.HPos.CENTER);
        circle.radiusProperty().bind(radioSize.divide(3));
        board.add(circle, j, i);
        tabs[i][j] = circle;

    }
    private void refreshCircleColors(){
        for (int i = 0 ; i < model.getSize() ; i++) {
            for (int j = 0; j < model.getSize(); j++) {
                if (model.getBoard().getTable()[i][j]!= -1) {
                    tabs[i][j].setFill(model.getPlayers()[model.getBoard().getTable()[i][j]].getColor());
                    tabs[i][j].setStroke(Color.BLACK);
                }
                else{
                    tabs[i][j].setFill(Color.GREEN);
                    tabs[i][j].setStroke(Color.GREEN);
                }
            }
        }
        showPosibleMoves();
    }
    private void showPosibleMoves(){
        for(Point aux: model.getMoves().keySet()){
            tabs[aux.getX()][aux.getY()].setStroke(Color.BLACK);
        }
    }
    private void refreshPoints(){
        player1Points.setText(Integer.toString(model.getPlayers()[0].getPoints()));
        player2Points.setText(Integer.toString(model.getPlayers()[1].getPoints()));
    }
    public void undoAction(){
        if(!model.getUndoMoves().empty()) {
            model.undoMove();
            model.nextTurn();
            refreshPoints();
            refreshCircleColors();
        }
    }
    public void passAction(){
        model.nextTurn();
        refreshCircleColors();
    }
    public void saveAction(){
        try {
            FileChooser fileChooser = new FileChooser();
            Stage stage = new Stage();
            File file = fileChooser.showSaveDialog(stage);
            if(file!=null) {
                ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file.toPath()));
                out.writeObject(model.getBoard());
                out.close();
            }
        }
        catch(Exception e){
            ExceptionPopup alert = new ExceptionPopup(e);
            alert.showAndWait();
        }
    }
}
