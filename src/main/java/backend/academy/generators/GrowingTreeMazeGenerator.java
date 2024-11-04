package backend.academy.generators;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrowingTreeMazeGenerator implements Generator {
    private final SelectionStrategy strategy;

    public GrowingTreeMazeGenerator() {
        this.strategy = SelectionStrategy.LAST;
    }

    @Override
    public Maze generate(Maze maze, Coordinate start, Coordinate end) {

        List<Coordinate> cells = new ArrayList<>();

        maze.setCellType(start, Cell.Type.PASSAGE);
        cells.add(start);

        Random random = new Random();

        while (!cells.isEmpty()) {
            Coordinate current = selectCell(cells);
            List<Coordinate> neighbors = maze.getUnvisitedNeighbors(current);

            if (!neighbors.isEmpty()) {
                Coordinate next = neighbors.get(random.nextInt(neighbors.size()));

                maze.setCellType(next, Cell.Type.PASSAGE);
                Coordinate between = getCellBetween(current, next);
                maze.setCellType(between, Cell.Type.PASSAGE);

                cells.add(next);
            } else {
                cells.remove(current);
            }
        }

        maze.setCellType(end, Cell.Type.PASSAGE);
        GeneratorUtils.addExtraPaths(maze);

        // Специальная пост-обработка для четных размеров
        ensureConnectivityForEvenSizes(maze);

        //GeneratorUtils.addExtraPaths(maze);

        return maze;
    }

    private void ensureConnectivityForEvenSizes(Maze maze) {
        int height = maze.getHeight();
        int width = maze.getWidth();

        // Пройти по последнему столбцу
        for (int row = 0; row < height - 1; row++) {
            if (maze.getType(new Coordinate(row, width - 1)) == Cell.Type.WALL) {
                maze.setCellType(new Coordinate(row, width - 1), Cell.Type.PASSAGE);
            }
        }

        // Пройти по последней строке
        for (int col = 0; col < width - 1; col++) {
            if (maze.getType(new Coordinate(height - 1, col)) == Cell.Type.WALL) {
                maze.setCellType(new Coordinate(height - 1, col), Cell.Type.PASSAGE);
            }
        }
    }

    // Метод для выбора ячейки
    private Coordinate selectCell(List<Coordinate> cells) {
        return strategy == SelectionStrategy.LAST ? cells.get(cells.size() - 1)
            : cells.get(new Random().nextInt(cells.size()));
    }

    // Метод для получения клетки между двумя клетками
    public Coordinate getCellBetween(Coordinate current, Coordinate next) {
        int betweenRow = (current.row() + next.row()) / 2;
        int betweenCol = (current.col() + next.col()) / 2;
        return new Coordinate(betweenRow, betweenCol);
    }

    public enum SelectionStrategy {
        LAST, RANDOM
    }
}
