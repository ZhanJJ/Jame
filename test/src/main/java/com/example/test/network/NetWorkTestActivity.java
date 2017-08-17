package com.example.test.network;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NetWorkTestActivity extends AppCompatActivity {
    boolean isActivate;
    NetworkReceiver mNetworkReceiver;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_test);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar)
                findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (isActivate) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
                isActivate = !isActivate;
            }
        });
    }

    @Override
    protected void onResume() {
        registerReceiver();
        super.onResume();
    }

    private void registerReceiver() {
        mNetworkReceiver = new NetworkReceiver();
        this.registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mNetworkReceiver);
        super.onDestroy();
    }

    @OnClick(R.id.progressBar)
    public void onClick() {
        if (isActivate) {
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        isActivate = !isActivate;
    }
}
