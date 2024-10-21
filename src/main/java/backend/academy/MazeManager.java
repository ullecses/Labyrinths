package backend.academy;

import backend.academy.generators.Generator;
import backend.academy.generators.GrowingTreeMazeGenerator;
import backend.academy.generators.KruskalMazeGenerator;
import backend.academy.solvers.AStarSolver;
import backend.academy.solvers.ShortestPathFinder;
import backend.academy.solvers.Solver;
import java.io.IOException;

public class MazeManager {
    public static final Coordinate SMALL_MAZE = new Coordinate(17, 31);
    public static final Coordinate MEDIUM_MAZE = new Coordinate(25, 45);
    public static final Coordinate LARGE_MAZE = new Coordinate(35, 65);
    private static final int SMALL_MAZE_ID = 1;
    private static final int MEDIUM_MAZE_ID = 2;
    private static final int LARGE_MAZE_ID = 3;
    private static final String INPUT_NULL = "Вы не ввели значение. Пожалуйста, попробуйте снова.";
    private static final String INPUT_INCORRECT = "Неверный выбор. Повторите.";
    private static final String INPUT_NEED_1_OR_2 = "Неверный ввод. Пожалуйста, введите число от 1 до 2.";

    private final IOHandler ioHandler;

    public MazeManager(IOHandler ioHandler) {
        this.ioHandler = ioHandler;
    }

    public Maze chooseMazeSize() throws IOException {
        String options = String.join("\n",
            "Выберите размер лабиринта:",
            SMALL_MAZE_ID + " - Маленький (17*31)",
            MEDIUM_MAZE_ID + " - Средний (25x45)",
            LARGE_MAZE_ID + " - Большой (35x65)"
        );

        ioHandler.writeLine(options);

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
                ioHandler.writeLine(INPUT_INCORRECT);
                continue;
            }

            switch (choice) {
                case SMALL_MAZE_ID:
                    return new Maze(SMALL_MAZE.getRow(), SMALL_MAZE.getCol());
                case MEDIUM_MAZE_ID:
                    return new Maze(MEDIUM_MAZE.getRow(), MEDIUM_MAZE.getCol());
                case LARGE_MAZE_ID:
                    return new Maze(LARGE_MAZE.getRow(), LARGE_MAZE.getCol());
                default:
                    ioHandler.writeLine(INPUT_INCORRECT);
            }
        }
    }

    public Generator chooseMazeGenerator() throws IOException {
        ioHandler.writeLine("Выберите способ генерации лабиринта:");
        ioHandler.writeLine("1 - KruskalMazeGenerator");
        ioHandler.writeLine("2 - GrowingTreeMazeGenerator");

        while (true) {
            String input = ioHandler.readLine();

            if (input == null || input.trim().isEmpty()) {
                ioHandler.writeLine(INPUT_NULL);
                continue;  // Продолжить цикл для нового ввода
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                ioHandler.writeLine(INPUT_NEED_1_OR_2);
                continue;
            }

            switch (choice) {
                case 1:
                    return new KruskalMazeGenerator();
                case 2:
                    return new GrowingTreeMazeGenerator();
                default:
                    ioHandler.writeLine(INPUT_INCORRECT);
            }
        }
    }

    public Solver chooseSolver() throws IOException {
        ioHandler.writeLine("Выберите способ поиска пути:");
        ioHandler.writeLine("1 - ShortestPathFinder");
        ioHandler.writeLine("2 - AStarSolver");

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

            switch (choice) {
                case 1:
                    return new ShortestPathFinder();
                case 2:
                    return new AStarSolver();
                default:
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
        // Визуализация лабиринта
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                ioHandler.write(getDisplaySymbol(grid[row][col]));
            }
            ioHandler.writeLine("");
        }
    }

    public String getDisplaySymbol(Cell cell) {
        String displaySymbol;
        switch (cell.getType()) {
            case WALL:
                displaySymbol = "⬜";  // Символ для стены
                break;
            case PASSAGE:
                displaySymbol = "⬛️";  // Символ для прохода
                break;
            case COIN:
                displaySymbol = "\uD83D\uDFE1";  // Символ для монетки (учучшающая поверхность)
                break;
            case SAND:
                displaySymbol = "\uD83D\uDFEB";  // Символ для песка (ухудшающая поверхность)
                break;
            case PATH:
                displaySymbol = "\uD83D\uDD33";  // Символ для песка (ухудшающая поверхность)
                break;
            default:
                throw new IllegalArgumentException("Unknown cell type: ");
        }
        return displaySymbol;
    }
}
