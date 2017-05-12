package com.cgtta.cgtta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cgtta.cgtta.adapters.AssociationMemberAdapter;
import com.cgtta.cgtta.adapters.PlayerMemberAdapter;
import com.cgtta.cgtta.classes.AssociationDetails;

public class AssociationMemberDetailsActivity extends AppCompatActivity {
    public static RecyclerView associationMemberRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_member_details);
        init();
    }
    void init(){
        associationMemberRecyclerView = (RecyclerView) findViewById(R.id.am_recyclerview);
        associationMemberRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AssociationMemberDetailsActivity.this);
        associationMemberRecyclerView.setLayoutManager(linearLayoutManager);
        AssociationMemberAdapter associationMemberAdapter = new AssociationMemberAdapter(AssociationMemberDetailsActivity.this);
        associationMemberRecyclerView.setAdapter(associationMemberAdapter);
    }
}
