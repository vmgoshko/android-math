package by.bsu.mg.math.parsing.lexemes;

public class Lexeme {
    private String value;
    private int level;
    private LexemeType type;

    public Lexeme(String value) {
        this.value = value;
        this.level = 0;
    }

    public Lexeme(String value, int level, LexemeType type) {
        this.value = value;
        this.type = type;
        this.level = level;
    }

    public String getValue() {
        return value;
    }

    public int getLevel() {
        return level;
    }


    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return value;
    }

    public void setType(LexemeType type) {
        this.type = type;
    }

    public LexemeType getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPriority() {
        return type.getPriority();
    }
}
