import backend.academy.maze.Coordinate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CoordinateTest {

    @Test
    void testCoordinateInitialization() {
        // Arrange
        int row = 3;
        int col = 5;

        // Act
        Coordinate coord = new Coordinate(row, col);

        // Assert
        assertEquals(row, coord.row(), "Row value should be initialized correctly.");
        assertEquals(col, coord.col(), "Column value should be initialized correctly.");
    }

    @Test
    void testGetRow() {
        // Arrange
        Coordinate coord = new Coordinate(2, 4);

        // Act
        int row = coord.getRow();

        // Assert
        assertEquals(2, row, "getRow() should return the correct row value.");
    }

    @Test
    void testGetCol() {
        // Arrange
        Coordinate coord = new Coordinate(2, 4);

        // Act
        int col = coord.getCol();

        // Assert
        assertEquals(4, col, "getCol() should return the correct column value.");
    }

    @Test
    void testEquality() {
        // Arrange
        Coordinate coord1 = new Coordinate(3, 5);
        Coordinate coord2 = new Coordinate(3, 5);

        // Act & Assert
        assertEquals(coord1, coord2, "Two coordinates with the same row and column should be equal.");
    }

    @Test
    void testInequality() {
        // Arrange
        Coordinate coord1 = new Coordinate(3, 5);
        Coordinate coord2 = new Coordinate(4, 5);

        // Act & Assert
        assertNotEquals(coord1, coord2, "Coordinates with different rows or columns should not be equal.");
    }

    @Test
    void testHashCode() {
        // Arrange
        Coordinate coord1 = new Coordinate(3, 5);
        Coordinate coord2 = new Coordinate(3, 5);

        // Act & Assert
        assertEquals(coord1.hashCode(), coord2.hashCode(), "Equal coordinates should have the same hash code.");
    }
}
