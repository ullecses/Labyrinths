import backend.academy.Coordinate;
import backend.academy.IOHandler;
import backend.academy.Maze;
import backend.academy.MazeApplication;
import backend.academy.ConsoleUserInterface;
import backend.academy.generators.Generator;
import backend.academy.generators.KruskalMazeGenerator;
import backend.academy.solvers.AStarSolver;
import backend.academy.solvers.Solver;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MazeApplicationTest {
    private IOHandler ioHandler;
    private ConsoleUserInterface consoleUserInterface;
    private MazeApplication mazeApplication;
    private Solver solver;

    @BeforeEach
    void setUp() {
        ioHandler = mock(IOHandler.class);
        consoleUserInterface = mock(ConsoleUserInterface.class);
        mazeApplication = new MazeApplication(ioHandler, consoleUserInterface);
        solver = mock(AStarSolver.class);
    }

    @Test
    void testRun() throws IOException {
        // Arrange
        Maze maze = new Maze(5, 5);
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 3);
        Generator generator = new KruskalMazeGenerator();

        // Настройка поведения моков
        when(consoleUserInterface.chooseMazeSize()).thenReturn(maze);
        when(consoleUserInterface.getCoordinate(anyString(), eq(maze))).thenReturn(start, end);
        when(consoleUserInterface.chooseMazeGenerator()).thenReturn(generator);
        when(consoleUserInterface.askUserToAddSurfaces()).thenReturn(false);
        when(consoleUserInterface.chooseSolver()).thenReturn(this.solver);
        when(this.solver.solve(eq(maze), eq(start), eq(end))).thenReturn(new ArrayList<>());

        // Act
        mazeApplication.run();

        // Assert
        verify(ioHandler, times(1)).writeLine("Сгенерированный лабиринт:");
        verify(ioHandler, times(1)).writeLine("Найденный путь:");
        verify(consoleUserInterface, times(2)).displayMaze(maze);
    }

    @Test
    void testRun_withSameStartAndEnd() throws IOException {
        // Arrange
        Maze maze = new Maze(5, 5);
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(1, 1); // одинаковые координаты
        Coordinate secondEnd = new Coordinate(4, 4);
        Generator generator = new KruskalMazeGenerator();

        // Настройка поведения моков
        when(consoleUserInterface.chooseMazeSize()).thenReturn(maze);
        when(consoleUserInterface.getCoordinate(anyString(), eq(maze)))
            .thenReturn(start, end, start, secondEnd); // повторный ввод
        when(consoleUserInterface.chooseMazeGenerator()).thenReturn(generator);
        when(consoleUserInterface.askUserToAddSurfaces()).thenReturn(false);
        when(consoleUserInterface.chooseSolver()).thenReturn(this.solver);
        when(this.solver.solve(eq(maze), eq(start), eq(end))).thenReturn(new ArrayList<>());

        // Act
        mazeApplication.run();

        // Assert
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(ioHandler, times(3)).writeLine(messageCaptor.capture());
        verify(consoleUserInterface, times(4)).getCoordinate(anyString(), eq(maze)); // Проверяем, что было два запроса на ввод координат
    }

    @Test
    void testRun_withSurfacesAdded() throws IOException {
        // Arrange
        Maze maze = mock(Maze.class);
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 3);
        Generator generator = new KruskalMazeGenerator();

        // Настройка поведения моков
        when(consoleUserInterface.chooseMazeSize()).thenReturn(maze);
        when(consoleUserInterface.getCoordinate(anyString(), eq(maze))).thenReturn(start, end);
        when(consoleUserInterface.chooseMazeGenerator()).thenReturn(generator);
        when(consoleUserInterface.askUserToAddSurfaces()).thenReturn(true); // Добавление поверхностей
        when(consoleUserInterface.chooseSolver()).thenReturn(this.solver);
        when(this.solver.solve(eq(maze), eq(start), eq(end))).thenReturn(new ArrayList<>());

        // Act
        mazeApplication.run();

        // Assert
        verify(maze, times(1)).addSurfaces();
    }
}
