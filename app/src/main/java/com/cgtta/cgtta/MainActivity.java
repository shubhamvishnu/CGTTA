package com.cgtta.cgtta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cgtta.cgtta.adapters.NewBulletinAdapter;

public class MainActivity extends AppCompatActivity {
    public static RecyclerView newsBulletinArticleRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_bulletin);

        Intent intent = new Intent(MainActivity.this, Contact.class);
        startActivity(intent);
    }

    void init() {
        newsBulletinArticleRecyclerView = (RecyclerView) findViewById(R.id.nb_recyclerview);
        newsBulletinArticleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        newsBulletinArticleRecyclerView.setLayoutManager(linearLayoutManager);
        NewBulletinAdapter newBulletinAdapter = new NewBulletinAdapter(MainActivity.this);
        newsBulletinArticleRecyclerView.setAdapter(newBulletinAdapter);
    }
}