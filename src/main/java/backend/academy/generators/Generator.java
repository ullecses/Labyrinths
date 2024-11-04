package backend.academy.generators;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;

public interface Generator {
    Maze generate(Maze maze, Coordinate start, Coordinate end);
}
