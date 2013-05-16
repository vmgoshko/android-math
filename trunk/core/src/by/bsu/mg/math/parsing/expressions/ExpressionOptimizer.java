package by.bsu.mg.math.parsing.expressions;

import by.bsu.mg.math.computing.calculators.XVarCalculator;
import by.bsu.mg.math.parsing.expressions.nodes.Node;
import by.bsu.mg.math.parsing.lexemes.Lexeme;
import by.bsu.mg.math.parsing.lexemes.LexemeType;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class ExpressionOptimizer {

    private final XVarCalculator calc = new XVarCalculator();

    public void optimize(Node root) {
        //tryConstant(root);
        //TODO: other optimization
    }

    private boolean tryConstant(Node root) {
        if (root.getChildren() == null) {
            return root.getType() == LexemeType.NUMBER;
        }

        boolean isConstBranches = true;
        for (int i = 0; i < root.getChildrenCount(); i++) {
            isConstBranches &= tryConstant(root.getChild(i));
        }

        if (isConstBranches) {
            double subTreeValue = calc.calculateNoArg(root);
            root.setChildren(null);
            root.setValue(
                    new Lexeme(Double.toString(subTreeValue), root.getLevel(), LexemeType.NUMBER)
            );
        }

        return isConstBranches;
    }
}
