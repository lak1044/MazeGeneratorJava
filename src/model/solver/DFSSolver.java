package model.solver;

import model.Cell;
import model.MazeModel;

import java.util.Stack;

/**
 * Created by lak1044 on 2/24/2017.
 */
public class DFSSolver extends Solver{
    private Stack<Cell> stack;
    private boolean completed;

    public DFSSolver(MazeModel mazeModel) {
        super(mazeModel);
        this.stack = new Stack<>();
        MazeModel.current.visit();
        this.stack.push(MazeModel.current);
        this.completed = false;
        System.out.println("DFSSolver created");
    }

    public void solve() {
        Cell next;

        //1. If current cell is goal
        if (this.stack.isEmpty()) {
            setSolved();
            System.out.println("DFSSolver Maze Solved");
        } else if (this.completed) {
            MazeModel.current.setInSolution();
            MazeModel.current = stack.pop();
        } else if (MazeModel.current.equals(goal)) {
            this.completed = true;
        } else {
            //2. Choose random accessible neighbor and set to next
            next = this.getRandomAccessibleNeighbor(MazeModel.current);
            //3. If neighbor exists
            if (next != null) {
                //1. Push current to stack
                this.stack.push(MazeModel.current);
                //2. Set current to temporary
                MazeModel.current.setTemporary();
                //3. Set current to next
                MazeModel.current = next;
                //4. Visit current Cell
                MazeModel.current.visit();
            }
            //4. If neighbor doesn't exist
            else {
                //1. Pop cell off stack and set it to current
                MazeModel.current.setTemporary();
                MazeModel.current = this.stack.pop();
            }
        }
    }
}
