package model;

import java.util.List;

/**
 * Created by lak1044 on 2/20/2017.
 */
public class Cell {
    int row;
    int col;
    private boolean N;
    private boolean S;
    private boolean W;
    private boolean E;
    private boolean visited;
    private Cell parent;
    private List<Cell> neigbors;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.N = true;
        this.S = true;
        this.E = true;
        this.W = true;
        this.visited = false;
        this.parent = null;
    }

    public void removeWall(char wall) {
        switch (wall) {
            case 'N':
                this.N = false;
                break;
            case 'S':
                this.S = false;
                break;
            case 'W':
                this.W = false;
                break;
            case 'E':
                this.E = false;
                break;
        }
    }

    public void visit() {
        this.visited = true;
    }
}
