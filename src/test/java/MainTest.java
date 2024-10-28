import backend.academy.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private File inputFile;
    private File outputFile;
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    void setUp() throws IOException {
        // Arrange
        inputFile = File.createTempFile("input", ".txt");
        outputFile = File.createTempFile("output", ".txt");

        // Сохраняем оригинальные потоки
        originalOut = System.out;
        originalIn = System.in;

        try (PrintWriter writer = new PrintWriter(new FileWriter(inputFile))) {
            writer.println("1");  // Выбор маленького лабиринта
            writer.println("1,1"); // Координаты старта
            writer.println("15,29"); // Координаты конца
            writer.println("1"); // Выбор генератора Краскала
            writer.println("нет"); // Не добавлять поверхности
            writer.println("1"); // Использовать A* для поиска пути
        }
    }

    @AfterEach
    void tearDown() {
        // Восстанавливаем оригинальные потоки и удаляем временные файлы
        System.setOut(originalOut);
        System.setIn(originalIn);

        inputFile.delete();
        outputFile.delete();
    }

    @Test
    void testMain() throws IOException {
        // Arrange
        InputStream testInput = new FileInputStream(inputFile);
        PrintStream testOutput = new PrintStream(new FileOutputStream(outputFile));
        System.setIn(testInput);
        System.setOut(testOutput);

        // Act
        Main.main(new String[]{});

        // Assert
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            // Проверка, что в выводе присутствуют ожидаемые фразы
            String outputStr = output.toString();
            assertTrue(outputStr.contains("Сгенерированный лабиринт:"), "Expected maze generation output missing.");
            assertTrue(outputStr.contains("Найденный путь:"), "Expected pathfinding output missing.");
        }
    }
}
