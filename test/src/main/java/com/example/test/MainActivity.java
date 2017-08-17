package com.example.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.view.CheckableRelativeLayout;
import com.example.test.view.ChildView;
import com.example.test.view.ChildView2;
import com.example.test.view.CustomImgView;
import com.example.test.view.ViewGroupView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;

public class MainActivity extends Activity {

    ListView mListViewDirectory;
    ListView mListViewContent;

    CommonListAdapter mDirectoryAdapter;
    ClassifyListAdapter mContentAdapter;
    List<String> mDirectoryList;
    List<ClassifyInfo> mContentList1, mContentList2, mContentList3, mContentList4;

    String currentParent = "";
    private ChildView childView1;
    private ChildView2 childView2;
    private ViewGroupView viewGroup;
    Button btn3;
    ImageView imgConner;
    CustomImgView imgCustom;
    private int i = 0;
    private CheckableRelativeLayout checkRL;
    private TextView tvContent;
    private TextView tvCountDown;
    private EditText edtPhone;
    private TextInputLayout tilPhone;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        init();
        Binder binder = new Binder();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {
        mListViewDirectory = (ListView) findViewById(R.id.listViewDirectory);
        mListViewContent = (ListView) findViewById(R.id.listViewContent);
        this.childView2 = (ChildView2) findViewById(R.id.childView2);
        this.childView1 = (ChildView) findViewById(R.id.childView1);
        this.viewGroup = (ViewGroupView) findViewById(R.id.viewGroup);
        this.btn3 = (Button) findViewById(R.id.btn3);

        childView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "onClick execute time +" + i++ + " " + v.getId());
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                String strUri = "http://app2.pgyer.com/bde6a1165bc765fa4ed75ce337db546e.apk?v=1.1&sign=bf16b72488ea391cb60e7caa1ac7a418&t=578de81a&attname=yiQu_Api-baidu-release.apk";
                String strUri = "http://d.koudai.com/com.koudai.weishop/1000f/weishop_1000f.apk";

                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.putExtra("strDownload", strUri);
                startService(intent);
            }
        });


        childView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TAG", "ChildView onTouch execute, action " + event.getAction());
                return false;
            }
        });


        viewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TAG", "ViewGroup onTouch execute, action " + event.getAction());
                return false;
            }
        });

        mDirectoryAdapter = new CommonListAdapter(this);
        mContentAdapter = new ClassifyListAdapter(this);

        mDirectoryList = new ArrayList<>();
        mContentList1 = new ArrayList<>();
        mContentList2 = new ArrayList<>();
        mContentList3 = new ArrayList<>();
        mContentList4 = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            mDirectoryList.add("目录" + i);
        }

        for (int i = 0; i < 8; i++) {
            ClassifyInfo classifyInfo = new ClassifyInfo();
            classifyInfo.parent = "A";
            classifyInfo.name = "商品A" + i;
            mContentList1.add(classifyInfo);

            ClassifyInfo classifyInfo2 = new ClassifyInfo();
            classifyInfo2.parent = "B";
            classifyInfo2.name = "商品B" + i;
            mContentList2.add(classifyInfo2);

            ClassifyInfo classifyInfo3 = new ClassifyInfo();
            classifyInfo3.parent = "C";
            classifyInfo3.name = "商品C" + i;
            mContentList3.add(classifyInfo3);

            ClassifyInfo classifyInfo4 = new ClassifyInfo();
            classifyInfo4.parent = "D";
            classifyInfo4.name = "商品D" + i;
            mContentList4.add(classifyInfo4);
        }

        mDirectoryAdapter.addAll(mDirectoryList);
        mListViewDirectory.setAdapter(mDirectoryAdapter);

        mContentAdapter.add(mContentList1);
        mContentAdapter.add(mContentList2);
        mContentAdapter.add(mContentList3);
        mContentAdapter.add(mContentList4);
        mListViewContent.setAdapter(mContentAdapter);

        mListViewDirectory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mListViewDirectory.smoothScrollToPositionFromTop(position,0);
                switch (position) {
                    case 0: {
//                        mContentAdapter.addAll(mContentList1);
                        mListViewDirectory.smoothScrollToPositionFromTop(0, 0);
                        break;
                    }
                    case 1: {
//                        mContentAdapter.addAll(mContentList2);
                        mListViewContent.smoothScrollToPositionFromTop(mContentList1.size(), 0);
                        break;
                    }
                    case 2: {
//                        mContentAdapter.addAll(mContentList3);
                        mListViewContent.smoothScrollToPositionFromTop(mContentList2.size(), 0);
                        break;
                    }
                    case 3: {
//                        mContentAdapter.addAll(mContentList4);
                        mListViewContent.smoothScrollToPositionFromTop(mContentList3.size(), 0);
                        break;
                    }
                }
            }
        });

        mListViewContent.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int position = mListViewContent.getFirstVisiblePosition();
                ClassifyInfo classifyInfo = (ClassifyInfo) mContentAdapter.getItem(position);
                if (currentParent.equals(classifyInfo.parent)) {
                    return;
                }
                currentParent = classifyInfo.parent;
                switch (classifyInfo.parent) {
                    case "A": {
                        mListViewDirectory.setSelection(0);
                        mDirectoryAdapter.setSelectedItem(0);
                        break;
                    }
                    case "B": {
                        mListViewDirectory.setSelection(1);
                        mDirectoryAdapter.setSelectedItem(1);
                        break;
                    }
                    case "C": {
                        mListViewDirectory.setSelection(2);
                        mDirectoryAdapter.setSelectedItem(2);
                        break;
                    }
                    case "D": {
                        mListViewDirectory.setSelection(3);
                        mDirectoryAdapter.setSelectedItem(3);
                        break;
                    }
                }
            }
        });
        checkRL = (CheckableRelativeLayout) findViewById(R.id.checkRL);
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent.setText(Html.fromHtml("<u>" + "12345" + "</u>"));

        tvCountDown = (TextView) findViewById(R.id.tvCountDown);
        tvCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDown(tvCountDown);
            }
        });
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        tilPhone = (TextInputLayout) findViewById(R.id.tilPhone);
    }

    private void init() {
        checkRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRL.setChecked(!checkRL.isChecked());
            }
        });
        checkRL.setOnCheckedChangeListener(new CheckableRelativeLayout.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RelativeLayout relativeLayout, boolean isChecked) {
                if (isChecked) {
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                } else {
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
                }
            }
        });
        initEdit();
    }

    private int compensateTime(int time) {
        if (time == 0) {
            return 0;
        }
        return (time + (time * 2) / 1000);
    }

    private void countDown(final TextView textView) {
        new CountDownTimer(compensateTime(10000), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("还有：" + millisUntilFinished / 1000 + "s");
                Log.i("millisUntilFinished-->", millisUntilFinished + "");
            }

            @Override
            public void onFinish() {
                textView.setText("计时结束");
            }
        }.start();
    }

    private void initEdit() {
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isPhoneNum(s.toString())) {
                    tilPhone.setErrorEnabled(true);
                    tilPhone.setError("请检查手机号格式");
                } else {
                    tilPhone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 是否电话号码
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNum(String phone) {
        return isRegexMatches("0?1[3|4|5|7|8][0-9]{9}", phone);
    }

    /**
     * 是否匹配正则表达式
     *
     * @param strRegex 正则表达式规则
     * @param strText  匹配字符串
     * @return
     */
    public static boolean isRegexMatches(String strRegex, String strText) {
        if (TextUtils.isEmpty(strRegex) || TextUtils.isEmpty(strText)) {
            return false;
        }
        Pattern r = Pattern.compile(strRegex);
        Matcher m = r.matcher(strText);

        return m.find();
    }

    private void submit() {
        // validate
        String edtPhoneString = edtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(edtPhoneString)) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
