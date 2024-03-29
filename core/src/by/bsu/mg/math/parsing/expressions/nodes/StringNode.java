package by.bsu.mg.math.parsing.expressions.nodes;

import by.bsu.mg.math.parsing.lexemes.Lexeme;

/**
 * Vladimir Goshko vmgoshko@gmail.com
 */
public class StringNode extends Node {
    private String value;

    public StringNode() {
    }

    public StringNode(Lexeme lexeme) {
        value = lexeme.getValue();
        setLevel(lexeme.getLevel());
        setType(lexeme.getType());
    }

    @Override
    public void setValue(Lexeme value) {
        this.value = value.getValue();
        super.setType(value.getType());
        super.setLevel(value.getLevel());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String string) {
        value = string;
    }

    @Override
    public String toString() {
        return value;
    }
}
