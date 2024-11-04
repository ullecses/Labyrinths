package backend.academy.solvers;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarSolver implements Solver {
    private boolean isPathFound = false; // флаг для проверки, найден ли путь

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        isPathFound = false;

        if (maze.getType(start) == Cell.Type.WALL || maze.getType(end) == Cell.Type.WALL) {
            return Collections.emptyList();
        }

        int[][] distances = new int[maze.getHeight()][maze.getWidth()];
        for (int[] row : distances) {
            Arrays.fill(row, Integer.MAX_VALUE); // Инициализируем все расстояния как бесконечные
        }
        distances[start.row()][start.col()] = 0;

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        openSet.add(new Node(start, 0, heuristic(start, end)));

        Map<Coordinate, Coordinate> predecessors = new HashMap<>();
        predecessors.put(start, null);

        Map<Coordinate, Integer> gScore = new HashMap<>();
        gScore.put(start, 0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Coordinate currentCoord = current.coord;

            if (currentCoord.equals(end)) {
                isPathFound = true; // Устанавливаем флаг, если конец достигнут
                break;
            }

            for (Coordinate neighbor : SolverUtils.getNeighbors(maze, currentCoord)) {
                int tentativeGScore = gScore.get(currentCoord) + getCellCost(maze, neighbor);

                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    predecessors.put(neighbor, currentCoord);
                    gScore.put(neighbor, tentativeGScore);

                    int fScore = tentativeGScore + heuristic(neighbor, end);
                    openSet.add(new Node(neighbor, tentativeGScore, fScore));
                }
            }
        }

        List<Coordinate> path = new ArrayList<>();
        if (isPathFound) { // Если путь найден, восстанавливаем его
            Coordinate step = end;
            while (step != null) {
                path.add(step);
                step = predecessors.get(step);
            }
            Collections.reverse(path);
        }

        return path;
    }

    @Override
    public boolean isPathFound() {
        return isPathFound;
    }

    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    private int getCellCost(Maze maze, Coordinate coord) {
        Cell.Type type = maze.getType(coord);
        switch (type) {
            case SAND:
                return 2;
            case COIN:
                return 0;
            default:
                return 1;
        }
    }

    private static class Node {
        Coordinate coord;
        int g;
        int f;

        Node(Coordinate coord, int g, int f) {
            this.coord = coord;
            this.g = g;
            this.f = f;
        }
    }
}
