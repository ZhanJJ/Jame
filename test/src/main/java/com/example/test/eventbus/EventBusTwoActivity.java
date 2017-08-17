package com.example.test.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.test.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EventBusTwoActivity extends AppCompatActivity {

    @BindView(R.id.tvReceiver)
    TextView tvReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_two);
        ButterKnife.bind(this);

        EventBus.getDefault().register(EventBusTwoActivity.this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(EventBusTwoActivity.this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMsg eventBusMsg) {
        Log.i("EventBus--->", "收到MSG!");
        if (eventBusMsg != null) {
            tvReceiver.setText(eventBusMsg.name);
        }
    }

    @OnClick(R.id.tvReceiver)
    public void onClick() {
        startActivity(new Intent(this, EventBusOneActivity.class));
    }
}
