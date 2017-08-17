package com.example.test.activity;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.R;

import java.util.List;

public class NetworkSettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("设置操作");
            // 将该按钮添加到该界面上
            setListFooter(button);
        }
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        System.out.println(fragmentName);
        return true;
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.headers, target);
        // 加载选项设置列表的布局文件
    }

    public static class FragmentOne extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

            String name = ((EditTextPreference) findPreference("name")).getText().trim();
            Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
        }
    }


    public static class FragmentTwo extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference2);
        }
    }
}
