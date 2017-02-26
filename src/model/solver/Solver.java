package model.solver;

import model.Cell;
import model.MazeModel;

/**
 * Created by lak1044 on 2/24/2017.
 */
public class Solver extends MazeModel implements Runnable {

    private boolean solved;
    protected Cell goal;

    Solver(MazeModel mazeModel) {
        super(mazeModel);
        this.solved = false;
        this.goal = MazeModel.end;
        for (int i = 0; i < MazeModel.rows; i++) {
            for (int j = 0; j < MazeModel.cols; j++) {
                maze[i][j].unVisit();
            }
        }
        System.out.println("Solver Created");
    }

    void setSolved() {
        this.solved = true;
        this.announceChange();
        System.out.println("Maze Solved!");
    }

    public void solve() {}

    @Override
    public void run() {
        while (!this.solved) {
            try {
                solve();
                announceChange();
                Thread.sleep(20);
            } catch (Exception e) {}
        }
    }
}
