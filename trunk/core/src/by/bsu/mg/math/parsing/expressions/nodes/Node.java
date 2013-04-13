package by.bsu.mg.math.parsing.expressions.nodes;

import by.bsu.mg.math.parsing.lexemes.Lexeme;
import by.bsu.mg.math.parsing.lexemes.LexemeType;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Vladimir Goshko uhoshka@exadel.com
 */
public abstract class Node {
    private List<Node> children;
    private LexemeType type;
    private int level;

    public abstract void setValue(Lexeme value);

    public void setChild(int index, Node child) {
        if (children == null) {
            children = new LinkedList<Node>();
        }

        while (children.size() < index + 1) {
            children.add(null);
        }

        children.set(index, child);
    }

    public Node getChild(int index) {
        return children.get(index);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public int getChildrenCount() {
        return children.size();
    }

    public LexemeType getType() {
        return type;
    }

    public void setType(LexemeType type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}