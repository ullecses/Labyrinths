package backend.academy.maze;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    private static final double CONST_FOR_SAND = 0.08;
    private static final double CONST_FOR_COIN = 0.07;
    private static final int OFFSET = 2;
    private final int width;
    private final int height;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];

        // Инициализируем все ячейки стенами
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(Cell.Type.WALL);
            }
        }
    }

    public boolean isPassage(int row, int col) {
        return grid[row][col].isPassage();
    }

    public Cell.Type getType(Coordinate coord) {
        return grid[coord.row()][coord.col()].getType();
    }

    public Cell[][] getCellArray() {
        return grid;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    // Метод для задания типа ячейки
    public void setCellType(Coordinate coord, Cell.Type type) {
        grid[coord.row()][coord.col()].setType(type);
    }

    public void addSurfaces() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col].getType() == Cell.Type.PASSAGE) {
                    // Назначение типа поверхности
                    assignSurface(row, col);
                }
            }
        }
    }

    //Метод для генерации дополнительных поверхностей
    private void assignSurface(int row, int col) {
        if (Math.random() < CONST_FOR_SAND) {
            grid[row][col].setType(Cell.Type.SAND);
        } else if (Math.random() < CONST_FOR_COIN) {
            grid[row][col].setType(Cell.Type.COIN);
        }
    }

    // Метод для получения непосещённых соседей
    public List<Coordinate> getUnvisitedNeighbors(Coordinate coord) {
        List<Coordinate> neighbors = new ArrayList<>();

        // Массив с направлениями движения (вверх, вниз, влево, вправо)
        int[][] directions = {
            {-OFFSET, 0}, // вверх на 2 клетки
            {OFFSET, 0},  // вниз на 2 клетки
            {0, -OFFSET}, // влево на 2 клетки
            {0, OFFSET}   // вправо на 2 клетки
        };

        // Проходим по каждому направлению
        for (int[] direction : directions) {
            int newRow = coord.row() + direction[0];
            int newCol = coord.col() + direction[1];

            // Проверяем, что новая координата находится внутри лабиринта
            if (isInBounds(newRow, newCol)) {
                Coordinate neighbor = new Coordinate(newRow, newCol);
                // Если соседняя ячейка — это стена, добавляем её в список
                if (grid[newRow][newCol].getType() == Cell.Type.WALL) {
                    neighbors.add(neighbor);
                }
            }
        }

        return neighbors;
    }

    public void markPath(List<Coordinate> path) {
        for (Coordinate coord : path) {
            grid[coord.row()][coord.col()].setType(Cell.Type.PATH); // Закрашиваем клетки пути
        }
    }

    // Метод для проверки, что координата находится в пределах лабиринта
    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    public Maze crop(int targetHeight, int targetWidth) {
        Cell[][] originalCells = this.getCellArray();
        Cell[][] croppedCells = new Cell[targetHeight][targetWidth];

        for (int row = 0; row < targetHeight; row++) {
            for (int col = 0; col < targetWidth; col++) {
                croppedCells[row][col] = originalCells[row][col];
            }
        }

        return new Maze(targetHeight, targetWidth);
    }
}
