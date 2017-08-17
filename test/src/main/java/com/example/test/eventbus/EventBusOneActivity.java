package com.example.test.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.test.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EventBusOneActivity extends AppCompatActivity {

    @BindView(R.id.btnOne)
    Button btnOne;
    @BindView(R.id.btnTwo)
    Button btnTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_one);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnOne, R.id.btnTwo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOne:
                EventBus.getDefault().post(new EventBusMsg("已发送数据!"));
                finish();
                break;
            case R.id.btnTwo:
                break;
        }
    }
}
