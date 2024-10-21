package backend.academy;

import backend.academy.generators.Generator;
import backend.academy.solvers.Solver;
import java.io.IOException;
import java.util.List;

public class MazeApplication {
    private final MazeManager mazeManager;
    private final IOHandler ioHandler;

    public MazeApplication(IOHandler ioHandler, MazeManager mazeManager) {
        this.ioHandler = ioHandler;
        this.mazeManager = mazeManager;
    }

    public void run() throws IOException {
        // Шаг 1: Выбор размера лабиринта
        Maze maze = mazeManager.chooseMazeSize();

        // Шаг 2: Ввод координат
        Coordinate start = mazeManager.getCoordinate("Введите координаты начала (формат: row,col): ", maze);
        Coordinate end = mazeManager.getCoordinate("Введите координаты конца (формат: row,col): ", maze);

        while (start.equals(end)) {
            ioHandler.writeLine("Начальная и конечная точки не должны совпадать. Повторите ввод.");
            start = mazeManager.getCoordinate("Введите координаты начала: ", maze);
            end = mazeManager.getCoordinate("Введите координаты конца: ", maze);
        }

        // Шаг 3: Выбор генератора лабиринта
        Generator generator = mazeManager.chooseMazeGenerator();
        generator.generate(maze, start, end);

        // Добавление поверхностей в лабиринт
        boolean addSurfaces = mazeManager.askUserToAddSurfaces();
        if (addSurfaces) {
            maze.addSurfaces();
        }

        // Показать лабиринт
        ioHandler.writeLine("Сгенерированный лабиринт:");
        mazeManager.displayMaze(maze);

        // Шаг 4: Выбор алгоритма поиска пути
        Solver solver = mazeManager.chooseSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        // Показать результат
        ioHandler.writeLine("Найденный путь:");
        maze.markPath(path);
        mazeManager.displayMaze(maze);
    }
}
