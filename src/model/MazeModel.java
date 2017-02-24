package model;

import java.util.*;

/**
 * Created by lak1044 on 2/20/2017.
 */
public class MazeModel extends Observable{
    public Cell[][] maze;
    public static int rows;
    public static int cols;
    public static Cell current;


    public MazeModel(int rows, int cols) {
        MazeModel.rows = rows;
        MazeModel.cols = cols;
        this.maze = new Cell[MazeModel.rows][MazeModel.cols];
        for (int i = 0; i < MazeModel.rows; i++) {
            for (int j = 0; j < MazeModel.cols; j++) {
                this.maze[i][j] = new Cell(i, j);
            }
        }
        MazeModel.current = maze[0][0];
        System.out.println("MazeModel created");
    }


    private ArrayList<Cell> getRandomNeighbors(Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        if (inMaze(cell.getRow() -1, cell.getCol()) && !maze[cell.getRow() - 1][cell.getCol()].getVisited()) {
            neighbors.add(maze[cell.getRow() - 1][cell.getCol()]);
        }
        if (inMaze(cell.getRow() + 1, cell.getCol()) && !maze[cell.getRow() + 1][cell.getCol()].getVisited()) {
            neighbors.add(maze[cell.getRow() + 1][cell.getCol()]);
        }
        if (inMaze(cell.getRow(), cell.getCol() - 1) && !maze[cell.getRow()][cell.getCol() - 1].getVisited()) {
            neighbors.add(maze[cell.getRow()][cell.getCol() - 1]);
        }
        if (inMaze(cell.getRow(), cell.getCol() + 1) && !maze[cell.getRow()][cell.getCol() + 1].getVisited()) {
            neighbors.add(maze[cell.getRow()][cell.getCol() + 1]);
        }
        Collections.shuffle(neighbors);
        return neighbors;
    }

    protected Cell getRandomNeighbor(Cell cell) {
        ArrayList<Cell> neighbors = getRandomNeighbors(cell);
        if (!neighbors.isEmpty()) {
            return neighbors.get(new Random().nextInt(neighbors.size()));
        } else {
            return null;
        }
    }

    private boolean inMaze(int row, int col) {
        return (row >= 0 && row < rows && col >= 0 && col < cols);
    }


    protected void announceChange() {
        System.out.println("Change announced");
        setChanged();
        notifyObservers();
    }
}
