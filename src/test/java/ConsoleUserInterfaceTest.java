import backend.academy.Coordinate;
import backend.academy.IOHandler;
import backend.academy.Maze;
import backend.academy.ConsoleUserInterface;
import backend.academy.generators.Generator;
import backend.academy.generators.GrowingTreeMazeGenerator;
import backend.academy.generators.KruskalMazeGenerator;
import backend.academy.solvers.AStarSolver;
import backend.academy.solvers.ShortestPathFinder;
import backend.academy.solvers.Solver;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(ioHandler.readLine()).thenReturn("1");

        Maze maze = consoleUserInterface.chooseMazeSize();

        assertNotNull(maze);
        assertEquals(ConsoleUserInterface.SMALL_MAZE.getRow(), maze.getHeight());
        assertEquals(ConsoleUserInterface.SMALL_MAZE.getCol(), maze.getWidth());
    }

    @Test
    void testChooseMazeSize_medium() throws IOException {
        when(ioHandler.readLine()).thenReturn("2");

        Maze maze = consoleUserInterface.chooseMazeSize();

        assertNotNull(maze);
        assertEquals(ConsoleUserInterface.MEDIUM_MAZE.getRow(), maze.getHeight());
        assertEquals(ConsoleUserInterface.MEDIUM_MAZE.getCol(), maze.getWidth());
    }

    @Test
    void testChooseMazeSize_large() throws IOException {
        when(ioHandler.readLine()).thenReturn("3");

        Maze maze = consoleUserInterface.chooseMazeSize();

        assertNotNull(maze);
        assertEquals(ConsoleUserInterface.LARGE_MAZE.getRow(), maze.getHeight());
        assertEquals(ConsoleUserInterface.LARGE_MAZE.getCol(), maze.getWidth());
    }

    @Test
    void testChooseMazeSize_invalidInput() throws IOException {
        when(ioHandler.readLine()).thenReturn("invalid", "4", "1");

        Maze maze = consoleUserInterface.chooseMazeSize();

        assertNotNull(maze);
        assertEquals(ConsoleUserInterface.SMALL_MAZE.getRow(), maze.getHeight());
        assertEquals(ConsoleUserInterface.SMALL_MAZE.getCol(), maze.getWidth());

        verify(ioHandler, times(3)).writeLine(anyString()); // Проверка, что сообщение об ошибке вывелось
    }

    @Test
    void testChooseMazeGenerator_kruskal() throws IOException {
        when(ioHandler.readLine()).thenReturn("1");

        Generator generator = consoleUserInterface.chooseMazeGenerator();

        assertTrue(generator instanceof KruskalMazeGenerator);
    }

    @Test
    void testChooseMazeGenerator_growingTree() throws IOException {
        when(ioHandler.readLine()).thenReturn("2");

        Generator generator = consoleUserInterface.chooseMazeGenerator();

        assertTrue(generator instanceof GrowingTreeMazeGenerator);
    }

    @Test
    void testChooseMazeGenerator_invalidInput() throws IOException {
        when(ioHandler.readLine()).thenReturn("invalid", "3", "1");

        Generator generator = consoleUserInterface.chooseMazeGenerator();

        assertTrue(generator instanceof KruskalMazeGenerator);
        verify(ioHandler, times(5)).writeLine(anyString()); // Проверка, что сообщение об ошибке вывелось
    }

    @Test
    void testChooseSolver_shortestPathFinder() throws IOException {
        when(ioHandler.readLine()).thenReturn("1");

        Solver solver = consoleUserInterface.chooseSolver();

        assertTrue(solver instanceof ShortestPathFinder);
    }

    @Test
    void testChooseSolver_aStarSolver() throws IOException {
        when(ioHandler.readLine()).thenReturn("2");

        Solver solver = consoleUserInterface.chooseSolver();

        assertTrue(solver instanceof AStarSolver);
    }

    @Test
    void testGetCoordinate_validInput() throws IOException {
        Maze maze = new Maze(10, 10); // создаем лабиринт 10x10
        when(ioHandler.readLine()).thenReturn("5,5");

        Coordinate coordinate = consoleUserInterface.getCoordinate("Введите координаты: ", maze);

        assertEquals(4, coordinate.getRow()); // Проверка на 1-индексацию
        assertEquals(4, coordinate.getCol());
    }

    @Test
    void testGetCoordinate_invalidInput() throws IOException {
        Maze maze = new Maze(10, 10); // создаем лабиринт 10x10
        when(ioHandler.readLine()).thenReturn("invalid", "11,11", "5,5");

        Coordinate coordinate = consoleUserInterface.getCoordinate("Введите координаты: ", maze);

        assertEquals(4, coordinate.getRow()); // Проверка на 1-индексацию
        assertEquals(4, coordinate.getCol());
        verify(ioHandler, times(2)).writeLine(anyString()); // Проверка, что сообщение об ошибке вывелось
    }

    @Test
    void testAskUserToAddSurfaces_yes() throws IOException {
        when(ioHandler.readLine()).thenReturn("да");

        boolean result = consoleUserInterface.askUserToAddSurfaces();

        assertTrue(result);
    }

    @Test
    void testAskUserToAddSurfaces_no() throws IOException {
        when(ioHandler.readLine()).thenReturn("нет");

        boolean result = consoleUserInterface.askUserToAddSurfaces();

        assertFalse(result);
    }
}
