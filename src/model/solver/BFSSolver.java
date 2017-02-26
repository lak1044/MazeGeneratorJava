package model.solver;

import model.Cell;
import model.MazeModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lak1044 on 2/24/2017.
 */
public class BFSSolver extends Solver {

    private Queue<Cell> queue;
    private boolean completed;

    public BFSSolver(MazeModel mazeModel) {
        super(mazeModel);
        this.queue = new LinkedList<>();
        MazeModel.current.visit();
        MazeModel.current.setTemporary();
        ArrayList<Cell> randomNeighbors = this.getRandomAccessibleNeighbors(MazeModel.current);
        for (Cell neighbor: randomNeighbors) {
            neighbor.setParent(MazeModel.current);
            MazeModel.current.setChildren(neighbor);
            queue.add(neighbor);
        }
        this.completed = false;
        System.out.println("BFSSolver created");
    }

    public void solve() {
        ArrayList<Cell> randomNeighbors;
        if (this.completed && MazeModel.current.equals(MazeModel.start)) {
            setSolved();
        } else if (this.completed) {
            MazeModel.current.setInSolution();
            MazeModel.lastCell = MazeModel.current;
            MazeModel.current = MazeModel.current.getParent();
        } else {
            //1. Dequeue first element in queue and make it current
            MazeModel.lastCell = MazeModel.current;
            MazeModel.current = queue.remove();
            MazeModel.current.setTemporary();
            MazeModel.current.getParent().setTemporary();
            //2. Get random neighbors, set parents/children, and add them to queue
            randomNeighbors = this.getRandomAccessibleNeighbors(MazeModel.current);
            for (Cell neighbor : randomNeighbors) {
                if (!neighbor.equals(MazeModel.current.getParent())) {
                    neighbor.setParent(MazeModel.current);
                    MazeModel.current.setChildren(neighbor);
                    queue.add(neighbor);
                }
            }
            MazeModel.current.setTemporary();
            if (MazeModel.current.equals(goal)) {
                this.completed = true;
            }
        }
    }
}
