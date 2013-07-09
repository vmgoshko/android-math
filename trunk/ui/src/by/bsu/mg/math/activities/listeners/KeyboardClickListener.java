package by.bsu.mg.math.activities.listeners;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import by.bsu.mg.math.activities.R;
import by.bsu.mg.math.parsing.lexemes.Lexeme;
import by.bsu.mg.math.parsing.lexemes.LexemeType;
import by.bsu.mg.math.views.custom.ExpressionEditText;
import by.bsu.mg.math.views.pages.KeysPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Goshko vmgoshko@gmail.com
 */
public abstract class KeyboardClickListener implements View.OnClickListener, View.OnTouchListener {
    protected ExpressionEditText currentEdit;
    protected TextView outView;
    protected Activity activity;
    private ViewPager pager;
    private List<Lexeme> lexemesToPrint = new ArrayList<>();

    public KeyboardClickListener(Activity activity, ViewPager pager, TextView outView) {
        this.activity = activity;
        this.pager = pager;
        this.outView = outView;
    }

    public void registerListener() {
        registerFunctions();
        registerNumbers();
        registerBinary();
        registerConstant();
        registerOther();
        registerVars();
    }

    private void registerVars() {
        registerToView(R.id.btnX);
        registerToView(R.id.btnY);
        registerToView(R.id.btnT);
    }

    private void registerConstant() {
        registerToView(R.id.btnPi);
        registerToView(R.id.btnE);
        registerToView(R.id.btnInf);
    }

    private void registerOther() {
        registerToView(R.id.btnOpenBracket);
        registerToView(R.id.btnCloseBracket);
        registerToView(R.id.btnClear);
        registerToView(R.id.btnLArrow);
        registerToView(R.id.btnRArrow);
        registerToView(R.id.btnEqual);
        registerToView(R.id.btnComma);
        registerToView(R.id.btnBackspace);
    }

    private void registerBinary() {
        registerToView(R.id.btnPlus);
        registerToView(R.id.btnMinus);
        registerToView(R.id.btnMul);
        registerToView(R.id.btnDiv);
        registerToView(R.id.btnMod);
        registerToView(R.id.btnPow);
    }

    private void registerNumbers() {
        registerToView(R.id.btn0);
        registerToView(R.id.btn1);
        registerToView(R.id.btn2);
        registerToView(R.id.btn3);
        registerToView(R.id.btn4);
        registerToView(R.id.btn5);
        registerToView(R.id.btn6);
        registerToView(R.id.btn7);
        registerToView(R.id.btn8);
        registerToView(R.id.btn9);
        registerToView(R.id.btnDot);
    }

    private void registerFunctions() {
        registerToView(R.id.btnSin);
        registerToView(R.id.btnCos);
        registerToView(R.id.btnTg);
        registerToView(R.id.btnCtg);
        registerToView(R.id.btnArsin);
        registerToView(R.id.btnArcos);
        registerToView(R.id.btnArtg);
        registerToView(R.id.btnArctg);
        registerToView(R.id.btnLn);
        registerToView(R.id.btnLg);
        registerToView(R.id.btnLog2);
        registerToView(R.id.btnLogd);
        registerToView(R.id.btnSh);
        registerToView(R.id.btnCh);
        registerToView(R.id.btnTh);
        registerToView(R.id.btnCth);
        registerToView(R.id.btnAbs);
        registerToView(R.id.btnFloor);
        registerToView(R.id.btnCeil);
        registerToView(R.id.btnSign);
        registerToView(R.id.btnMax);
        registerToView(R.id.btnMin);
        registerToView(R.id.btnTodeg);
        registerToView(R.id.btnTorad);
    }

    private View findView(int id) {
        View view = activity.findViewById(id);
        for (int i = 0; i < pager.getAdapter().getCount(); i++) {
            if (view == null) {
                View currentKeyboard = ((KeysPagerAdapter) pager.getAdapter()).getPages().get(i);
                view = currentKeyboard.findViewById(id);
            }
        }

        return view;
    }

    private void registerToView(int id) {
        View view = findView(id);
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnSin:
            case R.id.btnCos:
            case R.id.btnTg:
            case R.id.btnCtg:
            case R.id.btnArsin:
            case R.id.btnArcos:
            case R.id.btnArtg:
            case R.id.btnArctg:
            case R.id.btnLn:
            case R.id.btnLg:
            case R.id.btnLog2:
            case R.id.btnLogd:
            case R.id.btnSh:
            case R.id.btnCh:
            case R.id.btnTh:
            case R.id.btnCth:
            case R.id.btnAbs:
            case R.id.btnFloor:
            case R.id.btnCeil:
            case R.id.btnSign:
            case R.id.btnMax:
            case R.id.btnMin:
            case R.id.btnTodeg:
            case R.id.btnTorad:
                if (currentEdit == null)
                    break;

                Lexeme func = new Lexeme(((Button) view).getText().toString(), 0, LexemeType.FUNCTION);
                Lexeme bracket = new Lexeme("(", 0, LexemeType.OPEN_BRACKET);
                lexemesToPrint.add(func);
                lexemesToPrint.add(bracket);
                currentEdit.addLexemes(lexemesToPrint);
                lexemesToPrint.clear();
                break;

            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
            case R.id.btn0:
            case R.id.btnX:
            case R.id.btnY:
            case R.id.btnT:
            case R.id.btnPlus:
            case R.id.btnMinus:
            case R.id.btnMul:
            case R.id.btnDiv:
            case R.id.btnPow:
            case R.id.btnMod:
            case R.id.btnDot:
            case R.id.btnComma:
            case R.id.btnOpenBracket:
            case R.id.btnCloseBracket: {
                if (currentEdit == null)
                    break;

                Lexeme lexeme = new Lexeme(((Button) view).getText().toString(), 0, LexemeType.UNDEFINED);
                lexemesToPrint.add(lexeme);
                currentEdit.addLexemes(lexemesToPrint);
                lexemesToPrint.clear();
                break;
            }
            case R.id.btnPi: {
                if (currentEdit == null)
                    break;

                Lexeme lexeme = new Lexeme("π", 0, LexemeType.UNDEFINED);
                lexemesToPrint.add(lexeme);
                currentEdit.addLexemes(lexemesToPrint);
                lexemesToPrint.clear();
                break;
            }
            case R.id.btnE: {
                if (currentEdit == null)
                    break;

                Lexeme lexeme = new Lexeme("e", 0, LexemeType.UNDEFINED);
                lexemesToPrint.add(lexeme);
                currentEdit.addLexemes(lexemesToPrint);
                lexemesToPrint.clear();
                break;
            }

            case R.id.btnInf: {
                if (currentEdit == null)
                    break;

                Lexeme lexeme = new Lexeme("∞", 0, LexemeType.UNDEFINED);
                lexemesToPrint.add(lexeme);
                currentEdit.addLexemes(lexemesToPrint);
                lexemesToPrint.clear();
                break;
            }

            case R.id.btnClear:
                if (currentEdit != null)
                    currentEdit.clear();
                break;

            case R.id.btnEqual:
                onEqual();
                break;

            case R.id.btnLArrow:
                if (currentEdit != null)
                    currentEdit.setSelection(currentEdit.getSelectionStart() - 1);
                break;

            case R.id.btnRArrow:
                if (currentEdit != null)
                    currentEdit.setSelection(currentEdit.getSelectionStart() + 1);
                break;
            case R.id.btnBackspace:
                if (currentEdit != null)
                    currentEdit.backspace();
                break;


        }

    }

    protected abstract void onEqual();

    public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
        int id = view.getId();
        if (id == R.id.firstExprEditText ||
                id == R.id.secondExprEditText ||
                id == R.id.integrationEditTextUp ||
                id == R.id.integrationEditTextDown ||
                id == R.id.tEditTextFrom ||
                id == R.id.tEditTextTo) {

            currentEdit = (ExpressionEditText) view;
            currentEdit.onTouchEvent(motionEvent);

            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentEdit.getWindowToken(), 0);
            return true;
        }
        return false;
    }
}