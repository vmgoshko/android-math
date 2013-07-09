package by.bsu.mg.math.parsing.expressions;

import by.bsu.mg.math.exceptions.parsing.CantParseException;
import by.bsu.mg.math.parsing.expressions.nodes.DoubleNode;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.parsing.expressions.nodes.StringNode;
import by.bsu.mg.math.parsing.lexemes.*;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExpressionBuilder {
    private List<Lexeme> lexemes;
    private int maxLevel;
    private int maxPriority = 12;
    private Queue<IError> errors = new ArrayDeque<IError>();

    /**
     * Parse string expression and build it's tree
     *
     * @param expression : expression to parse
     * @return Node root    : root of expression tree
     * @throws CantParseException
     * @see by.bsu.mg.math.parsing.expressions.nodes.Node
     * @see CantParseException
     */
    public Node buildTree(String expression) throws CantParseException {
        LexemeParser parser = new LexemeParser(expression);
        lexemes = parser.parse();

        LexemeEvaluator.evaluateLexemeList(lexemes);
        LexemeListValidator validator = new LexemeListValidator(lexemes);

        if (!validator.validate()) {
            errors.addAll(validator.getErrors());
            throw new CantParseException(expression, errors);
        }

        maxLevel = LexemeEvaluator.getMaxLevel();
        Node exprTreeRoot = findRoot(0, 0, lexemes.size());

        //   ExpressionOptimizer exprOptimizer = new ExpressionOptimizer();
        //   exprOptimizer.optimize(exprTreeRoot);

        return exprTreeRoot;
    }

    private Node findRoot(int level, int begin, int end) {
        Node root = null;
        while (level <= maxLevel) {
            for (int priority = 0; priority < maxPriority; priority++) {
                if (root != null) {
                    break;
                }
                root = findPriorityLeveledRoot(priority, level, begin, end);

            }
            level++;
        }
        return root;
    }

    private Node findPriorityLeveledRoot(int priority, int level, int begin, int end) {
        Node root;

        for (int i = end - 1; i >= begin; i--) {
            Lexeme lexeme = lexemes.get(i);
            if (lexeme.getPriority() == priority && lexeme.getLevel() == level) {
                if (LexemeEvaluator.isUnaryMinus(lexeme.getType())) {
                    root = new StringNode();
                    root.setValue(lexeme);
                    root.setChild(0, findRoot(level, i + 1, end));
                    return root;
                }

                if (LexemeEvaluator.isFactorial(lexeme)) {
                    root = new StringNode();
                    root.setValue(lexeme);
                    root.setChild(0, findRoot(level, begin, i));
                    return root;
                }

                if (LexemeEvaluator.isBinary(lexeme.getType())) {
                    root = new StringNode();
                    root.setValue(lexeme);
                    root.setChild(0, findRoot(level, begin, i));
                    root.setChild(1, findRoot(level, i + 1, end));
                    return root;
                }

                if (LexemeEvaluator.isFunction(lexeme.getType())) {
                    root = new StringNode();
                    root.setValue(lexeme);
                    root.setChildren(getFunctionChildrenList(level + 1, i + 1, findCloseBracket(i + 1)));
                    return root;
                }

                if (LexemeEvaluator.isSimpleArgument(lexeme)) {
                    if (lexeme.getType() == LexemeType.VARIABLE) {
                        root = new StringNode();
                    } else {
                        root = new DoubleNode();
                    }

                    root.setValue(lexeme);
                    return root;
                }
            }
        }
        return null;
    }

    private List<Node> getFunctionChildrenList(int level, int begin, int end) {
        List<Node> children = new LinkedList<Node>();
        List<Integer> arguments = separateArguments(level, begin, end);

        for (int i = 0; i < arguments.size() - 1; i += 2) {
            children.add(findRoot(level, arguments.get(i), arguments.get(i + 1)));
        }

        return children;
    }

    private List<Integer> separateArguments(int level, int begin, int end) {
        List<Integer> argumentsIndexes = new LinkedList<Integer>();
        int argBegin = begin + 1;
        int argEnd;

        for (int i = begin; i < end; i++) {
            Lexeme sign = lexemes.get(i);
            if (LexemeEvaluator.isSeparator(sign) && sign.getLevel() == level) {
                argEnd = i;
                argumentsIndexes.add(argBegin);
                argumentsIndexes.add(argEnd);
                argBegin = argEnd + 1;
            }
        }

        argumentsIndexes.add(argBegin);
        argumentsIndexes.add(end);

        return argumentsIndexes;
    }

    private int findCloseBracket(int i) {
        int bracketCount = 0;
        Lexeme lexeme = lexemes.get(i);
        if (lexeme.getType() == LexemeType.OPEN_BRACKET) {
            bracketCount++;
            i++;
        }

        while (i < lexemes.size() && bracketCount != 0) {
            lexeme = lexemes.get(i);
            if (lexeme.getType() == LexemeType.OPEN_BRACKET) {
                bracketCount++;
            }

            if (lexeme.getType() == LexemeType.CLOSE_BRACKET) {
                bracketCount--;
            }

            i++;
        }

        return i - 1;
    }

    public Queue<IError> getErrors() {
        return errors;
    }
}
