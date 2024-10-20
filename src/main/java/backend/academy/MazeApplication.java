package backend.academy;

import java.io.IOException;
import java.util.List;

public class MazeApplication {
    private final IOHandler ioHandler;

    public MazeApplication(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    public void run() throws IOException {
        // Шаг 1: Выбор размера лабиринта
        Maze maze = chooseMazeSize();

        // Шаг 2: Ввод координат
        Coordinate start = getCoordinate("Введите координаты начала (формат: row,col): ", maze);
        Coordinate end = getCoordinate("Введите координаты конца (формат: row,col): ", maze);

        while (start.equals(end)) {
            ioHandler.writeLine("Начальная и конечная точки не должны совпадать. Повторите ввод.");
            start = getCoordinate("Введите координаты начала: ", maze);
            end = getCoordinate("Введите координаты конца: ", maze);
        }

        // Шаг 3: Выбор генератора лабиринта
        Generator generator = chooseMazeGenerator();
        generator.generate(maze.getHeight(), maze.getWidth(), start, end);
        maze.addSurfaces();

        // Показать лабиринт
        ioHandler.writeLine("Сгенерированный лабиринт:");
        displayMaze(maze);

        // Шаг 4: Выбор алгоритма поиска пути
        Solver solver = chooseSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        // Показать результат
        ioHandler.writeLine("Найденный путь:");
        maze.markPath(path);
        displayMaze(maze);
    }

    private Maze chooseMazeSize() throws IOException {
        ioHandler.writeLine("Выберите размер лабиринта:");
        ioHandler.writeLine("1 - Маленький (17*31)");
        ioHandler.writeLine("2 - Средний (25x45)");
        ioHandler.writeLine("3 - Большой (35x65)");

        int choice = Integer.parseInt(ioHandler.readLine());

        switch (choice) {
            case 1:
                return new Maze(17, 31);
            case 2:
                return new Maze(25, 45);
            case 3:
                return new Maze(35, 65);
            default:
                ioHandler.writeLine("Неверный выбор. Повторите.");
                return chooseMazeSize();
        }
    }

    private Coordinate getCoordinate(String prompt, Maze maze) throws IOException {
        ioHandler.write(prompt);
        String input = ioHandler.readLine();

        try {
            String[] parts = input.split(",");
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;

            if (!maze.isInBounds(row, col)) {
                ioHandler.writeLine("Координаты за пределами лабиринта. Повторите ввод.");
                return getCoordinate(prompt, maze);
            }

            return new Coordinate(row, col);
        } catch (Exception e) {
            ioHandler.writeLine("Неверный формат ввода. Попробуйте снова.");
            return getCoordinate(prompt, maze);
        }
    }

    private Generator chooseMazeGenerator() throws IOException {
        ioHandler.writeLine("Выберите способ генерации лабиринта:");
        ioHandler.writeLine("1 - KruskalMazeGenerator");
        ioHandler.writeLine("2 - GrowingTreeMazeGenerator");

        int choice = Integer.parseInt(ioHandler.readLine());

        switch (choice) {
            case 1:
                return new KruskalMazeGenerator();
            case 2:
                return new GrowingTreeMazeGenerator();
            default:
                ioHandler.writeLine("Неверный выбор. Повторите.");
                return chooseMazeGenerator();
        }
    }

    private Solver chooseSolver() throws IOException {
        ioHandler.writeLine("Выберите способ поиска пути:");
        ioHandler.writeLine("1 - ShortestPathFinder");
        ioHandler.writeLine("2 - AStarSolver");

        int choice = Integer.parseInt(ioHandler.readLine());

        switch (choice) {
            case 1:
                return new ShortestPathFinder();
            case 2:
                return new AStarSolver();
            default:
                ioHandler.writeLine("Неверный выбор. Повторите.");
                return chooseSolver();
        }
    }

    private void displayMaze(Maze maze) {
        Cell[][] grid = maze.getCellArray();
        // Пример визуализации лабиринта
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                ioHandler.write(grid[row][col].getDisplaySymbol());
            }
            ioHandler.writeLine("");
        }
    }
}

