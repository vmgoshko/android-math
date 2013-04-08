package by.bsu.mg.math.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: test
 * Date: 2/20/13
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class IllegalArgumentsAmountException extends RuntimeException {
    private String operation;
    private int argsAmount;

    public IllegalArgumentsAmountException(String operation, int argsAmount){
        this.operation = operation;
        this.argsAmount = argsAmount;
    }

    public String toString(){
        return String.format("Operation %s can't have %d arguments", operation, argsAmount);
    }

}
