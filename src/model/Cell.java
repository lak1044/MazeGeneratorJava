package model;


/**
 * Created by lak1044 on 2/20/2017.
 */
public class Cell {
    private int row;
    private int col;
    private boolean N;
    private boolean S;
    private boolean W;
    private boolean E;
    private boolean visited;
    private Cell parent;
    private boolean temporary;
    private boolean permanent;

    //private List<Cell> neigbors;

     Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.N = true;
        this.S = true;
        this.W = true;
        this.E = true;
        this.visited = false;
        this.parent = null;
        this.temporary = false;
        this.permanent = false;
    }
    /////////////Getters/////////////////
    public int getRow() {return this.row;}
    public int getCol() {return this.col;}
    public boolean getVisited() {return this.visited;}
    public boolean getTemporary() {return this.temporary;}
    public boolean getPermanent() {return this.permanent;}

    ////////////Setters////////////////
    public void setTemporary() {
         this.temporary = true;
         this.permanent = false;
    }
    public void setPermanent() {
         this.temporary = false;
         this.permanent = true;
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

    public boolean[] dirs() {
        boolean[] dirs = new boolean[4];
        if (this.N) {
            dirs[0] = true;
        }
        if (this.S) {
            dirs[1] = true;
        }
        if (this.W) {
            dirs[2] = true;
        }
        if (this.E) {
            dirs[3] = true;
        }
        return dirs;
    }
}
