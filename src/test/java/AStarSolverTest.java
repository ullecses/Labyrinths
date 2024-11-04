import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.solvers.AStarSolver;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AStarSolverTest {

    private final AStarSolver solver = new AStarSolver();

    @Test
    void testSimpleMaze() {
        // Arrange
        Maze maze = createMaze(5, 5, Cell.Type.PASSAGE);
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals(start, path.get(0));
        assertEquals(end, path.get(path.size() - 1));
    }

    @Test
    void testMazeWithWall() {
        // Arrange
        Maze maze = createMaze(5, 5, Cell.Type.PASSAGE);
        maze.setCellType(new Coordinate(2, 2), Cell.Type.WALL);
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertNotNull(path);
        assertFalse(path.contains(new Coordinate(2, 2)));
    }

    @Test
    void testMazeWithSand() {
        // Arrange
        Maze maze = createMaze(5, 5, Cell.Type.PASSAGE);
        maze.setCellType(new Coordinate(2, 2), Cell.Type.SAND);
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertNotNull(path);
        assertNotEquals(new Coordinate(2, 2), path.get(path.size() / 2));
    }

    @Test
    void testMazeWithCoin() {
        // Arrange
        Maze maze = createMaze(5, 5, Cell.Type.PASSAGE);
        maze.setCellType(new Coordinate(2, 2), Cell.Type.COIN);
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertNotNull(path);
        assertTrue(path.contains(new Coordinate(2, 2)));
    }

    @Test
    void testMazeBlockedStart() {
        // Arrange
        Maze maze = createMaze(5, 5, Cell.Type.WALL);
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);
        maze.setCellType(end, Cell.Type.PASSAGE);

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertTrue(path.isEmpty());
    }

    private Maze createMaze(int height, int width, Cell.Type defaultType) {
        Maze maze = new Maze(height, width);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                maze.setCellType(new Coordinate(row, col), defaultType);
            }
        }
        return maze;
    }
}
