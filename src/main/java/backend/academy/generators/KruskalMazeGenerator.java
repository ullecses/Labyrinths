package backend.academy.generators;

import backend.academy.Cell;
import backend.academy.Coordinate;
import backend.academy.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMazeGenerator implements Generator {

    // Метод для генерации лабиринта с помощью алгоритма Краскала
    @Override
    public Maze generate(Maze maze, Coordinate start, Coordinate end) {
        int height = maze.getHeight();
        int width = maze.getWidth();

        List<Edge> edges = new ArrayList<>();
        DisjointSet disjointSet = new DisjointSet(width * height);

        // Генерируем рёбра для внутренних клеток и границ лабиринта
        for (int row = 0; row < height; row += 2) {  // Включаем 0, чтобы захватить верхний ряд
            for (int col = 0; col < width; col += 2) {  // Включаем 0, чтобы захватить левый ряд
                Coordinate current = new Coordinate(row, col);

                // Добавляем горизонтальные рёбра
                if (col + 2 < width) {
                    edges.add(new Edge(current, new Coordinate(row, col + 2)));
                }
                // Добавляем вертикальные рёбра
                if (row + 2 < height) {
                    edges.add(new Edge(current, new Coordinate(row + 2, col)));
                }
            }
        }

        // Делаем все клетки стенами
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                maze.setCellType(new Coordinate(row, col), Cell.Type.WALL);
            }
        }

        // Перемешиваем рёбра для случайного порядка
        Collections.shuffle(edges);

        // Проходим по рёбрам и соединяем клетки, если они находятся в разных множествах
        for (Edge edge : edges) {
            Coordinate u = edge.u();
            Coordinate v = edge.v();

            int uIndex = coordinateToIndex(u, width);
            int vIndex = coordinateToIndex(v, width);

            // Если клетки находятся в разных множествах, соединяем их
            if (disjointSet.find(uIndex) != disjointSet.find(vIndex)) {
                disjointSet.union(uIndex, vIndex);

                // Убираем стену между клетками и делаем их проходами
                maze.setCellType(u, Cell.Type.PASSAGE);
                maze.setCellType(v, Cell.Type.PASSAGE);

                // Убираем стену между ними (выбираем среднюю клетку)
                Coordinate between = new Coordinate((u.row() + v.row()) / 2, (u.col() + v.col()) / 2);
                maze.setCellType(between, Cell.Type.PASSAGE);
            }
        }

        // Делаем старт и конец проходами
        maze.setCellType(start, Cell.Type.PASSAGE);
        maze.setCellType(end, Cell.Type.PASSAGE);

        return maze;
    }

    // Преобразует координаты клетки в индекс для использования в DisjointSet
    private int coordinateToIndex(Coordinate coord, int width) {
        return coord.row() * width + coord.col();
    }

    // Класс для хранения рёбер лабиринта
    private record Edge(Coordinate u, Coordinate v) {
    }

    // Класс для реализации структуры данных Disjoint Set (Union-Find)
    private static class DisjointSet {
        private final int[] parent;
        private final int[] rank;

        DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        // Поиск с сжатием пути
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        // Объединение двух множеств
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }
}
