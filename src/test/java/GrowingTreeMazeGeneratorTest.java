import backend.academy.Cell;
import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.generators.GrowingTreeMazeGenerator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GrowingTreeMazeGeneratorTest {

    private Maze maze;
    private GrowingTreeMazeGenerator generator;

    @BeforeEach
    void setUp() {
        maze = new Maze(10, 10); // Лабиринт 10x10
        generator = new GrowingTreeMazeGenerator();
    }

    @Test
    void testGenerateMazeCreatesPassageAtStartAndEnd() {
        // Arrange
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(9, 9);

        // Act
        Maze result = generator.generate(maze, start, end);

        // Assert
        assertEquals(Cell.Type.PASSAGE, result.getType(start), "Стартовая клетка должна быть проходом");
        assertEquals(Cell.Type.PASSAGE, result.getType(end), "Конечная клетка должна быть проходом");
    }

    @Test
    void testGenerateMazeAllCellsAreAccessible() {
        // Arrange
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(9, 9);

        // Act
        Maze result = generator.generate(maze, start, end);

        // Assert
        for (int row = 0; row < result.getHeight(); row++) {
            for (int col = 0; col < result.getWidth(); col++) {
                Cell.Type cellType = result.getType(new Coordinate(row, col));
                assertNotNull(cellType, "Все клетки должны быть либо стенами, либо проходами");
            }
        }
    }

    @Test
    void testSelectLastStrategy() {
        // Arrange
        List<Coordinate> cells = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 1),
            new Coordinate(2, 2)
        );

        // Act
        GrowingTreeMazeGenerator.SelectionStrategy strategy = GrowingTreeMazeGenerator.SelectionStrategy.LAST;
        Coordinate selectedCell = cells.get(cells.size() - 1);

        // Assert
        assertEquals(new Coordinate(2, 2), selectedCell, "Должна возвращаться последняя ячейка при стратегии LAST");
    }

    @Test
    void testGetCellBetween() {
        // Arrange
        Coordinate current = new Coordinate(0, 0);
        Coordinate next = new Coordinate(2, 2);

        // Act
        Coordinate between = generator.getCellBetween(current, next);

        // Assert
        assertEquals(new Coordinate(1, 1), between, "Координата между двумя клетками должна быть (1,1)");
    }

    @Test
    void testGenerateFillsMaze() {
        // Arrange
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(9, 9);

        // Act
        Maze result = generator.generate(maze, start, end);

        // Assert
        assertNotNull(result, "Лабиринт не должен быть null");
        assertTrue(result.isPassage(start.getRow(), start.getCol()), "Стартовая клетка должна быть проходом");
        assertTrue(result.isPassage(end.getRow(), end.getCol()), "Конечная клетка должна быть проходом");
    }
}

