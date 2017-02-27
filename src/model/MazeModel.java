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
    public static Cell lastCell;
    public static Cell start;
    public static Cell end;

    public MazeModel(int rows, int cols) {
        MazeModel.rows = rows;
        MazeModel.cols = cols;
        this.maze = new Cell[MazeModel.rows][MazeModel.cols];
        for (int i = 0; i < MazeModel.rows; i++) {
            for (int j = 0; j < MazeModel.cols; j++) {
                this.maze[i][j] = new Cell(i, j);
            }
        }
        MazeModel.start = maze[0][0];
        MazeModel.current = MazeModel.start;
        MazeModel.lastCell = null;
        MazeModel.end = this.maze[rows - 1][cols - 1];
        System.out.println("MazeModel created");
    }

    public MazeModel(MazeModel mazeModel) {
        this.maze = mazeModel.maze;
        System.out.println("MazeModel Created");
    }


    protected ArrayList<Cell> getRandomNeighbors(Cell cell) {
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

    protected ArrayList<Cell> getRandomAccessibleNeighbors(Cell cell) {
        ArrayList<Cell> accessibleNeighbors = new ArrayList<>();
        boolean[] dirs = cell.dirs();
        if (!dirs[0] &&
                inMaze(cell.getRow() - 1, cell.getCol()) &&
                !maze[cell.getRow() - 1][cell.getCol()].getVisited()) {
            accessibleNeighbors.add(maze[cell.getRow() - 1][cell.getCol()]);
        }
        if (!dirs[1] &&
                inMaze(cell.getRow() + 1, cell.getCol()) &&
                !maze[cell.getRow() + 1][cell.getCol()].getVisited()) {
            accessibleNeighbors.add(maze[cell.getRow() + 1][cell.getCol()]);
        }
        if (!dirs[2] &&
                inMaze(cell.getRow(), cell.getCol() - 1) &&
                !maze[cell.getRow()][cell.getCol() - 1].getVisited()) {
            accessibleNeighbors.add(maze[cell.getRow()][cell.getCol() - 1]);
        }
        if (!dirs[3] &&
                inMaze(cell.getRow(), cell.getCol() + 1) &&
                !maze[cell.getRow()][cell.getCol() + 1].getVisited()) {
            accessibleNeighbors.add(maze[cell.getRow()][cell.getCol() + 1]);
        }
        Collections.shuffle(accessibleNeighbors);
        return accessibleNeighbors;
    }

    protected Cell getRandomAccessibleNeighbor(Cell cell) {
        ArrayList<Cell> accessibleNeighbors = getRandomAccessibleNeighbors(cell);
        if (!accessibleNeighbors.isEmpty()) {
            return accessibleNeighbors.get(new Random().nextInt(accessibleNeighbors.size()));
        } else {
            return null;
        }
    }

    protected Cell getRandomVisitedNeighbor(Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        if (inMaze(cell.getRow() -1, cell.getCol()) && maze[cell.getRow() - 1][cell.getCol()].getVisited()) {
            neighbors.add(maze[cell.getRow() - 1][cell.getCol()]);
        }
        if (inMaze(cell.getRow() + 1, cell.getCol()) && maze[cell.getRow() + 1][cell.getCol()].getVisited()) {
            neighbors.add(maze[cell.getRow() + 1][cell.getCol()]);
        }
        if (inMaze(cell.getRow(), cell.getCol() - 1) && maze[cell.getRow()][cell.getCol() - 1].getVisited()) {
            neighbors.add(maze[cell.getRow()][cell.getCol() - 1]);
        }
        if (inMaze(cell.getRow(), cell.getCol() + 1) && maze[cell.getRow()][cell.getCol() + 1].getVisited()) {
            neighbors.add(maze[cell.getRow()][cell.getCol() + 1]);
        }
        Collections.shuffle(neighbors);
        if (!neighbors.isEmpty()) {
            return neighbors.get(new Random().nextInt(neighbors.size()));
        } else {
            return null;
        }
    }

    protected boolean accessible (Cell cell, char dir) {
        boolean[] dirs = cell.dirs();
        switch (dir) {
            case 'N':
                return !dirs[0];
            case 'S':
                return !dirs[1];
            case 'W':
                return !dirs[2];
            case 'E':
                return !dirs[3];
        }
        return false;
    }

    private boolean inMaze(int row, int col) {
        return (row >= 0 && row < rows && col >= 0 && col < cols);
    }


    protected void announceChange() {
        setChanged();
        notifyObservers();
    }
}
