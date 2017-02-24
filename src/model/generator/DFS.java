package model.generator;

import model.Cell;
import model.MazeModel;

import java.util.Stack;


/**
 * Created by lak1044 on 2/20/2017.
 */
public class DFS extends Generator {
    private Stack<Cell> stack;

    public DFS() throws Exception {
        super();
        this.stack = new Stack<>();
        MazeModel.current.visit();
        this.stack.push(MazeModel.current);
        System.out.println("DFS created");
    }

    public Stack<Cell> getStack() {return this.stack;}

    public void generate() {
        Cell next;
        //1. Get random neighbor of current cell and set it to next
        next = this.getRandomNeighbor(current);
        //2. If neighbor exists
        if (next != null) {
            //1. remove the common wall between current and next
            this.removeCommonWall(MazeModel.current, next);
            //2. push current cell to stack
            this.stack.push(current);
            MazeModel.current.setTemporary();
            //3. set current cell to next cell
            MazeModel.current = next;
            //4. visit current cell
            MazeModel.current.visit();
        }
        //3. Else if neighbor doesn't exist
        else {
            //1. pop cell from stack and set it to current cell
            MazeModel.current.setPermanent();
            MazeModel.current = this.stack.pop();
        }
        if (this.stack.isEmpty()) {
            setGenerated();
            System.out.println("DFS Maze Generated");
        }
    }
}
