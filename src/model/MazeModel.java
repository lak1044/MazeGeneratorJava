package model;

import java.util.Observable;

/**
 * Created by lak1044 on 2/20/2017.
 */
public class MazeModel extends Observable{
    public Cell[][] maze;
    public static int rows;
    public static int cols;
    boolean solved;
    boolean generated;

    public MazeModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.solved = false;
        this.generated = false;
        this.maze = new Cell[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.maze[i][j] = new Cell(i, j);
            }
        }
    }


    public void removeCommonWall(Cell c1, Cell c2) {
        if (c1.row - c2.row < 0) {                  //c1 is above c2
            c1.removeWall('S');
            c2.removeWall('N');
        } else if (c1.row - c2.row > 0) {           // c1 is below c2
            c1.removeWall('N');
            c2.removeWall('S');
        } else if (c1.col - c2.col < 0) {           //c1 is left of c2
            c1.removeWall('E');
            c2.removeWall('W');
        } else if (c1.col - c2.col > 0) {           //c1 is right of c2
            c1.removeWall('W');
            c2.removeWall('E');
        }
    }
}
