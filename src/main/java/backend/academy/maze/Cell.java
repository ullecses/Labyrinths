package backend.academy.maze;

import java.util.Map;

public class Cell {
    private static final Map<Type, String> SYMBOL_MAP = Map.of(
        Cell.Type.WALL, "⬜",
        Cell.Type.PASSAGE, "⬛️",
        Cell.Type.COIN, "\uD83D\uDFE1",
        Cell.Type.SAND, "\uD83D\uDFEB",
        Cell.Type.PATH, "🟪"
    );
    private Type type;

    public Cell(Type type) {
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

    public String getSymbol() {
        return SYMBOL_MAP.getOrDefault(type, "?"); // Везвращаем "?" для неизвестного типа
    }
}
