package backend.academy;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class IOHandler {
    private final BufferedReader reader;
    private final PrintStream writer;

    public IOHandler(InputStream inputStream, PrintStream outputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.writer = outputStream;
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void write(String message) {
        writer.print(message);
    }

    public void writeLine(String message) {
        writer.println(message);
    }
}
