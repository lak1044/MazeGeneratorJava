package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import model.MazeModel;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by lak1044 on 2/20/2017.
 */
public class MazeGUI extends Application implements Observer{

    private MazeModel model;

    private BorderPane borderPane;

    private GridPane mazeGrid;

    private Label status;



    private Stage stage;

    @Override
    public void init() {
        this.model = new MazeModel(10, 10);
        this.model.addObserver(this);
    }

    private GridPane constructMazeGrid() {
        mazeGrid = new GridPane();
        mazeGrid.setGridLinesVisible(true);

        for (int i = 0; i < model.rows; i++) {
            for (int j = 0; j < model.cols; j++) {
                Image whiteImg = new Image(getClass().getResourceAsStream("resources/white.png"));
                ImageView whiteIcon = new ImageView(whiteImg);
                mazeGrid.add(whiteIcon, i, j);
            }
        }

        return mazeGrid;
    }

    private VBox constructCommandButtons() {
        VBox commandButtons = new VBox();
        Button generateDFSButton = new Button("Generate DFS");
        commandButtons.getChildren().addAll(generateDFSButton);

        return commandButtons;
    }

    private void init(Stage stage) {
        borderPane = new BorderPane();
        borderPane.setCenter(constructMazeGrid());
        borderPane.setRight(constructCommandButtons());
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
    }


    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        init(primaryStage); //do all UI initialization
        primaryStage.setTitle("Maze Generator");
        primaryStage.show();
    }
}
