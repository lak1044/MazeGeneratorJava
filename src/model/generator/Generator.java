package model.generator;

import model.Cell;
import model.MazeModel;


/**
 * Created by lak1044 on 2/23/2017.
 */
public abstract class Generator extends MazeModel implements Runnable {
    private boolean generated;

    Generator() {
        super(MazeModel.rows, MazeModel.cols);
        this.generated = false;
        System.out.println("Generator created");
    }

    private boolean getGenerated() {return this.generated;}
    void setGenerated() {
        this.generated = true;
        this.announceChange();
    }
    public void generate() {}

    void removeCommonWall(Cell c1, Cell c2) {
        if (c1.getRow() - c2.getRow() < 0) {                  //c1 is above c2
            c1.removeWall('S');
            c2.removeWall('N');
        } else if (c1.getRow() - c2.getRow() > 0) {           // c1 is below c2
            c1.removeWall('N');
            c2.removeWall('S');
        } else if (c1.getCol() - c2.getCol() < 0) {           //c1 is left of c2
            c1.removeWall('E');
            c2.removeWall('W');
        } else if (c1.getCol() - c2.getCol() > 0) {           //c1 is right of c2
            c1.removeWall('W');
            c2.removeWall('E');
        }
    }

    @Override
    public void run() {
        while (!this.getGenerated()) {
            try {
                generate();
                announceChange();
                Thread.sleep(20);
            } catch (Exception e) {}
        }
    }
}
