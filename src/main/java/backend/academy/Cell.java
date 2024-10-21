package backend.academy;

public class Cell {
    private final int row;
    private final int col;
    private Type type;
    private String displaySymbol;

    public Cell(int row, int col, Type type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        WALL,          // Стена
        PASSAGE,       // Обычный проход
        SAND,          // Песок (ухудшающая поверхность)
        COIN,          // Монетка (улучшающая поверхность)
        PATH,
    }

    public boolean isPassage() {
        return this.type != Type.WALL;
    }
}
