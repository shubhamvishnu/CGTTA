package com.cgtta.cgtta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cgtta.cgtta.adapters.NewBulletinAdapter;

public class NewsBulletinActivity extends AppCompatActivity {
    public static RecyclerView newsBulletinArticleRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_bulletin);
        init();
    }
    void init() {
        newsBulletinArticleRecyclerView = (RecyclerView) findViewById(R.id.nb_recyclerview);
        newsBulletinArticleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewsBulletinActivity.this);
        newsBulletinArticleRecyclerView.setLayoutManager(linearLayoutManager);
        NewBulletinAdapter newBulletinAdapter = new NewBulletinAdapter(NewsBulletinActivity.this);
        newsBulletinArticleRecyclerView.setAdapter(newBulletinAdapter);
    }
}
