package generator;

import model.Cell;
import model.MazeModel;

import java.util.Stack;


/**
 * Created by lak1044 on 2/20/2017.
 */
public class DFS implements Generator {
    private MazeModel mazeModel;
    private Stack<Cell> stack;
    private Cell current;
    private Cell next;

    public DFS (MazeModel mazeModel) {
        this.mazeModel = mazeModel;
        this.stack = new Stack<Cell>();
        this.current = this.mazeModel.maze[0][0];
        this.current.visit();
    }

    public MazeModel generate(MazeModel mazeModel) {
        System.out.println("Hi");

        do {
            //1. Get random neighbor of current cell and set it to next

            //2. If neighbor exists
            if (this.next != null) {
                //1. push current cell to stack
                this.stack.push(current);
                //2. set current cell to next cell
                this.current = this.next;
                //3. visit current cell
                this.current.visit();
            }
            //3. Else if neighbor doesn't exist
            else {
                //1. pop cell from stack and set it to current cell
                this.current = this.stack.pop();
            }
        } while(!stack.empty());

        return mazeModel;
    }
}
