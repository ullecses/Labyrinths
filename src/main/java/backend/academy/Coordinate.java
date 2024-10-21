package backend.academy;

public record Coordinate(int row, int col) {

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
