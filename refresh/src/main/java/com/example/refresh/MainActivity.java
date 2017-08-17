package com.example.refresh;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.refresh.adapter.ContactPersonAdapter;
import com.example.refresh.divider.CustomItemDecoration;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private ContactPersonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(this);
    }

    private void init() {
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llManager);

        String[] names = getResources().getStringArray(R.array.listpersons);
        List<String> nameList = Arrays.asList(names);
        mAdapter = new ContactPersonAdapter(this, nameList);
        recyclerView.setAdapter(mAdapter);

        View headerView = LayoutInflater.from(this).inflate(R.layout.item_contact_header, null);
        View footerView = LayoutInflater.from(this).inflate(R.layout.item_contact_footer, null);
        mAdapter.setHeaderView(headerView);
        mAdapter.setFooterView(footerView);

        //添加自定义分割线
        recyclerView.addItemDecoration(new CustomItemDecoration(this, CustomItemDecoration.ORIENTATION_HORIZONTAL));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }
}
