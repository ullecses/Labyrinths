package backend.academy;

import java.util.Scanner;

public class ConsoleIOHandler {
    private final Scanner scanner;

    public ConsoleIOHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public void write(String message) {
        System.out.print(message);
    }

    public void writeLine(String message) {
        System.out.println(message);
    }
}
