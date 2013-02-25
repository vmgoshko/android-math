package by.bsu.mg.math.computing;

import by.bsu.mg.math.exceptions.expressions.EmptyExpressionTreeException;
import by.bsu.mg.math.parsing.lexemes.Lexeme;
import by.bsu.mg.math.parsing.lexemes.LexemeType;
import by.bsu.mg.math.parsing.expressions.Node;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class Differentiator {

    public Node differentiate(Node expr) throws EmptyExpressionTreeException {
        Lexeme lexeme;

        if(expr != null){
            throw new EmptyExpressionTreeException();
        }

        switch (expr.getValue().getType()) {
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
                lexeme = expr.getValue();
                return new Node(new Lexeme("0", lexeme.getLevel(), LexemeType.NUMBER));

            case VARIABLE:
                lexeme = expr.getValue();
                return new Node(new Lexeme("1", lexeme.getLevel(), LexemeType.NUMBER));
        }

        return null;
    }

    private Node diffFunction(Node expr) {
        Node root;
        Node leftMultArg = null;
        Node rightMultArg = null;

        Lexeme funcLexeme = expr.getValue();
        root = new Node(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));


        if ("sin".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("cos", 0, LexemeType.FUNCTION));
            leftMultArg.setChild(0, expr.getChild(0));
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("cos".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("-", 0, LexemeType.UNARY_MINUS));
            Node sin = new Node(new Lexeme("sin", 0, LexemeType.FUNCTION));

            sin.setChild(0, expr.getChild(0));

            leftMultArg.setChild(0, sin);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("tg".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new Node(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new Node(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            Node den_left = new Node(new Lexeme("cos", 0, LexemeType.FUNCTION));
            Node den_right = new Node(new Lexeme("2", 0, LexemeType.NUMBER));

            den_left.setChild(0,expr.getChild(0));

            den.setChild(0,den_left);
            den.setChild(1,den_right);

            leftMultArg.setChild(0, num);
            leftMultArg.setChild(1, den);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("ctg".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("-", 0, LexemeType.UNARY_MINUS));
            Node divide = new Node(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new Node(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new Node(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            Node den_left = new Node(new Lexeme("sin", 0, LexemeType.FUNCTION));
            Node den_right = new Node(new Lexeme("2", 0, LexemeType.NUMBER));

            den_left.setChild(0,expr.getChild(0));

            den.setChild(0,den_left);
            den.setChild(1,den_right);

            divide.setChild(0, num);
            divide.setChild(1, den);

            leftMultArg.setChild(0,divide);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("arsin".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new Node(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new Node(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            Node den_left = new Node(new Lexeme("-", 0, LexemeType.BINARY_MINUS));
            Node den_right = new Node(new Lexeme("0.5", 0, LexemeType.NUMBER));

            Node pow = new Node(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            pow.setChild(0,expr.getChild(0));
            pow.setChild(1,new Node(new Lexeme("2", 0, LexemeType.NUMBER)));

            den_left.setChild(0,new Node(new Lexeme("1", 0, LexemeType.NUMBER)));
            den_left.setChild(1,pow);

            den.setChild(0,den_left);
            den.setChild(1,den_right);

            leftMultArg.setChild(0, num);
            leftMultArg.setChild(1, den);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("arcos".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("-", 0, LexemeType.UNARY_MINUS));
            Node ratio = new Node(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new Node(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new Node(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            Node den_left = new Node(new Lexeme("-", 0, LexemeType.BINARY_MINUS));
            Node den_right = new Node(new Lexeme("0.5", 0, LexemeType.NUMBER));

            Node pow = new Node(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            pow.setChild(0,expr.getChild(0));
            pow.setChild(1,new Node(new Lexeme("2", 0, LexemeType.NUMBER)));

            den_left.setChild(0,new Node(new Lexeme("1", 0, LexemeType.NUMBER)));
            den_left.setChild(1,pow);

            den.setChild(0,den_left);
            den.setChild(1,den_right);

            ratio.setChild(0, num);
            ratio.setChild(1, den);

            leftMultArg.setChild(0,ratio);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("artg".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new Node(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new Node(new Lexeme("+", 0, LexemeType.BINARY_PLUS));
            Node den_left = new Node(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den_right = new Node(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            den_right.setChild(0,expr.getChild(0));
            den_right.setChild(1,new Node(new Lexeme("2", 0, LexemeType.NUMBER)));

            den.setChild(0,den_left);
            den.setChild(1,den_right);

            leftMultArg.setChild(0,num);
            leftMultArg.setChild(1,den);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("arctg".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("-", 0, LexemeType.UNARY_MINUS));
            Node ratio = new Node(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node num = new Node(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den = new Node(new Lexeme("+", 0, LexemeType.BINARY_PLUS));
            Node den_left = new Node(new Lexeme("1", 0, LexemeType.NUMBER));
            Node den_right = new Node(new Lexeme("^", 0, LexemeType.BINARY_POWER));
            den_right.setChild(0,expr.getChild(0));
            den_right.setChild(1,new Node(new Lexeme("2", 0, LexemeType.NUMBER)));

            den.setChild(0,den_left);
            den.setChild(1,den_right);

            ratio.setChild(0,num);
            ratio.setChild(1,den);

            leftMultArg.setChild(0,ratio);

            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("ln".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            leftMultArg.setChild(0, new Node( new Lexeme("1", 0, LexemeType.NUMBER)));
            leftMultArg.setChild(1, expr.getChild(0));
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("lg".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node den = new Node(new Lexeme("*",0,LexemeType.BINARY_MULTIPLY));
            Node ln = new Node(new Lexeme("ln",0,LexemeType.FUNCTION));
            ln.setChild(0,new Node(new Lexeme("10",0,LexemeType.NUMBER)));

            den.setChild(0,expr.getChild(0));
            den.setChild(1,ln);

            leftMultArg.setChild(0, new Node( new Lexeme("1", 0, LexemeType.NUMBER)));
            leftMultArg.setChild(1, den);
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("log2".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node den = new Node(new Lexeme("*",0,LexemeType.BINARY_MULTIPLY));
            Node ln = new Node(new Lexeme("ln",0,LexemeType.FUNCTION));
            ln.setChild(0,new Node(new Lexeme("2",0,LexemeType.NUMBER)));

            den.setChild(0,expr.getChild(0));
            den.setChild(1,ln);

            leftMultArg.setChild(0, new Node( new Lexeme("1", 0, LexemeType.NUMBER)));
            leftMultArg.setChild(1, den);
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("logd".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("/", 0, LexemeType.BINARY_DIVIDE));
            Node den = new Node(new Lexeme("*",0,LexemeType.BINARY_MULTIPLY));
            Node ln = new Node(new Lexeme("ln",0,LexemeType.FUNCTION));
            ln.setChild(0,new Node(new Lexeme( expr.getChild(1).getValue().getValue(),0,LexemeType.NUMBER)));

            den.setChild(0,expr.getChild(0));
            den.setChild(1,ln);

            leftMultArg.setChild(0, new Node( new Lexeme("1", 0, LexemeType.NUMBER)));
            leftMultArg.setChild(1, den);
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("sinh".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("cosh", 0, LexemeType.FUNCTION));
            leftMultArg.setChild(0, expr.getChild(0));
            rightMultArg = differentiate(expr.getChild(0));
        }

        if ("cosh".equals(funcLexeme.getValue())) {
            leftMultArg = new Node(new Lexeme("sinh", 0, LexemeType.FUNCTION));
            leftMultArg.setChild(0, expr.getChild(0));
            rightMultArg = differentiate(expr.getChild(0));
        }

        //TODO: add other function differentiations here

        root.setChild(0, leftMultArg);
        root.setChild(1, rightMultArg);

        return root;
    }

    private Node diffPow(Node expr) {
        Node root = new Node( new Lexeme("*",0,LexemeType.BINARY_MULTIPLY));
        Node leftMultArg = new Node( new Lexeme("^",0,LexemeType.BINARY_POWER));

        Node leftPowerArg = new Node( new Lexeme(String.valueOf(Math.exp(1)),0,LexemeType.NUMBER));

        Node rightPowerArg =  new Node( new Lexeme("*",0,LexemeType.BINARY_MULTIPLY));
        Node leftPowerMultArg = new Node( new Lexeme("ln",0,LexemeType.FUNCTION));
        leftPowerMultArg.setChild(0,expr.getChild(0));
        rightPowerArg.setChild(0,leftPowerMultArg);
        rightPowerArg.setChild(1,expr.getChild(1));

        leftMultArg.setChild(0,leftPowerArg);
        leftMultArg.setChild(1,rightPowerArg);

        Node rightMultArg = new Node( new Lexeme("+",0,LexemeType.BINARY_PLUS));

        Node leftPlusArg =  new Node( new Lexeme("*",0,LexemeType.BINARY_MULTIPLY));
        Node leftPlusLeftArg = new Node( new Lexeme("/",0,LexemeType.BINARY_DIVIDE));
        leftPlusLeftArg.setChild(0,differentiate(expr.getChild(0)));
        leftPlusLeftArg.setChild(1,expr.getChild(0));
        leftPlusArg.setChild(0,leftPlusLeftArg);

        leftPlusArg.setChild(1,expr.getChild(1));

        Node rightPlusArg =  new Node( new Lexeme("*",0,LexemeType.BINARY_MULTIPLY));

        Node rightPlusRightArg = new Node(new Lexeme("ln",0,LexemeType.FUNCTION));
        rightPlusRightArg.setChild(0,expr.getChild(0));

        rightPlusArg.setChild(0,differentiate(expr.getChild(0)));
        rightPlusArg.setChild(1,rightPlusRightArg);

        rightMultArg.setChild(0,leftPlusArg);
        rightMultArg.setChild(1,rightPlusArg);

        root.setChild(0,leftMultArg);
        root.setChild(1,rightMultArg);
        return root;
    }

    private Node diffDiv(Node expr) {
        Node root = new Node(expr.getValue());

        Node pow = new Node(new Lexeme("^", 0, LexemeType.BINARY_POWER));
        Node rightPow = new Node(new Lexeme("2", 0, LexemeType.NUMBER));
        Node leftPow = differentiate(expr.getChild(1));
        pow.setChild(0, leftPow);
        pow.setChild(1, rightPow);

        Node minus = new Node(new Lexeme("-", 0, LexemeType.BINARY_MINUS));
        Node minusLeftMult = new Node(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));
        Node minusRightMult = new Node(new Lexeme("*", 0, LexemeType.BINARY_MULTIPLY));

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
        Node root = new Node(new Lexeme("+", expr.getValue().getLevel(), LexemeType.BINARY_PLUS));
        Node leftMult = new Node(new Lexeme("*", expr.getValue().getLevel(), LexemeType.BINARY_MULTIPLY));
        Node rightMult = new Node(new Lexeme("*", expr.getValue().getLevel(), LexemeType.BINARY_MULTIPLY));

        leftMult.setChild(0, differentiate(expr.getChild(0)));
        leftMult.setChild(1, expr.getChild(1));

        rightMult.setChild(0, expr.getChild(0));
        rightMult.setChild(1, differentiate(expr.getChild(1)));

        root.setChild(0, leftMult);
        root.setChild(1, rightMult);

        return root;
    }

    private Node diffBinaryPlusMinus(Node expr) {
        Node root = new Node();
        root.setValue(expr.getValue());
        root.setChild(0, differentiate(expr.getChild(0)));
        root.setChild(1, differentiate(expr.getChild(1)));
        return root;
    }
}
