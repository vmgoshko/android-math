package by.bsu.mg.math.parsing.expressions.nodes;

import by.bsu.mg.math.parsing.lexemes.Lexeme;

/**
 * Vladimir Goshko vmgoshko@gmail.com
 */
public class DoubleNode extends Node {
    private Double value;

    public DoubleNode() {

    }

    public DoubleNode(Lexeme lexeme) {
        value = Double.valueOf(lexeme.getValue());
        setType(lexeme.getType());
        setLevel(lexeme.getLevel());
    }

    @Override
    public void setValue(Lexeme value) {
        this.value = Double.valueOf(value.getValue());
        setLevel(value.getLevel());
        setType(value.getType());
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
