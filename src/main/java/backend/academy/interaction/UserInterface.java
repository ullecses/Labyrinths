package backend.academy.interaction;

import backend.academy.generators.Generator;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.solvers.Solver;
import java.io.IOException;

public interface UserInterface {

    Maze chooseMazeSize() throws IOException;

    Generator chooseMazeGenerator() throws IOException;

    Solver chooseSolver() throws IOException;

    Coordinate getCoordinate(String prompt, Maze maze) throws IOException;

    boolean askUserToAddSurfaces() throws IOException;

    void displayMaze(Maze maze);
}
