package com.sgcc.pda.jszp.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * author:赵锦
 * date:2018/8/20 0020 15:10
 */
public class JszpUtils {
    /**
     * 打开软键盘
     * @param context
     */
    public static void openInputSoft(Context context, EditText editText){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);// 显示输入法
    }

    /**
     * 打开软键盘
     * @param context
     */
    public static void closeInputSoft(EditText mEditText,Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
