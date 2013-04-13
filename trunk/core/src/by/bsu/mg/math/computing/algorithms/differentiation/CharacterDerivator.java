package by.bsu.mg.math.computing.algorithms.differentiation;

import by.bsu.mg.math.exceptions.expressions.EmptyExpressionTreeException;
import by.bsu.mg.math.parsing.expressions.nodes.DoubleNode;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.parsing.expressions.nodes.StringNode;
import by.bsu.mg.math.parsing.lexemes.Lexeme;
import by.bsu.mg.math.parsing.lexemes.LexemeType;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class CharacterDerivator {

    public Node differentiate(Node expr) throws EmptyExpressionTreeException {
        Lexeme lexeme;

        // TODO: implement other derivative powers
        if (expr == null) {
            throw new EmptyExpressionTreeException();
        }

        switch (expr.getType()) {
            case BINARY_PLUS:
            case BINARY_MINUS:
                return diffBinaryPlusMinus(expr);

            case BINARY_MULTIPLY:
                return diffMult(expr);

            case BINARY_DIVIDE:
                return diffDiv(expr);

            case BINARY_POWER:
                return diffPow(expr);

            case FUNCTION:
                return diffFunction(expr);

            case NUMBER:
                return new DoubleNode(new Lexeme("0", expr.getLevel(), LexemeType.NUMBER));

            case VARIABLE:
                return new StringNode(new Lexeme("1", expr.getLevel(), LexemeType.NUMBER));
        }

        return null;
    }

    private Node diffFunction(Node expr) {
        Node root;
        Node leftMultArg = null;
        Node rightMultArg = null;
        StringNode currNode = (StringNode) expr;

        root = new StringNode(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));

        if ("sin".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("cos", 0, LexemeType.FUNCTION));
            leftMultArg.setChild(0, expr.getChild(0));
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("cos".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("-", 0, LexemeType.UNARY_MINUS));
            Node sin = new StringNode(new Lexeme("sin", 0, LexemeType.FUNCTION));

            sin.setChild(0, expr.getChild(0));

            leftMultArg.setChild(0, sin);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("tg".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new StringNode(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new StringNode(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            Node den_left = new StringNode(new Lexeme("cos", 0, LexemeType.FUNCTION));
            Node den_right = new StringNode(new Lexeme("2", 0, LexemeType.NUMBER));

            den_left.setChild(0, expr.getChild(0));

            den.setChild(0, den_left);
            den.setChild(1, den_right);

            leftMultArg.setChild(0, num);
            leftMultArg.setChild(1, den);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("ctg".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("-", 0, LexemeType.UNARY_MINUS));
            Node divide = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new StringNode(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new StringNode(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            Node den_left = new StringNode(new Lexeme("sin", 0, LexemeType.FUNCTION));
            Node den_right = new StringNode(new Lexeme("2", 0, LexemeType.NUMBER));

            den_left.setChild(0, expr.getChild(0));

            den.setChild(0, den_left);
            den.setChild(1, den_right);

            divide.setChild(0, num);
            divide.setChild(1, den);

            leftMultArg.setChild(0, divide);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("arsin".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new StringNode(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new StringNode(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            Node den_left = new StringNode(new Lexeme("-", 0, LexemeType.BINARY_MINUS));
            Node den_right = new StringNode(new Lexeme("0.5", 0, LexemeType.NUMBER));

            Node pow = new StringNode(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            pow.setChild(0, expr.getChild(0));
            pow.setChild(1, new StringNode(new Lexeme("2", 0, LexemeType.NUMBER)));

            den_left.setChild(0, new StringNode(new Lexeme("1", 0, LexemeType.NUMBER)));
            den_left.setChild(1, pow);

            den.setChild(0, den_left);
            den.setChild(1, den_right);

            leftMultArg.setChild(0, num);
            leftMultArg.setChild(1, den);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("arcos".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("-", 0, LexemeType.UNARY_MINUS));
            Node ratio = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new StringNode(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new StringNode(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            Node den_left = new StringNode(new Lexeme("-", 0, LexemeType.BINARY_MINUS));
            Node den_right = new StringNode(new Lexeme("0.5", 0, LexemeType.NUMBER));

            Node pow = new StringNode(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            pow.setChild(0, expr.getChild(0));
            pow.setChild(1, new StringNode(new Lexeme("2", 0, LexemeType.NUMBER)));

            den_left.setChild(0, new StringNode(new Lexeme("1", 0, LexemeType.NUMBER)));
            den_left.setChild(1, pow);

            den.setChild(0, den_left);
            den.setChild(1, den_right);

            ratio.setChild(0, num);
            ratio.setChild(1, den);

            leftMultArg.setChild(0, ratio);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("artg".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new StringNode(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new StringNode(new Lexeme("+", 0, LexemeType.BINARY_PLUS));
            Node den_left = new StringNode(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den_right = new StringNode(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            den_right.setChild(0, expr.getChild(0));
            den_right.setChild(1, new StringNode(new Lexeme("2", 0, LexemeType.NUMBER)));

            den.setChild(0, den_left);
            den.setChild(1, den_right);

            leftMultArg.setChild(0, num);
            leftMultArg.setChild(1, den);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("arctg".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("-", 0, LexemeType.UNARY_MINUS));
            Node ratio = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new StringNode(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new StringNode(new Lexeme("+", 0, LexemeType.BINARY_PLUS));
            Node den_left = new StringNode(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den_right = new StringNode(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            den_right.setChild(0, expr.getChild(0));
            den_right.setChild(1, new StringNode(new Lexeme("2", 0, LexemeType.NUMBER)));

            den.setChild(0, den_left);
            den.setChild(1, den_right);

            ratio.setChild(0, num);
            ratio.setChild(1, den);

            leftMultArg.setChild(0, ratio);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("ln".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            leftMultArg.setChild(0, new StringNode(new Lexeme("1", 0, LexemeType.NUMBER)));
            leftMultArg.setChild(1, expr.getChild(0));
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("lg".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node den = new StringNode(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));
            Node ln = new StringNode(new Lexeme("ln", 0, LexemeType.FUNCTION));
            ln.setChild(0, new StringNode(new Lexeme("10", 0, LexemeType.NUMBER)));

            den.setChild(0, expr.getChild(0));
            den.setChild(1, ln);

            leftMultArg.setChild(0, new StringNode(new Lexeme("1", 0, LexemeType.NUMBER)));
            leftMultArg.setChild(1, den);
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("log2".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node den = new StringNode(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));
            Node ln = new StringNode(new Lexeme("ln", 0, LexemeType.FUNCTION));
            ln.setChild(0, new StringNode(new Lexeme("2", 0, LexemeType.NUMBER)));

            den.setChild(0, expr.getChild(0));
            den.setChild(1, ln);

            leftMultArg.setChild(0, new StringNode(new Lexeme("1", 0, LexemeType.NUMBER)));
            leftMultArg.setChild(1, den);
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("logd".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node den = new StringNode(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));
            Node ln = new StringNode(new Lexeme("ln", 0, LexemeType.FUNCTION));
            ln.setChild(0, new StringNode(new Lexeme(((DoubleNode) expr.getChild(1)).getValue().toString(), 0, LexemeType.NUMBER)));

            den.setChild(0, expr.getChild(0));
            den.setChild(1, ln);

            leftMultArg.setChild(0, new StringNode(new Lexeme("1", 0, LexemeType.NUMBER)));
            leftMultArg.setChild(1, den);
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("sinh".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("cosh", 0, LexemeType.FUNCTION));
            leftMultArg.setChild(0, expr.getChild(0));
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("cosh".equals(currNode.getValue())) {
            leftMultArg = new StringNode(new Lexeme("sinh", 0, LexemeType.FUNCTION));
            leftMultArg.setChild(0, expr.getChild(0));
            rightMultArg = differentiate(expr.getChild(0));
        }

        //TODO: add other function differentiations here

        root.setChild(0, leftMultArg);
        root.setChild(1, rightMultArg);

        return root;
    }

    private Node diffPow(Node expr) {
        Node root = new StringNode(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));
        Node leftMultArg = new StringNode(new Lexeme("^", 0, LexemeType.BINARY_POWER));

        Node leftPowerArg = new DoubleNode(new Lexeme(String.valueOf(Math.exp(1)), 0, LexemeType.NUMBER));

        Node rightPowerArg = new StringNode(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));
        Node leftPowerMultArg = new StringNode(new Lexeme("ln", 0, LexemeType.FUNCTION));
        leftPowerMultArg.setChild(0, expr.getChild(0));
        rightPowerArg.setChild(0, leftPowerMultArg);
        rightPowerArg.setChild(1, expr.getChild(1));

        leftMultArg.setChild(0, leftPowerArg);
        leftMultArg.setChild(1, rightPowerArg);

        Node rightMultArg = new StringNode(new Lexeme("+", 0, LexemeType.BINARY_PLUS));

        Node leftPlusArg = new StringNode(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));
        Node leftPlusLeftArg = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
        leftPlusLeftArg.setChild(0, differentiate(expr.getChild(0)));
        leftPlusLeftArg.setChild(1, expr.getChild(0));
        leftPlusArg.setChild(0, leftPlusLeftArg);

        leftPlusArg.setChild(1, expr.getChild(1));

        Node rightPlusArg = new StringNode(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));

        Node rightPlusRightArg = new StringNode(new Lexeme("ln", 0, LexemeType.FUNCTION));
        rightPlusRightArg.setChild(0, expr.getChild(0));

        rightPlusArg.setChild(0, differentiate(expr.getChild(0)));
        rightPlusArg.setChild(1, rightPlusRightArg);

        rightMultArg.setChild(0, leftPlusArg);
        rightMultArg.setChild(1, rightPlusArg);

        root.setChild(0, leftMultArg);
        root.setChild(1, rightMultArg);
        return root;
    }

    private Node diffDiv(Node expr) {
        Node root = new StringNode(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));

        Node pow = new StringNode(new Lexeme("^", 0, LexemeType.BINARY_POWER));
        Node rightPow = new DoubleNode(new Lexeme("2", 0, LexemeType.NUMBER));
        Node leftPow = differentiate(expr.getChild(1));
        pow.setChild(0, leftPow);
        pow.setChild(1, rightPow);

        Node minus = new StringNode(new Lexeme("-", 0, LexemeType.BINARY_MINUS));
        Node minusLeftMult = new StringNode(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));
        Node minusRightMult = new StringNode(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));

        Node leftMultLeft = differentiate(expr.getChild(0));
        Node leftMultRight = expr.getChild(1);

        Node rightMultRight = expr.getChild(0);
        Node rightMultLeft = differentiate(expr.getChild(1));

        minusLeftMult.setChild(0, leftMultLeft);
        minusLeftMult.setChild(1, leftMultRight);

        minusRightMult.setChild(0, rightMultLeft);
        minusRightMult.setChild(1, rightMultRight);

        root.setChild(0, minus);
        root.setChild(1, pow);

        return root;
    }

    private Node diffMult(Node expr) {
        Node root = new StringNode(new Lexeme("+", expr.getLevel(), LexemeType.BINARY_PLUS));
        Node leftMult = new StringNode(new Lexeme("*", expr.getLevel(), LexemeType.BINARY_MULTIPLY));
        Node rightMult = new StringNode(new Lexeme("*", expr.getLevel(), LexemeType.BINARY_MULTIPLY));

        leftMult.setChild(0, differentiate(expr.getChild(0)));
        leftMult.setChild(1, expr.getChild(1));

        rightMult.setChild(0, expr.getChild(0));
        rightMult.setChild(1, differentiate(expr.getChild(1)));

        root.setChild(0, leftMult);
        root.setChild(1, rightMult);

        return root;
    }

    private Node diffBinaryPlusMinus(Node expr) {
        Node root = new StringNode(new Lexeme("+", expr.getLevel(), LexemeType.BINARY_PLUS));
        root.setChild(0, differentiate(expr.getChild(0)));
        root.setChild(1, differentiate(expr.getChild(1)));
        return root;
    }
}
