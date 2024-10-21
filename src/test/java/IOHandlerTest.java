import backend.academy.IOHandler;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class IOHandlerTest {

    private ByteArrayOutputStream outputStream;
    private IOHandler ioHandler;

    @BeforeEach
    public void setup() {
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        ioHandler = new IOHandler(new ByteArrayInputStream(new byte[0]), printStream);
    }

    @Test
    public void testWrite() {
        // Arrange
        String message = "Hello, World!";

        // Act
        ioHandler.write(message);

        // Assert
        assertEquals(message, outputStream.toString());
    }

    @Test
    public void testWriteLine() {
        // Arrange
        String message = "Hello, World!";

        // Act
        ioHandler.writeLine(message);

        // Assert
        assertEquals(message + System.lineSeparator(), outputStream.toString());
    }

    @Test
    public void testReadLine() throws IOException {
        // Arrange
        String input = "User input";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ioHandler = new IOHandler(inputStream, new PrintStream(outputStream));

        // Act
        String result = ioHandler.readLine();

        // Assert
        assertEquals(input, result);
    }

    @Test
    public void testReadLine_IOException() throws IOException {
        // Arrange
        BufferedReader readerMock = Mockito.mock(BufferedReader.class);
        when(readerMock.readLine()).thenThrow(new IOException("Read error"));
        IOHandler ioHandler = new IOHandler(readerMock, System.out);  // Передаем мок

        // Act & Assert
        assertThrows(IOException.class, ioHandler::readLine);
    }
}
