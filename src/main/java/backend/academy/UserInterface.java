package backend.academy;

import backend.academy.generators.Generator;
import backend.academy.solvers.Solver;
import java.io.IOException;

public interface UserInterface {

    Maze chooseMazeSize() throws IOException;

    Generator chooseMazeGenerator() throws IOException;

    Solver chooseSolver() throws IOException;

    Coordinate getCoordinate(String prompt, Maze maze) throws IOException;

    boolean askUserToAddSurfaces() throws IOException;

    void displayMaze(Maze maze);

    String getDisplaySymbol(Cell cell);
}
