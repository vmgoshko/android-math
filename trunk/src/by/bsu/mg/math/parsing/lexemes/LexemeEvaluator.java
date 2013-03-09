package by.bsu.mg.math.parsing.lexemes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Create by Vladimir Goshko goshkovm@tut.by
 */

/*
sin
cos
tg
crg
arsin
arcos
artg
arctg
ln
lg
log2
logd
todeg
torad
abs
floor
ceil
sinh
cosh
max
min
*/
public class LexemeEvaluator {
    private static String[] binaryOperations = {"+", "-", "*", "/", "^", "%"};

    private static String[] functionOperations = {"sin", "cos", "tg", "ctg", "arsin", "arcos",
                                                  "artg", "arctg", "ln", "lg", "log2", "logd",
                                                  "todeg", "torad", "abs", "floor", "ceil", "sinh",
                                                  "cosh", "max", "min"};

    private static String[] variables = {"x", "y", "z", "t"};
    private static String[] constants = {"pi", "e"};
    private static String[] unaryOperations = {"-", "!"};
    private static String[] separators = {","};

    private static int bracketLevel = 0;
    private static int maxLevel;

    private static List<Lexeme> lexemes;

    private static class StringComparator implements Comparator<String> {

        @Override
        public int compare(String left, String right) {
            return right.compareTo(left);
        }
    }


    public static List<Lexeme> evaluateLexemeList(List<Lexeme> lexemeList) {
        lexemes = lexemeList;

        sortLexemeArrays();
        for (int i = 0; i < lexemes.size(); i++) {
            LexemeType lexemeType = evaluateLexeme(i);
            lexemes.get(i).setType(lexemeType);
            lexemes.get(i).setLevel(bracketLevel);

            if (bracketLevel > maxLevel) {
                maxLevel = bracketLevel;
            }
        }

        return lexemes;
    }

    private static void sortLexemeArrays() {
        StringComparator comparator = new StringComparator();
        Arrays.sort(binaryOperations, comparator);
        Arrays.sort(functionOperations, comparator);
        Arrays.sort(variables, comparator);
        Arrays.sort(constants, comparator);
        Arrays.sort(unaryOperations, comparator);
        Arrays.sort(separators, comparator);
    }

    public static int getMaxLevel() {
        return maxLevel;
    }

    private static LexemeType evaluateLexeme(int index) {
        LexemeType type;

        type = getBinary(index);

        if (type == LexemeType.UNDEFINED) {
            type = getFunction(index);
        }

        if (type == LexemeType.UNDEFINED) {
            type = getBracket(index);
        }

        if (type == LexemeType.UNDEFINED) {
            type = getSimpleArgument(index);
        }

        if (type == LexemeType.UNDEFINED) {
            type = getSeparator(index);
        }

        return type;
    }

    private static LexemeType getSimpleArgument(int index) {
        Lexeme lexeme = lexemes.get(index);
        LexemeType result = LexemeType.UNDEFINED;

        if ("x y z t".contains(lexeme.getValue())) {
            result = LexemeType.VARIABLE;
            return result;
        }

        if (isNumber(lexeme.getValue())) {
            result = LexemeType.NUMBER;
            return result;
        }

        if(isConstant(lexeme.getValue())){
            result = LexemeType.NUMBER;
            replaceConstant(lexeme);
            return result;
        }
        return result;
    }

    private static void replaceConstant(Lexeme lexeme) {
        if("pi".equals(lexeme.getValue())){
            lexeme.setValue(String.valueOf(3.141592653589793238));
            return;
        }

        if("e".equals(lexeme.getValue())){
            lexeme.setValue(String.valueOf(2.718281828459045235));
            return;
        }
    }

    private static boolean isConstant(String value) {

        int valueIndex = Arrays.binarySearch(constants, value, new StringComparator());

        return valueIndex >= 0;
    }

    private static LexemeType getFunction(int index) {
        Lexeme lexeme = lexemes.get(index);

        int lexemeValueIndex = Arrays.binarySearch(functionOperations, lexeme.getValue(), new StringComparator());

        if(lexemeValueIndex >= 0){
           return LexemeType.FUNCTION;
        }

        return LexemeType.UNDEFINED;
    }

    private static LexemeType getBinary(int index) {
        Lexeme lexeme = lexemes.get(index);

        if ("+".equals(lexeme.getValue())) {
            return LexemeType.BINARY_PLUS;
        }

        if ("-".equals(lexeme.getValue())) {
            return defineMinusSign(index);
        }

        if ("*".equals(lexeme.getValue())) {
            return LexemeType.BINARY_MULTIPLY;
        }

        if ("/".equals(lexeme.getValue())) {
            return LexemeType.BINARY_DIVIDE;
        }

        if ("^".equals(lexeme.getValue())) {
            return LexemeType.BINARY_POWER;
        }

        if ("%".equals(lexeme.getValue())) {
            return LexemeType.BINARY_MOD;
        }

        return LexemeType.UNDEFINED;
    }

    private static LexemeType getBracket(int index) {
        Lexeme lexeme = lexemes.get(index);

        if ("(".equals(lexeme.getValue())) {
            bracketLevel++;
            return LexemeType.OPEN_BRACKET;
        }

        if (")".equals(lexeme.getValue())) {
            bracketLevel--;
            return LexemeType.CLOSE_BRACKET;
        }

        return LexemeType.UNDEFINED;
    }

    private static LexemeType getSeparator(int index) {
        Lexeme lexeme = lexemes.get(index);

        if (",".equals(lexeme.getValue())) {
            return LexemeType.SEPARATOR;
        }

        return LexemeType.UNDEFINED;
    }

    private static LexemeType defineMinusSign(int index) {
        if (index == 0) {
            return LexemeType.UNARY_MINUS;
        }

        Lexeme preMinusLexeme = lexemes.get(index - 1);

        LexemeType definedType;
        if(preMinusLexeme.getType() == LexemeType.OPEN_BRACKET){
            definedType = LexemeType.UNARY_MINUS;
        } else {
            definedType = LexemeType.BINARY_MINUS;
        }

        return definedType;
    }



    private static boolean isNumber(String str) {
        if (!(str.charAt(0) >= '0' && str.charAt(0) <= '9')) {
            return false;
        }

        for (int i = 1; i < str.length(); i++) {
            if (!((str.charAt(0) >= '0' && str.charAt(0) <= '9') || str.charAt(i) == '.')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUnaryMinus(Lexeme lexeme) {
        return lexeme.getType() == LexemeType.UNARY_MINUS;
    }

    public static boolean isFactorial(Lexeme lexeme) {
        return lexeme.getType() == LexemeType.UNARY_FACTORIAL;
    }

    public static boolean isBinary(Lexeme lexeme) {
        LexemeType type = lexeme.getType();
        return (type == LexemeType.BINARY_DIVIDE) ||
                (type == LexemeType.BINARY_PLUS) ||
                (type == LexemeType.BINARY_MINUS) ||
                (type == LexemeType.BINARY_MULTIPLY) ||
                (type == LexemeType.BINARY_POWER);
    }

    public static boolean isFunction(Lexeme lexeme) {
        return lexeme.getType() == LexemeType.FUNCTION;
    }

    public static boolean isSimpleArgument(Lexeme lexeme) {
        LexemeType type = lexeme.getType();
        return (type == LexemeType.NUMBER) ||
                (type == LexemeType.VARIABLE);
    }

    public static boolean isSeparator(Lexeme lexeme) {
        return lexeme.getType() == LexemeType.SEPARATOR;
    }
}
