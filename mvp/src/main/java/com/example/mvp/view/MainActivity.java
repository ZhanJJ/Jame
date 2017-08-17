package com.example.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mvp.R;
import com.example.mvp.presenter.IUserPresenter;
import com.example.mvp.presenter.UserPresenter;

public class MainActivity extends AppCompatActivity implements IUserView, View.OnClickListener {

    private EditText edt_first;
    private EditText edt_last;
    private EditText edt_id;
    private Button btn_save;
    private Button btn_load;

    private IUserPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //获取p层的接口实例，并且传入此v层,为了调用p层里的实现业务逻辑的方法
        presenter = new UserPresenter(this);
    }

    @Override
    public int getId() {
        return Integer.valueOf(edt_id.getText().toString());
    }

    @Override
    public String getFirstName() {
        return edt_first.getText().toString();
    }

    @Override
    public String getLastName() {
        return edt_last.getText().toString();
    }

    @Override
    public void setFirstName(String firstName) {
        edt_first.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        edt_last.setText(lastName);
    }

    private void initView() {
        edt_first = (EditText) findViewById(R.id.edt_first);
        edt_last = (EditText) findViewById(R.id.edt_last);
        edt_id = (EditText) findViewById(R.id.edt_id);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_load = (Button) findViewById(R.id.btn_load);

        btn_save.setOnClickListener(this);
        btn_load.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //存储数据只需要和p层打交道，太神奇了！
            case R.id.btn_save:
                presenter.saveUser();
                break;
            case R.id.btn_load:
                presenter.loadUser(getId());
                break;
        }
    }
}
