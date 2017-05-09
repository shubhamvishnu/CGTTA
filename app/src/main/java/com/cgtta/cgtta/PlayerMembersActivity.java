package com.cgtta.cgtta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cgtta.cgtta.adapters.PlayerMemberAdapter;

public class PlayerMembersActivity extends AppCompatActivity {
    RecyclerView playerRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_members);
        init();
    }
    void init(){
        playerRecyclerView = (RecyclerView) findViewById(R.id.p_recyclerview);
        playerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PlayerMembersActivity.this);
        playerRecyclerView.setLayoutManager(linearLayoutManager);
        PlayerMemberAdapter playerMemberAdapter = new PlayerMemberAdapter(PlayerMembersActivity.this);
        playerRecyclerView.setAdapter(playerMemberAdapter);
    }
}
