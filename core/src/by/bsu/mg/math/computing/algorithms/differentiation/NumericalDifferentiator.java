package by.bsu.mg.math.computing.algorithms.differentiation;

import by.bsu.mg.math.computing.calculators.OneVarCalculator;
import by.bsu.mg.math.parsing.expressions.nodes.Node;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class NumericalDifferentiator {

    public double diff1(Node root, double point){
        double h = 0.00000001;
        OneVarCalculator calc = new OneVarCalculator();

        double result = (   -calc.calculate(root,point + 2*h) +
                            8 * calc.calculate(root, point + h) -
                            8 * calc.calculate(root, point - h) +
                            calc.calculate(root, point - 2 * h)
                        ) / (12 * h);
        return result;
    }
}
