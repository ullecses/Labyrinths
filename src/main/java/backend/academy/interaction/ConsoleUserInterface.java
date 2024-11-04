package backend.academy.interaction;

import backend.academy.generators.Generator;
import backend.academy.generators.GrowingTreeMazeGenerator;
import backend.academy.generators.KruskalMazeGenerator;
import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.solvers.AStarSolver;
import backend.academy.solvers.ShortestPathFinder;
import backend.academy.solvers.Solver;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConsoleUserInterface implements UserInterface {
    private static final String INPUT_NULL = "Вы не ввели значение. Пожалуйста, попробуйте снова.";
    private static final String INPUT_INCORRECT = "Неверный выбор. Повторите.";
    private static final String INPUT_NEED_1_OR_2 = "Неверный ввод. Пожалуйста, введите число от 1 до 2.";
    private static final int MINPARAMETER = 5;
    private static final int MAXPARAMETER = 101;

    private final IOHandler ioHandler;

    public ConsoleUserInterface(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    public Maze chooseMazeSize() throws IOException {
        ioHandler.writeLine("Введите размер лабиринта (например, 18*20). Размеры должны быть от 3 до 100.");

        while (true) {
            String input = ioHandler.readLine();

            if (input == null || input.trim().isEmpty()) {
                ioHandler.writeLine(INPUT_NULL);
                continue;
            }

            String[] parts = input.split("\\*");
            if (parts.length != 2) {
                ioHandler.writeLine(INPUT_INCORRECT);
                continue;
            }

            try {
                int height = Integer.parseInt(parts[0].trim());
                int width = Integer.parseInt(parts[1].trim());

                // Проверка на допустимые размеры
                if (height < MINPARAMETER || height > MAXPARAMETER || width < MINPARAMETER || width > MAXPARAMETER) {
                    ioHandler.writeLine("Размеры должны быть от 3 до 100. Повторите ввод.");
                    continue;
                }

                Maze maze = new Maze(height, width);

                return maze;
            } catch (NumberFormatException e) {
                ioHandler.writeLine(INPUT_INCORRECT);
            }
        }
    }

    public Generator chooseMazeGenerator() throws IOException {
        Map<Integer, Generator> generators = new HashMap<>();

        generators.put(1, new KruskalMazeGenerator());
        generators.put(2, new GrowingTreeMazeGenerator());

        ioHandler.writeLine("Выберите способ генерации лабиринта:");
        generators.forEach((key, value) -> ioHandler.writeLine(key + " " + value.getClass().getSimpleName()));

        while (true) {
            String input = ioHandler.readLine();

            if (input == null || input.trim().isEmpty()) {
                ioHandler.writeLine(INPUT_NULL);
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                ioHandler.writeLine(INPUT_NEED_1_OR_2);
                continue;
            }

            Generator generator = generators.get(choice);
            if (generator != null) {
                return generator;
            } else {
                ioHandler.writeLine(INPUT_INCORRECT);
            }
        }
    }

    public Solver chooseSolver() throws IOException {
        Map<Integer, Solver> solvers = new HashMap<>();

        solvers.put(1, new ShortestPathFinder());
        solvers.put(2, new AStarSolver());

        ioHandler.writeLine("Выберите способ поиска пути:");
        solvers.forEach((key, value) -> ioHandler.writeLine(key + " " + value.getClass().getSimpleName()));

        while (true) {
            String input = ioHandler.readLine();

            if (input == null || input.trim().isEmpty()) {
                ioHandler.writeLine(INPUT_NULL);
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                ioHandler.writeLine(INPUT_NEED_1_OR_2);
                continue;
            }

            Solver solver = solvers.get(choice);
            if (solver != null) {
                return solver;
            } else {
                ioHandler.writeLine(INPUT_INCORRECT);
            }
        }
    }

    public Coordinate getCoordinate(String prompt, Maze maze) throws IOException {
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
            ioHandler.writeLine("Неверный ввод. Попробуйте еще раз.");
            return getCoordinate(prompt, maze);
        }
    }

    public boolean askUserToAddSurfaces() throws IOException {
        while (true) {
            ioHandler.writeLine("Вы хотите добавить поверхности в лабиринт? (да/нет): ");
            String response = ioHandler.readLine().trim().toLowerCase();

            if (response.equals("да")) {
                return true;
            } else if (response.equals("нет")) {
                return false;
            } else {
                ioHandler.writeLine("Неверный ввод. Пожалуйста, введите 'да' или 'нет'.");
            }
        }
    }

    public void displayMaze(Maze maze) {
        Cell[][] grid = maze.getCellArray();
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                ioHandler.write(grid[row][col].getSymbol());
            }
            ioHandler.writeLine("");
        }
    }
}
