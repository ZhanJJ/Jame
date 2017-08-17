package com.example.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kin on 2017/2/23.
 */

public class SoftKeyboardFragment extends Fragment {
    View mView;
    @BindView(R.id.btn1)
    Button btn1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_soft_keyboard, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }
}
