import backend.academy.Cell;
import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.solvers.ShortestPathFinder;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShortestPathFinderTest {

    @Test
    public void testSimpleMaze() {
        // Arrange
        Maze maze = new Maze(3, 3);

        // Инициализируем клетки вручную
        maze.getGrid()[0][0] = new Cell(0, 0, Cell.Type.PASSAGE);
        maze.getGrid()[0][1] = new Cell(0, 1, Cell.Type.PASSAGE);
        maze.getGrid()[0][2] = new Cell(0, 2, Cell.Type.WALL);

        maze.getGrid()[1][0] = new Cell(1, 0, Cell.Type.PASSAGE);
        maze.getGrid()[1][1] = new Cell(1, 1, Cell.Type.PASSAGE);
        maze.getGrid()[1][2] = new Cell(1, 2, Cell.Type.PASSAGE);

        maze.getGrid()[2][0] = new Cell(2, 0, Cell.Type.WALL);
        maze.getGrid()[2][1] = new Cell(2, 1, Cell.Type.PASSAGE);
        maze.getGrid()[2][2] = new Cell(2, 2, Cell.Type.PASSAGE);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);

        ShortestPathFinder solver = new ShortestPathFinder();

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals(List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(1, 1),
            new Coordinate(2, 1),
            new Coordinate(2, 2)
        ), path);
    }

    @Test
    public void testPartiallyBlockedEnd() {
        // Arrange
        Maze maze = new Maze(3, 3);

        maze.getGrid()[0][0] = new Cell(0, 0, Cell.Type.PASSAGE);
        maze.getGrid()[0][1] = new Cell(0, 1, Cell.Type.PASSAGE);
        maze.getGrid()[0][2] = new Cell(0, 2, Cell.Type.WALL);

        maze.getGrid()[1][0] = new Cell(1, 0, Cell.Type.PASSAGE);
        maze.getGrid()[1][1] = new Cell(1, 1, Cell.Type.WALL);
        maze.getGrid()[1][2] = new Cell(1, 2, Cell.Type.PASSAGE);

        maze.getGrid()[2][0] = new Cell(2, 0, Cell.Type.PASSAGE);
        maze.getGrid()[2][1] = new Cell(2, 1, Cell.Type.PASSAGE);
        maze.getGrid()[2][2] = new Cell(2, 2, Cell.Type.PASSAGE);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);

        ShortestPathFinder solver = new ShortestPathFinder();

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals(new Coordinate(2, 2), path.get(path.size() - 1)); // Проверяем, что путь заканчивается на (2, 2)
    }

    @Test
    public void testMazeWithSandAndCoins() {
        // Arrange
        Maze maze = new Maze(3, 3);

        maze.getGrid()[0][0] = new Cell(0, 0, Cell.Type.PASSAGE);
        maze.getGrid()[0][1] = new Cell(0, 1, Cell.Type.PASSAGE);
        maze.getGrid()[0][2] = new Cell(0, 2, Cell.Type.SAND);

        maze.getGrid()[1][0] = new Cell(1, 0, Cell.Type.COIN);
        maze.getGrid()[1][1] = new Cell(1, 1, Cell.Type.WALL);
        maze.getGrid()[1][2] = new Cell(1, 2, Cell.Type.PASSAGE);

        maze.getGrid()[2][0] = new Cell(2, 0, Cell.Type.WALL);
        maze.getGrid()[2][1] = new Cell(2, 1, Cell.Type.SAND);
        maze.getGrid()[2][2] = new Cell(2, 2, Cell.Type.PASSAGE);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);

        ShortestPathFinder solver = new ShortestPathFinder();

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals(List.of(
            new Coordinate(0, 0),
            new Coordinate(0, 1),
            new Coordinate(0, 2),
            new Coordinate(1, 2),
            new Coordinate(2, 2)
        ), path);
    }

    @Test
    public void testStartEqualsEnd() {
        // Arrange
        Maze maze = new Maze(2, 2);

        maze.getGrid()[0][0] = new Cell(0, 0, Cell.Type.PASSAGE);
        maze.getGrid()[0][1] = new Cell(0, 1, Cell.Type.WALL);

        maze.getGrid()[1][0] = new Cell(1, 0, Cell.Type.PASSAGE);
        maze.getGrid()[1][1] = new Cell(1, 1, Cell.Type.PASSAGE);

        Coordinate start = new Coordinate(1, 1); // Начало и конец — одна и та же точка
        Coordinate end = new Coordinate(1, 1);

        ShortestPathFinder solver = new ShortestPathFinder();

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertNotNull(path);
        assertEquals(1, path.size()); // Путь должен содержать только одну клетку
        assertEquals(start, path.get(0)); // Путь должен состоять из стартовой точки
    }
}
