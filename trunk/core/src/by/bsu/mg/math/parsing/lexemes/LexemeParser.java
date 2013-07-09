package by.bsu.mg.math.parsing.lexemes;

import java.util.ArrayList;
import java.util.List;

public class LexemeParser {

    private List<Lexeme> lexemes;
    private int currentPosition;
    private int exprLen;
    private String expr;

    /**
     * @param expression - expression to parse
     */
    public LexemeParser(String expression) {
        lexemes = new ArrayList<Lexeme>();
        expr = expression;
        exprLen = expr.length();
    }

    /**
     * Splits expression to lexemes.
     *
     * @return lexemes list.
     */
    public List<Lexeme> parse() {
        while (currentPosition < exprLen) {
            getLexeme();
        }

        return lexemes;
    }

    /**
     * Cuts next lexeme from string.
     *
     * @return String - lexeme.
     */
    private void getLexeme() {
        String lexemeValue = null;
        int i = currentPosition;

        while (i < expr.length()) {
            if (isOneSymbolLexeme(expr, i)) {
                lexemeValue = expr.substring(i, i + 1);
                lexemes.add(new Lexeme(lexemeValue));
                break;
            }

            if (isAlphabet(expr, i)) {
                lexemeValue = getWord(expr, i);
                lexemes.add(new Lexeme(lexemeValue));
                break;
            }

            if (isNumber(expr, i)) {
                lexemeValue = getNumber(expr, i);
                lexemes.add(new Lexeme(lexemeValue));
                break;
            }
        }

        if (lexemeValue != null) {
            currentPosition += lexemeValue.length();
        } else {
            throw new IllegalArgumentException("Lexeme can't be token at position " + currentPosition);
        }
    }

    /**
     * Tries to cut number lexeme.
     *
     * @param expr  - expression.
     * @param start - start index of lexeme.
     * @return number lexeme
     */
    private String getNumber(String expr, int start) {
        StringBuilder number = new StringBuilder();
        while (start < expr.length() && isCharBetween(expr.charAt(start), '0', '9', '.')) {
            number.append(expr.charAt(start));
            start++;
        }
        return number.toString();
    }

    /**
     * Tries to cut word lexeme.
     *
     * @param expr  - expression.
     * @param start - start index of lexeme.
     * @return lexeme
     */
    private String getWord(String expr, int start) {
        StringBuilder word = new StringBuilder();
        while (start < expr.length() && isAlphaNum(expr, start)) {
            word.append(expr.charAt(start));
            start++;
        }
        return word.toString();
    }

    /**
     * Checks that char at "position" is numerical symbol.
     *
     * @param expr
     * @param position
     * @return false if char at "position" isn't numerical symbol.
     */
    private boolean isNumber(String expr, int position) {
        char c = expr.charAt(position);
        return isCharBetween(c, '0', '9');
    }

    /**
     * Checks that char at "position" is alphabetic symbol.
     *
     * @param expr
     * @param position
     * @return false if char at "position" isn't alphabetic symbol.
     */
    private boolean isAlphabet(String expr, int position) {
        char c = expr.charAt(position);
        return isCharBetween(c, 'A', 'Z') || isCharBetween(c, 'a', 'z') || c == 'π';
    }

    private boolean isAlphaNum(String expr, int position) {
        char c = expr.charAt(position);
        return isAlphabet(expr, position) || isNumber(expr, position);
    }

    /**
     * Checks that char at "position" is one-symbol lexeme such as ( ) + - * / ! ^ ,
     *
     * @param expr
     * @param position
     * @return false if char at "position" isn't one-symbol lexeme.
     */
    private boolean isOneSymbolLexeme(String expr, int position) {
        char c = expr.charAt(position);
        return isCharExist(c, '(', ')', '+', '-', '*', '/', '!', '^', ',', '%', '∞');
    }

    /**
     * Checks that "symbol" list contains "character".
     *
     * @param character
     * @param symbols
     * @return true if "symbol list not contains "character".
     */
    private boolean isCharExist(char character, char... symbols) {
        for (Character symbol : symbols) {
            if (character == symbol) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks that "character" lies between "begin" char and "end" char, or "others" char list contains "character".
     *
     * @param character
     * @param begin
     * @param end
     * @param others
     * @return false
     */
    private boolean isCharBetween(char character, char begin, char end, char... others) {
        for (Character symbol : others) {
            if (character == symbol) {
                return true;
            }
        }
        return character >= begin && character <= end;
    }
}