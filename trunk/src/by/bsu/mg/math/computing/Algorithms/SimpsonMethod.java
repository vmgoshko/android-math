package by.bsu.mg.math.computing.Algorithms;

import by.bsu.mg.math.computing.Calculator;
import by.bsu.mg.math.parsing.expressions.Node;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public class SimpsonMethod implements DefiniteIntegrator {

    private double a;
    private double b;
    private Node expr;
    private int sign;

    public SimpsonMethod(double a, double b, Node expr) {
        this.a = a;
        this.b = b;
        this.expr = expr;
        this.sign = 1;

        if(b < a){
            double swap = this.a;
            this.a = b;
            this.b = swap;
            this.sign = -1;
        }

    }

    @Override
    public double integrate() {

        List<Double> segments = getSegments();
        Calculator calc = new Calculator();
        double result = 0;

        for(int i = 0; i < segments.size() - 1; i++){
            double a = segments.get(i);
            double b = segments.get(i+1);

            result += (b - a)/6*(calc.calculate(expr, a) + calc.calculate(expr, (a+b)/2)*4 + calc.calculate(expr, b));
        }

        return result*sign;
    }

    private List<Double> getSegments() {
        List<Double> segments = new ArrayList<Double>();
        int segmentsAmount = getSegmentsAmount();
        double h = (b-a)/segmentsAmount;

        double position = a;
        while(position <= b){
            segments.add(position);
            position += h;
        }

        return segments;
    }

    private int getSegmentsAmount() {
        return 10000;
    }

}
