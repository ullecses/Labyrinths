import backend.academy.maze.Cell;
import backend.academy.maze.Cell.Type;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellTest {

    @Test
    void testCellInitialization() {
        // Arrange
        int row = 1;
        int col = 2;
        Type type = Type.PASSAGE;

        // Act
        Cell cell = new Cell(type);

        // Assert
        assertEquals(Type.PASSAGE, cell.getType(), "Cell type should be PASSAGE after initialization");
    }

    @Test
    void testSetType() {
        // Arrange
        Cell cell = new Cell(Type.PASSAGE);
        Type newType = Type.WALL;

        // Act
        cell.setType(newType);

        // Assert
        assertEquals(Type.WALL, cell.getType(), "Cell type should be WALL after setting it");
    }

    @Test
    void testIsPassageTrue() {
        // Arrange
        Cell cell = new Cell(Type.PASSAGE);

        // Act
        boolean result = cell.isPassage();

        // Assert
        assertTrue(result, "Cell should be passable if type is PASSAGE");
    }

    @Test
    void testIsPassageFalse() {
        // Arrange
        Cell cell = new Cell(Type.WALL);

        // Act
        boolean result = cell.isPassage();

        // Assert
        assertFalse(result, "Cell should not be passable if type is WALL");
    }

    @Test
    void testCellTypeSand() {
        // Arrange
        int row = 1;
        int col = 2;
        Type type = Type.SAND;

        // Act
        Cell cell = new Cell(type);

        // Assert
        assertEquals(Type.SAND, cell.getType(), "Cell type should be SAND");
    }

    @Test
    void testCellTypeCoin() {
        // Arrange
        int row = 1;
        int col = 2;
        Type type = Type.COIN;

        // Act
        Cell cell = new Cell(type);

        // Assert
        assertEquals(Type.COIN, cell.getType(), "Cell type should be COIN");
    }

    @Test
    void testCellTypePath() {
        // Arrange
        int row = 1;
        int col = 2;
        Type type = Type.PATH;

        // Act
        Cell cell = new Cell(type);

        // Assert
        assertEquals(Type.PATH, cell.getType(), "Cell type should be PATH");
    }
}
