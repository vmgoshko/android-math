package by.bsu.mg.math.parsing.expressions;

import by.bsu.mg.math.parsing.lexemes.Lexeme;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Vladimir Goshko uhoshka@exadel.com
 */
public class Node {
    private Lexeme value;
    private List<Node> children;

    public Node() {
    }

    public Node(Lexeme lexeme) {
        this.value = lexeme;
    }

    public void setValue(Lexeme value) {
        this.value = value;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void setChild(int index, Node child) {
        if (children == null) {
            children = new LinkedList<Node>();
        }
        while (children.size() < index + 1) {
            children.add(null);
        }
        children.set(index, child);
    }

    public Lexeme getValue() {
        return value;
    }

    public Node getChild(int index) {
        return children.get(index);
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getChildrenCount() {
        return children.size();
    }
}
