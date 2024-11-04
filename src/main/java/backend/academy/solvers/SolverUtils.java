package backend.academy.solvers;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.ArrayList;
import java.util.List;

public class SolverUtils {
    private SolverUtils() {
        // Приватный конструктор для предотвращения создания экземпляров этого класса
    }

    public static List<Coordinate> getNeighbors(Maze maze, Coordinate coord) {
        List<Coordinate> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Вверх, вниз, влево, вправо

        for (int[] direction : directions) {
            int newRow = coord.row() + direction[0];
            int newCol = coord.col() + direction[1];

            if (maze.isInBounds(newRow, newCol) && maze.isPassage(newRow, newCol)) {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }

        return neighbors;
    }
}

