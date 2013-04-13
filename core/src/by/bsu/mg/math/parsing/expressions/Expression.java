package by.bsu.mg.math.parsing.expressions;

import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.parsing.lexemes.LexemeType;

import java.util.Map;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class Expression {
    private Node expressionRoot;
    private Map<String, Double> varMap;

    public Expression(Node expressionRoot) {
        this.expressionRoot = expressionRoot;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        nodeToString(expressionRoot, string, 0);
        return string.toString();
    }

    private void nodeToString(Node node, StringBuilder str, int pos) {
        LexemeType type = node.getType();

        switch (type) {
            case BINARY_MINUS:
            case BINARY_DIVIDE:
            case BINARY_MULTIPLY:
            case BINARY_POWER:
            case BINARY_PLUS:
                // +
                str.insert(pos, node.toString());
                // +()
                str.insert(pos + 1, "()");
                // +(...)
                nodeToString(node.getChild(1), str, pos + 2);
                //()+(...)
                str.insert(pos, "()");
                //(...)+(...)
                nodeToString(node.getChild(0), str, pos + 1);
                break;
            case UNARY_FACTORIAL:
                // !
                str.insert(pos, node.toString());
                // ()!
                str.insert(pos, "()");
                // (...)!
                nodeToString(node.getChild(0), str, pos + 1);
                break;
            case UNARY_MINUS:
                // -
                str.insert(pos, node.toString());
                // -()
                str.insert(pos + 1, "()");
                // -(...)
                nodeToString(node.getChild(0), str, pos + 2);
                break;
            case FUNCTION:
                int funcLen = node.toString().length();
                int childCount = node.getChildrenCount();
                // max
                str.insert(pos, node.toString());
                // max()
                str.insert(pos + funcLen, "()");
                // max(,,,)
                for (int i = 0; i < childCount - 1; i++) {
                    str.insert(pos + funcLen + 1, ",");
                }

                int currLen;
                int prevLen = str.length();
                for (int j = 0; j < childCount; j++) {
                    currLen = str.length();
                    int offset = currLen - prevLen;
                    nodeToString(node.getChild(j), str, (pos + funcLen + 1) + offset + j);
                }
                break;
            case NUMBER:
            case VARIABLE:
                str.insert(pos, node.toString());
                break;


        }

    }

}
