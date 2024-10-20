package backend.academy;

import java.util.*;

public class ShortestPathFinder implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        int[][] distances = new int[maze.getHeight()][maze.getWidth()];
        for (int[] row : distances) {
            Arrays.fill(row, Integer.MAX_VALUE); // Инициализируем все расстояния как бесконечные
        }
        distances[start.row()][start.col()] = 0;

        // Очередь для BFS (поиск в ширину)
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);

        // Карта для хранения предков
        Map<Coordinate, Coordinate> predecessors = new HashMap<>();
        predecessors.put(start, null);

        // Поиск кратчайших расстояний
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            int currentDistance = distances[current.row()][current.col()];

            // Если мы достигли конца, останавливаемся
            if (current.equals(end)) {
                break;
            }

            // Проходим по всем соседям
            for (Coordinate neighbor : getNeighbors(maze, current)) {
                int newDistance = currentDistance + getCellCost(maze, neighbor);

                if (newDistance < distances[neighbor.row()][neighbor.col()]) {
                    distances[neighbor.row()][neighbor.col()] = newDistance;
                    queue.add(neighbor);
                    predecessors.put(neighbor, current); // Запоминаем предка для пути
                }
            }
        }

        // Собираем кратчайший путь, начиная с конца
        List<Coordinate> path = new ArrayList<>();
        Coordinate step = end;

        while (step != null) {
            path.add(step);
            step = predecessors.get(step); // Идем по предкам
        }

        Collections.reverse(path); // Разворачиваем путь, так как мы шли от конца

        return path; // Возвращаем список клеток, образующих кратчайший путь
    }

    private List<Coordinate> getNeighbors(Maze maze, Coordinate coord) {
        List<Coordinate> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Вверх, вниз, влево, вправо

        for (int[] direction : directions) {
            int newRow = coord.row() + direction[0];
            int newCol = coord.col() + direction[1];

            if (maze.isInBounds(newRow, newCol) && maze.isPassage(newRow, newCol)) {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }

        return neighbors;
    }

    private int getCellCost(Maze maze, Coordinate coord) {
        Cell.Type type = maze.getType(coord);
        switch (type) {
            case SAND:
                return 2; // Стоимость прохода по песку
            case COIN:
                return 0; // Стоимость прохода по монетке
            default:
                return 1; // Обычная клетка или проход
        }
    }
}
