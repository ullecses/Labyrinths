package backend.academy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        PrintStream printStream = System.out;

        IOHandler ioHandler = new IOHandler(inputStream, printStream);
        ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface(ioHandler);
        MazeApplication app = new MazeApplication(ioHandler, consoleUserInterface);

        app.run();
    }
}
