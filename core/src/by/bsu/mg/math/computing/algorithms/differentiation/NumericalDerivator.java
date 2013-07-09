package by.bsu.mg.math.computing.algorithms.differentiation;

import by.bsu.mg.math.computing.calculators.XVarCalculator;
import by.bsu.mg.math.parsing.expressions.nodes.Node;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class NumericalDerivator {
    private XVarCalculator calc = new XVarCalculator();

    public double diff(Node root, int power, double point) {
        double h = 0.00000001;
        switch (power) {
            case 1:
                return (-calc.calculate(root, point + 2 * h) +
                        8 * calc.calculate(root, point + h) -
                        8 * calc.calculate(root, point - h) +
                        calc.calculate(root, point - 2 * h))
                        / (12 * h);
            case 2:
                return (calc.calculate(root, point + h) -
                        2 * calc.calculate(root, point) +
                        calc.calculate(root, point - h))
                        / (h * h);
            case 3:
                return (calc.calculate(root, point + 3 * h / 2) -
                        3 * calc.calculate(root, point + h / 2) +
                        3 * calc.calculate(root, point - h / 2) -
                        calc.calculate(root, point - 3 * h / 2))
                        / (h * h * h);
            default:
                break;
        }

        return 0;
    }


}
