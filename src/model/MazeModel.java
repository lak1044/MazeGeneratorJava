package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/**
 * Created by lak1044 on 2/20/2017.
 */
public class MazeModel extends Observable{
    public Cell[][] maze;
    public static int rows;
    public static int cols;

    public MazeModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new Cell[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.maze[i][j] = new Cell(i, j);
            }
        }
        this.announceChange();
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

    public Cell getRandomNeighbor(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        if (inMaze(cell.row -1, cell.col) && !maze[cell.row - 1][cell.col].isVisited()) {
            neighbors.add(maze[cell.row - 1][cell.col]);
        }
        if (inMaze(cell.row + 1, cell.col) && !maze[cell.row + 1][cell.col].isVisited()) {
            neighbors.add(maze[cell.row + 1][cell.col]);
        }
        if (inMaze(cell.row, cell.col - 1) && !maze[cell.row][cell.col - 1].isVisited()) {
            neighbors.add(maze[cell.row][cell.col - 1]);
        }
        if (inMaze(cell.row, cell.col + 1) && !maze[cell.row][cell.col + 1].isVisited()) {
            neighbors.add(maze[cell.row][cell.col + 1]);
        }
        if (!neighbors.isEmpty()) {
            return neighbors.get(new Random().nextInt(neighbors.size()));
        } else {
            return null;
        }
    }

    private boolean inMaze(int row, int col) {
        return (row >= 0 && row < rows && col >= 0 && col < cols);
    }


    public void announceChange() {
        setChanged();
        notifyObservers();
    }
}
