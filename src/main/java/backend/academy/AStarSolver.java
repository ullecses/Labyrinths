package backend.academy;

import java.util.*;

public class AStarSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        int[][] distances = new int[maze.getHeight()][maze.getWidth()];
        for (int[] row : distances) {
            Arrays.fill(row, Integer.MAX_VALUE); // Инициализируем все расстояния как бесконечные
        }
        distances[start.row()][start.col()] = 0;

        // Приоритетная очередь для поиска, основанная на стоимости пути f(x) = g(x) + h(x)
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        openSet.add(new Node(start, 0, heuristic(start, end)));

        // Карта для хранения предков
        Map<Coordinate, Coordinate> predecessors = new HashMap<>();
        predecessors.put(start, null);

        // Карта для отслеживания кратчайших расстояний от старта до текущей клетки (g(x))
        Map<Coordinate, Integer> gScore = new HashMap<>();
        gScore.put(start, 0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Coordinate currentCoord = current.coord;

            // Если мы достигли конца, можно завершить
            if (currentCoord.equals(end)) {
                break;
            }

            // Обработка всех соседей
            for (Coordinate neighbor : getNeighbors(maze, currentCoord)) {
                int tentativeGScore = gScore.get(currentCoord) + getCellCost(maze, neighbor);

                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    // Обновляем путь к соседу, если найден более короткий
                    predecessors.put(neighbor, currentCoord);
                    gScore.put(neighbor, tentativeGScore);

                    // Добавляем соседа в очередь с новой стоимостью f(x)
                    int fScore = tentativeGScore + heuristic(neighbor, end);
                    openSet.add(new Node(neighbor, tentativeGScore, fScore));
                }
            }
        }

        // Восстанавливаем путь, начиная с конечной клетки
        List<Coordinate> path = new ArrayList<>();
        Coordinate step = end;

        while (step != null) {
            path.add(step);
            step = predecessors.get(step); // Идем по предкам
        }

        Collections.reverse(path); // Разворачиваем путь, так как он был восстановлен от конца

        return path;
    }

    // Метод для получения соседей клетки
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

    // Метод для расчета эвристики (Manhattan distance)
    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    // Метод для получения стоимости клетки
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

    // Вспомогательный класс для хранения координаты, стоимости пути и эвристической оценки
    private static class Node {
        Coordinate coord;
        int g; // Текущая стоимость пути g(x)
        int f; // Общая стоимость f(x) = g(x) + h(x)

        public Node(Coordinate coord, int g, int f) {
            this.coord = coord;
            this.g = g;
            this.f = f;
        }
    }
}

