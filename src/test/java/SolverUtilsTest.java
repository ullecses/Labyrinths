import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.Cell;
import backend.academy.solvers.SolverUtils;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolverUtilsTest {

    @Test
    public void testGetNeighbors_MiddleCell() {
        // Arrange
        Maze maze = new Maze(3, 3);
        maze.getGrid()[0][0] = new Cell(0, 0, Cell.Type.PASSAGE);
        maze.getGrid()[0][1] = new Cell(0, 1, Cell.Type.PASSAGE);
        maze.getGrid()[0][2] = new Cell(0, 2, Cell.Type.WALL);

        maze.getGrid()[1][0] = new Cell(1, 0, Cell.Type.PASSAGE);
        maze.getGrid()[1][1] = new Cell(1, 1, Cell.Type.PASSAGE);
        maze.getGrid()[1][2] = new Cell(1, 2, Cell.Type.PASSAGE);

        maze.getGrid()[2][0] = new Cell(2, 0, Cell.Type.WALL);
        maze.getGrid()[2][1] = new Cell(2, 1, Cell.Type.PASSAGE);
        maze.getGrid()[2][2] = new Cell(2, 2, Cell.Type.PASSAGE);

        Coordinate coord = new Coordinate(1, 1);

        // Act
        List<Coordinate> neighbors = SolverUtils.getNeighbors(maze, coord);

        // Assert
        assertEquals(4, neighbors.size());
        assertTrue(neighbors.contains(new Coordinate(0, 1))); // Вверх
        assertTrue(neighbors.contains(new Coordinate(2, 1))); // Вниз
        assertTrue(neighbors.contains(new Coordinate(1, 0))); // Влево
        assertTrue(neighbors.contains(new Coordinate(1, 2))); // Вправо
    }

    @Test
    public void testGetNeighbors_EdgeCell() {
        // Arrange
        Maze maze = new Maze(3, 3);
        maze.getGrid()[0][0] = new Cell(0, 0, Cell.Type.PASSAGE);
        maze.getGrid()[0][1] = new Cell(0, 1, Cell.Type.WALL);
        maze.getGrid()[0][2] = new Cell(0, 2, Cell.Type.WALL);

        maze.getGrid()[1][0] = new Cell(1, 0, Cell.Type.PASSAGE);
        maze.getGrid()[1][1] = new Cell(1, 1, Cell.Type.WALL);
        maze.getGrid()[1][2] = new Cell(1, 2, Cell.Type.PASSAGE);

        maze.getGrid()[2][0] = new Cell(2, 0, Cell.Type.WALL);
        maze.getGrid()[2][1] = new Cell(2, 1, Cell.Type.PASSAGE);
        maze.getGrid()[2][2] = new Cell(2, 2, Cell.Type.PASSAGE);

        Coordinate coord = new Coordinate(0, 0);

        // Act
        List<Coordinate> neighbors = SolverUtils.getNeighbors(maze, coord);

        // Assert
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(new Coordinate(1, 0)));
    }

    @Test
    public void testGetNeighbors_CornerCell() {
        // Arrange
        Maze maze = new Maze(3, 3);
        maze.getGrid()[0][0] = new Cell(0, 0, Cell.Type.WALL);
        maze.getGrid()[0][1] = new Cell(0, 1, Cell.Type.WALL);
        maze.getGrid()[0][2] = new Cell(0, 2, Cell.Type.WALL);

        maze.getGrid()[1][0] = new Cell(1, 0, Cell.Type.PASSAGE);
        maze.getGrid()[1][1] = new Cell(1, 1, Cell.Type.WALL);
        maze.getGrid()[1][2] = new Cell(1, 2, Cell.Type.PASSAGE);

        maze.getGrid()[2][0] = new Cell(2, 0, Cell.Type.WALL);
        maze.getGrid()[2][1] = new Cell(2, 1, Cell.Type.PASSAGE);
        maze.getGrid()[2][2] = new Cell(2, 2, Cell.Type.WALL);

        Coordinate coord = new Coordinate(2, 1);

        // Act
        List<Coordinate> neighbors = SolverUtils.getNeighbors(maze, coord);

        // Assert
        assertEquals(0, neighbors.size());
    }

    @Test
    public void testGetNeighbors_NoNeighbors() {
        // Arrange
        Maze maze = new Maze(3, 3);
        maze.getGrid()[0][0] = new Cell(0, 0, Cell.Type.WALL);
        maze.getGrid()[0][1] = new Cell(0, 1, Cell.Type.WALL);
        maze.getGrid()[0][2] = new Cell(0, 2, Cell.Type.WALL);

        maze.getGrid()[1][0] = new Cell(1, 0, Cell.Type.WALL);
        maze.getGrid()[1][1] = new Cell(1, 1, Cell.Type.WALL);
        maze.getGrid()[1][2] = new Cell(1, 2, Cell.Type.WALL);

        maze.getGrid()[2][0] = new Cell(2, 0, Cell.Type.WALL);
        maze.getGrid()[2][1] = new Cell(2, 1, Cell.Type.WALL);
        maze.getGrid()[2][2] = new Cell(2, 2, Cell.Type.WALL);

        Coordinate coord = new Coordinate(1, 1);

        // Act
        List<Coordinate> neighbors = SolverUtils.getNeighbors(maze, coord);

        // Assert
        assertTrue(neighbors.isEmpty());
    }
}
