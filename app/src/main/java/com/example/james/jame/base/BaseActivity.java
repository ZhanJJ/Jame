package com.example.james.jame.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by James on 2016/5/8.
 */
public class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    public void showShortToast(String strText) {
        Toast.makeText(mContext, strText, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class<?> cls) {
        if (cls == null) {
            return;
        }
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromInputMethod(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
