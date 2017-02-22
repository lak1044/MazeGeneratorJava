package gui;

import generator.DFS;
import generator.Generator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    private Label status;

    private Canvas canvas;
    private GraphicsContext gc;

    private static final int CELLWIDTH = 15;

    private Stage stage;

    @Override
    public void init() {
        this.model = new MazeModel(30, 30);
        this.model.addObserver(this);
    }

    private VBox constructCommandButtons() {
        VBox commandButtons = new VBox();
        Button generateDFSButton = new Button("Generate DFS");
        generateDFSButton.setOnAction(event -> {
            Generator dfsMaze = new DFS(model);
            this.model.maze = dfsMaze.generate();
            this.model.announceChange();
        });
        commandButtons.getChildren().addAll(generateDFSButton);

        return commandButtons;
    }

    private void drawMaze() {
        int x;
        int y;
        boolean[] dirs;
        gc.save();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(1);

        for (int i = 0; i < model.rows; i++) {
            for (int j = 0; j < model.cols; j++) {
                x = j * CELLWIDTH;
                y = i * CELLWIDTH;
                if (model.maze[i][j].isVisited()) {
                    gc.setFill(Color.RED);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(x, y, CELLWIDTH, CELLWIDTH);
                dirs = model.maze[i][j].dirs();
                if (dirs[0]) {
                    gc.strokeLine(x, y, x + CELLWIDTH, y);
                }
                if (dirs[1]) {
                    gc.strokeLine(x, y +CELLWIDTH, x + CELLWIDTH, y + CELLWIDTH);
                }
                if (dirs[2]) {
                    gc.strokeLine(x, y, x, y + CELLWIDTH);
                }
                if (dirs[3]) {
                    gc.strokeLine(x + CELLWIDTH, y, x + CELLWIDTH, y + CELLWIDTH);
                }
            }
        }
    }

    private void init(Stage stage) {
        borderPane = new BorderPane();
        //borderPane.setCenter(constructMazeGrid());
        borderPane.setRight(constructCommandButtons());
        canvas = new Canvas(model.cols * CELLWIDTH, model.rows * CELLWIDTH);
        gc = canvas.getGraphicsContext2D();
        borderPane.setCenter(canvas);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
    }


    @Override
    public void update(Observable o, Object arg) {
        drawMaze();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        init(primaryStage); //do all UI initialization
        primaryStage.setTitle("Maze Generator");
        primaryStage.show();
        drawMaze();
    }
}
