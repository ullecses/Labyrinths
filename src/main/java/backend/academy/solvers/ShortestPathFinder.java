package backend.academy.solvers;

import backend.academy.Cell;
import backend.academy.Coordinate;
import backend.academy.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ShortestPathFinder implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        if (maze.getGrid()[end.getRow()][end.getCol()].getType() == Cell.Type.WALL) {
            return Collections.emptyList(); // путь невозможен
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

            // Если мы достигли конца, останавливаемся
            if (current.equals(end)) {
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
