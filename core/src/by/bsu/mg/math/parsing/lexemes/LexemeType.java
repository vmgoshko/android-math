package by.bsu.mg.math.parsing.lexemes;

public enum LexemeType {
    UNDEFINED(-4),
    OPEN_BRACKET(-3),
    CLOSE_BRACKET(-2),
    SEPARATOR(-1),


    BINARY_PLUS(0),
    BINARY_MINUS(1),
    BINARY_MULTIPLY(2),
    BINARY_DIVIDE(3),
    BINARY_MOD(4),
    BINARY_POWER(5),

    UNARY_MINUS(6),
    UNARY_FACTORIAL(7),

    FUNCTION(8),

    NUMBER(9),
    VARIABLE(10);

    private static int maxPriority = 10;
    private int priority;

    LexemeType(int priority){
        this.priority = priority;
    }

    public int getPriority(){
        return priority;
    }

    public static int getMaxPriority(){
        return maxPriority;
    }

    public boolean equals(int priority){
         return this.priority == priority;
    }

    public boolean equals(LexemeType lexemeType){
        return this.priority == lexemeType.getPriority();
    }

}
