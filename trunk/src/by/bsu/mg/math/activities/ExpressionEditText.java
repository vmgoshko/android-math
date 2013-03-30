package by.bsu.mg.math.activities;

import android.content.Context;
import android.widget.EditText;
import by.bsu.mg.math.parsing.lexemes.Lexeme;
import by.bsu.mg.math.parsing.lexemes.LexemeType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExpressionEditText extends EditText {
    private StringBuilder sb;
    private List<Integer> lexemeBeginList;

    public ExpressionEditText(Context context) {
        super(context);
        init();
    }

    public ExpressionEditText(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public ExpressionEditText(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        sb = new StringBuilder();
        lexemeBeginList = new ArrayList<Integer>();
    }

    public boolean onTouchEvent(android.view.MotionEvent event) {

        super.onTouchEvent(event);
        int pos = getSelectionStart();

        if (pos == lexemeBeginList.size()) {
            return true;
        }

        setSelection(pos - lexemeBeginList.get(pos));
        return true;
    }

    public void addLexemes(List<Lexeme> lexemeList) {
        int pos = getSelectionStart();
        for (int i = 0; i < lexemeList.size(); i++) {
            sb.insert(pos, lexemeList.get(i).getValue());

            for (int j = 0; j < lexemeList.get(i).getValue().length(); j++) {
                if (lexemeList.get(i).getType() == LexemeType.FUNCTION)
                    lexemeBeginList.add(pos, j);
                else
                    lexemeBeginList.add(pos, 0);

                pos++;
            }
        }
        setText(sb.toString());
        setSelection(pos);
    }

    public void backspace() {
        int pos = getSelectionStart();
        int delCount = 0;

        if (pos > 0) {
            delCount = lexemeBeginList.get(pos - 1) + 1;
            sb.delete(pos - delCount, pos);
            for (int i = 0; i < delCount; i++)
                lexemeBeginList.remove(pos - delCount);
        }

        setText(sb.toString());
        setSelection(pos - delCount);
    }

    public void clear() {
        if (lexemeBeginList.size() > 0) {
            lexemeBeginList.clear();
        }

        if (sb.length() > 0) {
            sb.delete(0, sb.length());
        }
        setText("");
        setSelection(0);
    }


    @Override
    protected boolean getDefaultEditable() {
        return true;
    }

    @Override
    protected android.text.method.MovementMethod getDefaultMovementMethod() {
        return super.getDefaultMovementMethod();
    }

    @Override
    public android.text.Editable getText() {
        return super.getText();
    }

    @Override
    public void setText(java.lang.CharSequence text, android.widget.TextView.BufferType type) {
        super.setText(text, type);
    }

    @Override
    public void setSelection(int start, int stop) {
        super.setSelection(start, stop);
    }

    @Override
    public void setSelection(int index) {
        if(index < 0 || index > this.length() ){
            return;
        }

        super.setSelection(index);
    }

    @Override
    public void selectAll() {
        super.selectAll();

    }

    @Override
    public void extendSelection(int index) {
        super.extendSelection(index);
    }

    @Override
    public void setEllipsize(android.text.TextUtils.TruncateAt ellipsis) {
        super.setEllipsize(ellipsis);
    }


}