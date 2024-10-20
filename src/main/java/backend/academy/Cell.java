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

    public String getDisplaySymbol() {
        switch (type) {
            case WALL:
                displaySymbol = "⬜";  // Символ для стены
                break;
            case PASSAGE:
                displaySymbol = "⬛️";  // Символ для прохода
                break;
            case COIN:
                displaySymbol = "\uD83D\uDFE1";  // Символ для монетки (учучшающая поверхность)
                break;
            case SAND:
                displaySymbol = "\uD83D\uDFEB";  // Символ для песка (ухудшающая поверхность)
                break;
            case PATH:
                displaySymbol = "\uD83D\uDD33";  // Символ для песка (ухудшающая поверхность)
                break;
            default:
                throw new IllegalArgumentException("Unknown cell type: " + type);
        }
        return displaySymbol;
    }
}
