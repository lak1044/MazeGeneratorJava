package gui;

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
import model.Cell;
import model.MazeModel;
import model.generator.DFSGenerator;
import model.generator.Generator;
import model.generator.PrimsGenerator;
import model.solver.BFSSolver;
import model.solver.DFSSolver;
import model.solver.Solver;

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

    private static final int CELL_WIDTH = 15;

    private Stage stage;

    @Override
    public void init() {
        this.model = new MazeModel(25, 25);
        this.model.addObserver(this);
    }

    private VBox constructCommandButtons() {
        VBox commandButtons = new VBox();
        Button generateDFSButton = new Button("Generate DFS");
        generateDFSButton.setOnAction(event -> {
            try {
                Generator dfsMaze = new DFSGenerator();
                dfsMaze.addObserver(this);
                Thread dfsThread = new Thread(dfsMaze);
                this.model = dfsMaze;
                drawMaze();
                dfsThread.start();
            } catch (Exception e) {}
        });
        Button generatePrimsButton = new Button("Generate Prim's");
        generatePrimsButton.setOnAction(event -> {
            try {
                Generator primsMaze = new PrimsGenerator();
                primsMaze.addObserver(this);
                Thread dfsThread = new Thread(primsMaze);
                this.model = primsMaze;
                drawMaze();
                dfsThread.start();
            } catch (Exception e) {}
        });
        Button solveDFSButton = new Button("Solve DFS");
        solveDFSButton.setOnAction(event -> {
            try {
                Solver dfsMaze = new DFSSolver(this.model);
                dfsMaze.addObserver(this);
                Thread dfsThread = new Thread(dfsMaze);
                this.model = dfsMaze;
                drawMaze();
                dfsThread.start();
            } catch (Exception e) {}
        });
        Button solveBFSButton = new Button("Solve BFS");
        solveBFSButton.setOnAction(event -> {
            try {
                Solver bfsMaze = new BFSSolver(this.model);
                bfsMaze.addObserver(this);
                Thread dfsThread = new Thread(bfsMaze);
                this.model = bfsMaze;
                drawMaze();
                dfsThread.start();
            } catch (Exception e) {}
        });
        commandButtons.getChildren().addAll(generateDFSButton, generatePrimsButton, solveDFSButton, solveBFSButton);

        return commandButtons;
    }

    private void drawMaze() {
        int x;
        int y;
        boolean[] dirs;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(1);

        for (int i = 0; i < MazeModel.rows; i++) {
            for (int j = 0; j < MazeModel.cols; j++) {
                x = j * CELL_WIDTH;
                y = i * CELL_WIDTH;
                if (model.maze[i][j].getPermanent()) {
                    gc.setFill(Color.RED);
                } else if (model.maze[i][j].getTemporary()) {
                    gc.setFill(Color.YELLOW);
                } else if (model.maze[i][j].getInSolution()) {
                    gc.setFill(Color.CYAN);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(x, y, CELL_WIDTH, CELL_WIDTH);
                dirs = model.maze[i][j].dirs();
                if (dirs[0]) {
                    gc.strokeLine(x, y, x + CELL_WIDTH, y);
                }
                if (dirs[1]) {
                    gc.strokeLine(x, y + CELL_WIDTH, x + CELL_WIDTH, y + CELL_WIDTH);
                }
                if (dirs[2]) {
                    gc.strokeLine(x, y, x, y + CELL_WIDTH);
                }
                if (dirs[3]) {
                    gc.strokeLine(x + CELL_WIDTH, y, x + CELL_WIDTH, y + CELL_WIDTH);
                }
            }
        }
    }

    private void drawCurrent() {
        if (MazeModel.current.getParent() != null) {
            drawCell(MazeModel.current.getParent());
        }
        if (!MazeModel.current.getChildren().isEmpty()) {
           for (Cell cell: MazeModel.current.getChildren()) {
               drawCell(cell);
           }
        }
        drawCell(MazeModel.current);
    }

    private void drawCell(Cell cell) {
        int x = cell.getCol() * CELL_WIDTH;
        int y = cell.getRow() * CELL_WIDTH;
        if (cell.equals(MazeModel.current)) {
            gc.setFill(Color.LIME);
        } else {
            if (cell.getTemporary()) {
                gc.setFill(Color.YELLOW);
            } else if (cell.getPermanent()) {
                gc.setFill(Color.RED);
            } else if (cell.getInSolution()) {
                gc.setFill(Color.CYAN);
            }
        }
        gc.fillRect(x, y, CELL_WIDTH, CELL_WIDTH);
        boolean[] dirs = cell.dirs();
        if (dirs[0]) {
            gc.strokeLine(x, y, x + CELL_WIDTH, y);
        }
        if (dirs[1]) {
            gc.strokeLine(x, y + CELL_WIDTH, x + CELL_WIDTH, y + CELL_WIDTH);
        }
        if (dirs[2]) {
            gc.strokeLine(x, y, x, y + CELL_WIDTH);
        }
        if (dirs[3]) {
            gc.strokeLine(x + CELL_WIDTH, y, x + CELL_WIDTH, y + CELL_WIDTH);
        }
    }

    private void init(Stage stage) {
        borderPane = new BorderPane();
        borderPane.setRight(constructCommandButtons());
        canvas = new Canvas(MazeModel.cols * CELL_WIDTH, MazeModel.rows * CELL_WIDTH);
        gc = canvas.getGraphicsContext2D();
        borderPane.setCenter(canvas);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
    }


    @Override
    public void update(Observable o, Object arg) {
        drawCurrent();
        if (MazeModel.lastCell != null) {
            drawCell(MazeModel.lastCell);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        init(primaryStage); //do all UI initialization
        primaryStage.setTitle("Maze Generator");
        primaryStage.show();
        drawMaze();
        drawCurrent();
    }
}
