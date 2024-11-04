package backend.academy.generators;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.Random;

public class GeneratorUtils {
    private static final int FORPATH = 10;

    private GeneratorUtils() {
        // Приватный конструктор для предотвращения создания экземпляров этого класса
    }

    public static void addExtraPaths(Maze maze) {
        Random random = new Random();
        int height = maze.getHeight();
        int width = maze.getWidth();

        // Устанавливаем количество дополнительных путей на основе размера лабиринта
        int extraPathCount = (height * width) / FORPATH; // Один дополнительный путь на каждые 10 клеток

        for (int i = 0; i < extraPathCount; i++) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);

            // Проверяем, что это стена и не находится на границе
            if (!(maze.isPassage(row, col)) && row > 0 && row < height - 1 && col > 0 && col < width - 1) {
                // Пробиваем стену, превращая её в проход
                maze.setCellType(new Coordinate(row, col), Cell.Type.PASSAGE);
            }
        }
    }
}
