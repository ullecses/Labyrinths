package backend.academy.generators;

import backend.academy.Coordinate;
import backend.academy.Maze;

public interface Generator {
    Maze generate(Maze maze, Coordinate start, Coordinate end);
}
