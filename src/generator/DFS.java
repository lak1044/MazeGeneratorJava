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
        this.mazeModel = new MazeModel(mazeModel.rows, mazeModel.cols);
        this.stack = new Stack<>();
        this.current = this.mazeModel.maze[0][0];
        this.current.visit();
        this.stack.push(this.current);
    }

    public Cell[][] generate() {
        System.out.println("Starting DFS Generation");

        while(!this.stack.empty()) {
            //1. Get random neighbor of current cell and set it to next
            this.next = this.mazeModel.getRandomNeighbor(current);
            //2. If neighbor exists
            if (this.next != null) {
                //1. remove the common wall between current and next
                this.mazeModel.removeCommonWall(this.current, this.next);
                //2. push current cell to stack
                this.stack.push(current);
                //3. set current cell to next cell
                this.current = this.next;
                //4. visit current cell
                this.current.visit();
            }
            //3. Else if neighbor doesn't exist
            else {
                //1. pop cell from stack and set it to current cell
                this.current = this.stack.pop();
            }
        }
        System.out.println("DFS Maze Generated");
        mazeModel.announceChange();
        return this.mazeModel.maze;
    }
}
