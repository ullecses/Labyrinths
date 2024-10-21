package backend.academy.generators;

import backend.academy.Cell;
import backend.academy.Coordinate;
import backend.academy.Maze;
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
        int height = maze.getHeight();
        int width = maze.getWidth();
        List<Coordinate> cells = new ArrayList<>();

        // Добавляем стартовую ячейку
        maze.setCellType(start, Cell.Type.PASSAGE);
        cells.add(start);

        Random random = new Random();

        while (!cells.isEmpty()) {
            Coordinate current = selectCell(cells);
            List<Coordinate> neighbors = maze.getUnvisitedNeighbors(current);

            if (!neighbors.isEmpty()) {
                Coordinate next = neighbors.get(random.nextInt(neighbors.size()));

                // Устанавливаем тип клетки как проход
                maze.setCellType(next, Cell.Type.PASSAGE);

                // Устанавливаем тип клетки между current и next как проход
                Coordinate between = getCellBetween(current, next);
                maze.setCellType(between, Cell.Type.PASSAGE);

                cells.add(next);
            } else {
                cells.remove(current);
            }
        }

        // Устанавливаем конечную точку как проход
        maze.setCellType(end, Cell.Type.PASSAGE);

        return maze;
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
