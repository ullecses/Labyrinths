package backend.academy.interaction;

import backend.academy.generators.Generator;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.solvers.Solver;
import java.io.IOException;
import java.util.List;

public class MazeApplication {
    private final ConsoleUserInterface consoleUserInterface;
    private final IOHandler ioHandler;

    public MazeApplication(IOHandler ioHandler, ConsoleUserInterface consoleUserInterface) {
        this.ioHandler = ioHandler;
        this.consoleUserInterface = consoleUserInterface;
    }

    public void run() throws IOException {
        // Шаг 1: Выбор размера лабиринта
        Maze maze = consoleUserInterface.chooseMazeSize();

        // Шаг 2: Ввод координат
        Coordinate start;
        Coordinate end;

        do {
            start = consoleUserInterface.getCoordinate("Введите координаты начала (формат: row,col): ", maze);
            end = consoleUserInterface.getCoordinate("Введите координаты конца (формат: row,col): ", maze);

            if (start.equals(end)) {
                ioHandler.writeLine("Начальная и конечная точки не должны совпадать. Повторите ввод.");
            }
        } while (start.equals(end));

        // Шаг 3: Выбор генератора лабиринта
        Generator generator = consoleUserInterface.chooseMazeGenerator();
        generator.generate(maze, start, end);

        // Добавление поверхностей в лабиринт
        boolean addSurfaces = consoleUserInterface.askUserToAddSurfaces();
        if (addSurfaces) {
            maze.addSurfaces();
        }

        // Показать лабиринт
        ioHandler.writeLine("Сгенерированный лабиринт:");
        consoleUserInterface.displayMaze(maze);

        // Шаг 4: Выбор алгоритма поиска пути
        Solver solver = consoleUserInterface.chooseSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        // Показать результат
        if (solver.isPathFound()) {
            ioHandler.writeLine("Найденный путь:");
            maze.markPath(path);
            consoleUserInterface.displayMaze(maze);
        } else {
            ioHandler.writeLine("Пути нет");
        }
    }
}
