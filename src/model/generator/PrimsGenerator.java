package model.generator;

import model.Cell;
import model.MazeModel;

import java.util.*;

/**
 * Created by lak1044 on 2/25/2017.
 */
public class PrimsGenerator extends Generator {
    private ArrayList<Cell> frontier;

    public PrimsGenerator() {
        super();
        this.frontier = new ArrayList<>();
        MazeModel.current = this.maze[(int)Math.floor(Math.random() * MazeModel.rows)][(int)Math.floor(Math.random() * MazeModel.cols)];
        MazeModel.current.visit();
        MazeModel.current.setPermanent();
        for (Cell cell: this.getRandomNeighbors(MazeModel.current)) {
            cell.setParent(MazeModel.current);
            MazeModel.current.setChildren(cell);
            cell.visit();
            cell.setTemporary();
            this.frontier.add(cell);
        }
        System.out.println("PrimsGenerator Created");
    }

    @Override
    public void generate() {
        Cell parent;
        if (!frontier.isEmpty()) {
            MazeModel.lastCell = MazeModel.current;
            MazeModel.current = frontier.remove(0);
             do  {
                 parent = this.getRandomVisitedNeighbor(MazeModel.current);
             } while (!parent.getPermanent() || parent.getTemporary());
            removeCommonWall(MazeModel.current, parent);
            MazeModel.current.setParent(parent);
            MazeModel.current.setPermanent();

            for (Cell cell: this.getRandomNeighbors(MazeModel.current)) {
                cell.setParent(MazeModel.current);
                MazeModel.current.setChildren(cell);
                cell.visit();
                cell.setTemporary();
                this.frontier.add(cell);
            }

            Collections.shuffle(this.frontier);
        }
        else {
            MazeModel.current = MazeModel.start;
            this.setGenerated();
        }
    }
}
