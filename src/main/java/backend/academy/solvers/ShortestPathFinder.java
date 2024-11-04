package backend.academy.solvers;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ShortestPathFinder implements Solver {

    private boolean isPathFound; // Флаг для обозначения, найден ли путь

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        isPathFound = false; // Изначально считаем, что путь не найден

        if (maze.getGrid()[end.getRow()][end.getCol()].getType() == Cell.Type.WALL) {
            return Collections.emptyList(); // Путь невозможен, так как конечная точка - стена
        }

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

            // Если мы достигли конца, отмечаем, что путь найден и останавливаемся
            if (current.equals(end)) {
                isPathFound = true; // Устанавливаем флаг, если конечная точка достигнута
                break;
            }

            // Проходим по всем соседям
            for (Coordinate neighbor : SolverUtils.getNeighbors(maze, current)) {
                int newDistance = currentDistance + getCellCost(maze, neighbor);

                if (newDistance < distances[neighbor.row()][neighbor.col()]) {
                    distances[neighbor.row()][neighbor.col()] = newDistance;
                    queue.add(neighbor);
                    predecessors.put(neighbor, current); // Запоминаем предка для пути
                }
            }
        }

        // Если путь не найден, возвращаем пустой список
        if (!isPathFound) {
            return Collections.emptyList();
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

    @Override
    public boolean isPathFound() {
        return isPathFound;
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
