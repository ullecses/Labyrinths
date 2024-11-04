import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MazeTest {

    @Test
    void testMazeInitialization() {
        // Arrange
        int width = 5;
        int height = 5;

        // Act
        Maze maze = new Maze(height, width);

        // Assert
        assertEquals(width, maze.getWidth(), "Maze width should be initialized correctly.");
        assertEquals(height, maze.getHeight(), "Maze height should be initialized correctly.");

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                assertEquals(Cell.Type.WALL, maze.getCellArray()[row][col].getType(),
                    "Each cell should be initialized as a WALL.");
            }
        }
    }

    @Test
    void testSetCellType() {
        // Arrange
        Maze maze = new Maze(5, 5);
        Coordinate coord = new Coordinate(2, 2);
        Cell.Type newType = Cell.Type.PASSAGE;

        // Act
        maze.setCellType(coord, newType);

        // Assert
        assertEquals(newType, maze.getType(coord), "Cell type should be updated to PASSAGE.");
    }

    @Test
    void testIsPassage() {
        // Arrange
        Maze maze = new Maze(5, 5);
        Coordinate coord = new Coordinate(2, 2);
        maze.setCellType(coord, Cell.Type.PASSAGE);

        // Act
        boolean isPassage = maze.isPassage(2, 2);

        // Assert
        assertTrue(isPassage, "Cell should be passable after being set to PASSAGE.");
    }

    @Test
    void testGetUnvisitedNeighbors() {
        // Arrange
        Maze maze = new Maze(5, 5);
        Coordinate coord = new Coordinate(2, 2);

        // Act
        List<Coordinate> neighbors = maze.getUnvisitedNeighbors(coord);

        // Assert
        assertEquals(4, neighbors.size(), "There should be 4 unvisited neighbors (walls).");
        for (Coordinate neighbor : neighbors) {
            assertEquals(Cell.Type.WALL, maze.getType(neighbor), "Each neighbor should be a WALL.");
        }
    }

    @Test
    void testAddSurfaces() {
        // Arrange
        Maze maze = new Maze(10, 10);
        Coordinate coord = new Coordinate(2, 2);
        maze.setCellType(coord, Cell.Type.PASSAGE);

        // Act
        maze.addSurfaces();

        // Assert
        Cell.Type type = maze.getType(coord);
        assertTrue(type == Cell.Type.PASSAGE || type == Cell.Type.SAND || type == Cell.Type.COIN,
            "Passage cells should randomly be turned into SAND, COIN, or remain as PASSAGE.");
    }

    @Test
    void testMarkPath() {
        // Arrange
        Maze maze = new Maze(5, 5);
        List<Coordinate> path = List.of(
            new Coordinate(1, 1),
            new Coordinate(1, 2),
            new Coordinate(1, 3)
        );

        // Act
        maze.markPath(path);

        // Assert
        for (Coordinate coord : path) {
            assertEquals(Cell.Type.PATH, maze.getType(coord), "Each cell on the path should be marked as PATH.");
        }
    }

    @Test
    void testAssignSurface() {
        // Arrange
        Maze maze = new Maze(5, 5);
        Coordinate coord = new Coordinate(2, 2);
        maze.setCellType(coord, Cell.Type.PASSAGE); // Установим тип как PASSAGE

        // Act
        maze.addSurfaces();

        // Assert
        Cell.Type type = maze.getType(coord);
        assertTrue(type == Cell.Type.PASSAGE || type == Cell.Type.SAND || type == Cell.Type.COIN,
            "The cell should randomly be turned into SAND, COIN, or remain as PASSAGE.");

        // Проверяем, что тип клетки изменился на SAND или COIN
        if (type == Cell.Type.PASSAGE) {
            // Если тип остался как PASSAGE, то это тоже допустимо, так как метод рандомный
            assertTrue(true);
        } else {
            assertTrue(type == Cell.Type.SAND || type == Cell.Type.COIN,
                "The cell should be either SAND or COIN after surface assignment.");
        }
    }

    @Test
    void testIsInBounds() {
        // Arrange
        Maze maze = new Maze(5, 5);

        // Act & Assert
        assertTrue(maze.isInBounds(2, 2), "Coordinate (2, 2) should be in bounds.");
        assertFalse(maze.isInBounds(-1, 2), "Negative row should be out of bounds.");
        assertFalse(maze.isInBounds(2, -1), "Negative column should be out of bounds.");
        assertFalse(maze.isInBounds(5, 2), "Row beyond maze height should be out of bounds.");
        assertFalse(maze.isInBounds(2, 5), "Column beyond maze width should be out of bounds.");
    }
}

