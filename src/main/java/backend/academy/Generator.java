package backend.academy;

public interface Generator {
    Maze generate(int height, int width, Coordinate start, Coordinate end);
}
