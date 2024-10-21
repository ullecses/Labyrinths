import backend.academy.Cell;
import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.generators.KruskalMazeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KruskalMazeGeneratorTest {

    private KruskalMazeGenerator generator;
    private Maze maze;

    @BeforeEach
    void setUp() {
        generator = new KruskalMazeGenerator();
        maze = new Maze(10, 10);  // Лабиринт размером 10x10
    }

    @Test
    void testMazeGeneratedSuccessfully() {
        // Arrange
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(9, 9);

        // Act
        Maze result = generator.generate(maze, start, end);

        // Assert
        assertNotNull(result, "Генерация лабиринта должна возвращать непустой объект.");
    }

    @Test
    void testStartAndEndArePassages() {
        // Arrange
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(9, 9);

        // Act
        Maze result = generator.generate(maze, start, end);

        // Assert
        assertEquals(Cell.Type.PASSAGE, result.getType(start), "Стартовая клетка должна быть проходом.");
        assertEquals(Cell.Type.PASSAGE, result.getType(end), "Конечная клетка должна быть проходом.");
    }

    @Test
    void testAllCellsInitiallyWalls() {

        // Assert
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Coordinate coord = new Coordinate(row, col);
                assertEquals(Cell.Type.WALL, maze.getType(coord), "Все клетки должны быть стенами до начала генерации.");
            }
        }
    }

    @Test
    void testPassagesCreatedBetweenConnectedCells() {
        // Arrange
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(9, 9);

        // Act
        Maze result = generator.generate(maze, start, end);

        // Assert
        for (int row = 0; row < result.getHeight(); row++) {
            for (int col = 0; col < result.getWidth(); col++) {
                Cell.Type type = result.getType(new Coordinate(row, col));
                assertNotNull(type, "Каждая клетка должна иметь тип после генерации.");
                assertTrue(type == Cell.Type.WALL || type == Cell.Type.PASSAGE, "Тип каждой клетки должен быть либо стеной, либо проходом.");
            }
        }
    }

    @Test
    void testDisjointSetFindUnion() {
        // Arrange
        KruskalMazeGenerator.DisjointSet ds = new KruskalMazeGenerator.DisjointSet(10);

        // Act
        ds.union(0, 1);
        ds.union(2, 3);
        ds.union(1, 2);

        // Assert
        assertEquals(ds.find(0), ds.find(3), "Клетки 0 и 3 должны быть в одном множестве.");
        assertNotEquals(ds.find(0), ds.find(4), "Клетки 0 и 4 должны находиться в разных множествах.");
    }
}
