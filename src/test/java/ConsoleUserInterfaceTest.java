import backend.academy.maze.Coordinate;
import backend.academy.interaction.IOHandler;
import backend.academy.maze.Maze;
import backend.academy.interaction.ConsoleUserInterface;
import backend.academy.generators.Generator;
import backend.academy.generators.GrowingTreeMazeGenerator;
import backend.academy.generators.KruskalMazeGenerator;
import backend.academy.solvers.AStarSolver;
import backend.academy.solvers.ShortestPathFinder;
import backend.academy.solvers.Solver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsoleUserInterfaceTest {

    private IOHandler ioHandler;
    private ConsoleUserInterface consoleUserInterface;

    @BeforeEach
    void setUp() {
        ioHandler = mock(IOHandler.class);
        consoleUserInterface = new ConsoleUserInterface(ioHandler);
    }

    @Test
    void testChooseMazeSize_small() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("1");

        // Act
        Maze maze = consoleUserInterface.chooseMazeSize();

        // Assert
        assertNotNull(maze);
        assertEquals(ConsoleUserInterface.SMALL_MAZE.getRow(), maze.getHeight());
        assertEquals(ConsoleUserInterface.SMALL_MAZE.getCol(), maze.getWidth());
    }

    @Test
    void testChooseMazeSize_medium() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("2");

        // Act
        Maze maze = consoleUserInterface.chooseMazeSize();

        // Assert
        assertNotNull(maze);
        assertEquals(ConsoleUserInterface.MEDIUM_MAZE.getRow(), maze.getHeight());
        assertEquals(ConsoleUserInterface.MEDIUM_MAZE.getCol(), maze.getWidth());
    }

    @Test
    void testChooseMazeSize_large() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("3");

        // Act
        Maze maze = consoleUserInterface.chooseMazeSize();

        // Assert
        assertNotNull(maze);
        assertEquals(ConsoleUserInterface.LARGE_MAZE.getRow(), maze.getHeight());
        assertEquals(ConsoleUserInterface.LARGE_MAZE.getCol(), maze.getWidth());
    }

    @Test
    void testChooseMazeSize_invalidInput() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("invalid", "4", "1");

        // Act
        Maze maze = consoleUserInterface.chooseMazeSize();

        // Assert
        assertNotNull(maze);
        assertEquals(ConsoleUserInterface.SMALL_MAZE.getRow(), maze.getHeight());
        assertEquals(ConsoleUserInterface.SMALL_MAZE.getCol(), maze.getWidth());
        verify(ioHandler, times(3)).writeLine(anyString());
    }

    @Test
    void testChooseMazeGenerator_kruskal() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("1");

        // Act
        Generator generator = consoleUserInterface.chooseMazeGenerator();

        // Assert
        assertTrue(generator instanceof KruskalMazeGenerator);
    }

    @Test
    void testChooseMazeGenerator_growingTree() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("2");

        // Act
        Generator generator = consoleUserInterface.chooseMazeGenerator();

        // Assert
        assertTrue(generator instanceof GrowingTreeMazeGenerator);
    }

    @Test
    void testChooseMazeGenerator_invalidInput() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("invalid", "3", "1");

        // Act
        Generator generator = consoleUserInterface.chooseMazeGenerator();

        // Assert
        assertTrue(generator instanceof KruskalMazeGenerator);
        verify(ioHandler, times(5)).writeLine(anyString());
    }

    @Test
    void testChooseSolver_shortestPathFinder() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("1");

        // Act
        Solver solver = consoleUserInterface.chooseSolver();

        // Assert
        assertTrue(solver instanceof ShortestPathFinder);
    }

    @Test
    void testChooseSolver_aStarSolver() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("2");

        // Act
        Solver solver = consoleUserInterface.chooseSolver();

        // Assert
        assertTrue(solver instanceof AStarSolver);
    }

    @Test
    void testGetCoordinate_validInput() throws IOException {
        // Arrange
        Maze maze = new Maze(10, 10);
        when(ioHandler.readLine()).thenReturn("5,5");

        // Act
        Coordinate coordinate = consoleUserInterface.getCoordinate("Введите координаты: ", maze);

        // Assert
        assertEquals(4, coordinate.getRow());
        assertEquals(4, coordinate.getCol());
    }

    @Test
    void testGetCoordinate_invalidInput() throws IOException {
        // Arrange
        Maze maze = new Maze(10, 10);
        when(ioHandler.readLine()).thenReturn("invalid", "11,11", "5,5");

        // Act
        Coordinate coordinate = consoleUserInterface.getCoordinate("Введите координаты: ", maze);

        // Assert
        assertEquals(4, coordinate.getRow());
        assertEquals(4, coordinate.getCol());
        verify(ioHandler, times(2)).writeLine(anyString());
    }

    @Test
    void testAskUserToAddSurfaces_yes() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("да");

        // Act
        boolean result = consoleUserInterface.askUserToAddSurfaces();

        // Assert
        assertTrue(result);
    }

    @Test
    void testAskUserToAddSurfaces_no() throws IOException {
        // Arrange
        when(ioHandler.readLine()).thenReturn("нет");

        // Act
        boolean result = consoleUserInterface.askUserToAddSurfaces();

        // Assert
        assertFalse(result);
    }
}
