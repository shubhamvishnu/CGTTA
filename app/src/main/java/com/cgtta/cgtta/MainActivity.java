package com.cgtta.cgtta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toPlayerMembersActivity();
    }
    void toPlayerMembersActivity(){
        Intent intent = new Intent(MainActivity.this, RankingListActivity.class);
        startActivity(intent);
    }
}
