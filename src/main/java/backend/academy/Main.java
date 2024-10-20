package backend.academy;

import lombok.experimental.UtilityClass;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLOutput;

@UtilityClass
public class Main {
    public static void main(String[] args) throws IOException {

        /*int width = 65;
        int height = 35;

        // Начальная точка
        Coordinate start = new Coordinate(0, 6);
        Coordinate end = new Coordinate(34, 64);

        // Создаём генератор лабиринта
        //GrowingTreeMazeGenerator generator = new GrowingTreeMazeGenerator();
        KruskalMazeGenerator generator = new KruskalMazeGenerator();

            // Генерируем лабиринт
        Maze maze = generator.generate(height, width, start, end);
        Solver solver1 = new AStarSolver();

        //Maze copy = maze;
        // Ищем кратчайший путь
        maze.markPath(solver1.solve(maze, start, end));

        // Выводим лабиринт на экран
        maze.printMaze();*/
        InputStream inputStream = System.in;
        PrintStream printStream = System.out;

        IOHandler ioHandler = new IOHandler(inputStream, printStream);
        MazeApplication app = new MazeApplication(ioHandler);

        app.run();
    }
}
