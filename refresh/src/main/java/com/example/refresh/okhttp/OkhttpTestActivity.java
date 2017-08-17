package com.example.refresh.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.refresh.R;

import java.io.IOException;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpTestActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "OkHttp";
    private Button button;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_test);
        initView();
        ButterKnife.bind(this);
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                RequestBody requestBody = new FormBody.Builder()
                        .add("type", "8003")
                        .add("phone", "13790303985")
                        .add("password", "e10adc3949ba59abbe56e057f20f883e")
                        .build();

                try {
                    CommonOkHttp.getInstance().post(requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i(TAG, e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.i(TAG, response.body().string());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button2:

                break;
            case R.id.button3:

                break;
        }
    }
}
