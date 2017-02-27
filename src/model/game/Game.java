package model.game;

import model.MazeModel;


/**
 * Created by lak1044 on 2/26/2017.
 */
public class Game extends MazeModel implements Runnable {

    boolean completed;

    public Game(MazeModel mazeModel) {
        super(mazeModel);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.maze[i][j].unVisit();
                this.maze[i][j].setPermanent();
            }
        }
        MazeModel.current = MazeModel.start;
        MazeModel.current.setInSolution();
        MazeModel.current.visit();
        this.completed = false;
        System.out.println("Game Created");
    }

    public void move(char dir) {
        if (this.accessible(MazeModel.current, dir)) {
            MazeModel.lastCell = MazeModel.current;

            switch (dir) {
                case 'N':
                    MazeModel.current = this.maze[MazeModel.current.getRow() - 1][MazeModel.current.getCol()];
                    break;
                case 'S':
                    MazeModel.current = this.maze[MazeModel.current.getRow() + 1][MazeModel.current.getCol()];
                    break;
                case 'W':
                    MazeModel.current = this.maze[MazeModel.current.getRow()][MazeModel.current.getCol() - 1];
                    break;
                case 'E':
                    MazeModel.current = this.maze[MazeModel.current.getRow()][MazeModel.current.getCol() + 1];
                    break;
            }

        }
        announceChange();
        if (MazeModel.current.equals(MazeModel.end)) {
            this.completed = true;
            System.out.println("You solved the Maze!");
        }
    }

    @Override
    public void run() {
        while (!this.completed) {

        }
    }
}
