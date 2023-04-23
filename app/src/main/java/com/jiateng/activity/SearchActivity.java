package com.jiateng.activity;

import static com.jiateng.utils.ToastUtil.ToastShow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.adapter.SeachRecordAdapter;
import com.jiateng.adapter.SearchRecycleAdapter;
import com.jiateng.db.impl.RecordDaoImpl;

public class SearchActivity extends Activity {

    private Button searchButton;
    private EditText searchBar;
    private RecyclerView historyListView;
    private TextView deleteTextView;
    private SeachRecordAdapter seachRecordAdapter;
    private RecordDaoImpl recordDao;
    private ImageView quite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recordDao = RecordDaoImpl.getInstance(SearchActivity.this);
        initViews();
    }

    private void initViews() {
        recordDao.queryAll();
        quite = findViewById(R.id.quitePage);
        searchButton = findViewById(R.id.btn_serarch);
        searchBar = findViewById(R.id.search);
        deleteTextView = findViewById(R.id.deleteAll);
        deleteTextView.setOnClickListener(view -> {
            recordDao.deleteData();
            seachRecordAdapter.updata(recordDao.queryAll());
        });
        historyListView = findViewById(R.id.searchRecyclerView);
        historyListView.setLayoutManager(new LinearLayoutManager(this));
        seachRecordAdapter = new SeachRecordAdapter(recordDao.queryAll(), this);
        seachRecordAdapter.setRvItemOnclickListener(new SearchRecycleAdapter.RvItemOnclickListener() {
            @Override
            public void removeItemOnclick(int position) {
                recordDao.delete(recordDao.queryAll().get(position));
                seachRecordAdapter.updata(recordDao.queryAll());
            }

            @Override
            public void intoHistoryPage(int position) {
                String name = recordDao.queryAll().get(position);
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("searchName", name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        historyListView.setAdapter(seachRecordAdapter);
        //事件监听
        searchButton.setOnClickListener(view -> {
            if (searchBar.getText().toString().trim().length() != 0) {
                recordDao.insertData(searchBar.getText().toString().trim());
                seachRecordAdapter.updata(recordDao.queryAll());
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("searchName", searchBar.getText().toString().trim());
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                ToastShow("请输入内容");
            }
        });
        quite.setOnClickListener(v -> finish());
    }
}