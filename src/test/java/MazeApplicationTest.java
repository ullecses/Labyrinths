import backend.academy.Coordinate;
import backend.academy.IOHandler;
import backend.academy.Maze;
import backend.academy.MazeApplication;
import backend.academy.MazeManager;
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
    private MazeManager mazeManager;
    private MazeApplication mazeApplication;
    private Solver solver;

    @BeforeEach
    void setUp() {
        ioHandler = mock(IOHandler.class);
        mazeManager = mock(MazeManager.class);
        mazeApplication = new MazeApplication(ioHandler, mazeManager);
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
        when(mazeManager.chooseMazeSize()).thenReturn(maze);
        when(mazeManager.getCoordinate(anyString(), eq(maze))).thenReturn(start, end);
        when(mazeManager.chooseMazeGenerator()).thenReturn(generator);
        when(mazeManager.askUserToAddSurfaces()).thenReturn(false);
        when(mazeManager.chooseSolver()).thenReturn(this.solver);
        when(this.solver.solve(eq(maze), eq(start), eq(end))).thenReturn(new ArrayList<>());

        // Act
        mazeApplication.run();

        // Assert
        verify(ioHandler, times(1)).writeLine("Сгенерированный лабиринт:");
        verify(ioHandler, times(1)).writeLine("Найденный путь:");
        verify(mazeManager, times(2)).displayMaze(maze);
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
        when(mazeManager.chooseMazeSize()).thenReturn(maze);
        when(mazeManager.getCoordinate(anyString(), eq(maze)))
            .thenReturn(start, end, start, secondEnd); // повторный ввод
        when(mazeManager.chooseMazeGenerator()).thenReturn(generator);
        when(mazeManager.askUserToAddSurfaces()).thenReturn(false);
        when(mazeManager.chooseSolver()).thenReturn(this.solver);
        when(this.solver.solve(eq(maze), eq(start), eq(end))).thenReturn(new ArrayList<>());

        // Act
        mazeApplication.run();

        // Assert
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(ioHandler, times(3)).writeLine(messageCaptor.capture());
        verify(mazeManager, times(4)).getCoordinate(anyString(), eq(maze)); // Проверяем, что было два запроса на ввод координат
    }

    @Test
    void testRun_withSurfacesAdded() throws IOException {
        // Arrange
        Maze maze = mock(Maze.class);
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 3);
        Generator generator = new KruskalMazeGenerator();

        // Настройка поведения моков
        when(mazeManager.chooseMazeSize()).thenReturn(maze);
        when(mazeManager.getCoordinate(anyString(), eq(maze))).thenReturn(start, end);
        when(mazeManager.chooseMazeGenerator()).thenReturn(generator);
        when(mazeManager.askUserToAddSurfaces()).thenReturn(true); // Добавление поверхностей
        when(mazeManager.chooseSolver()).thenReturn(this.solver);
        when(this.solver.solve(eq(maze), eq(start), eq(end))).thenReturn(new ArrayList<>());

        // Act
        mazeApplication.run();

        // Assert
        verify(maze, times(1)).addSurfaces();
    }
}
