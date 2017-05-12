package com.cgtta.cgtta;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtta.cgtta.adapters.PlayerMemberAdapter;
import com.cgtta.cgtta.adapters.RankingListAdapter;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingListActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<String> colHeadersList;
    String selectedYear = "2017";
    String playerCategory = "Cadet_Girls";
    List<String> rowContentList;
    List<String> colContentList;
    public static RecyclerView rankingListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_list);
        init();

    }

    void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(FirebaseReferences.FIREBASE_RANKING_LIST);
        databaseReference.keepSynced(true);


        rankingListRecyclerView = (RecyclerView) findViewById(R.id.rl_recyclerview);
        colHeadersList = new ArrayList<>();
        colContentList = new ArrayList<>();
        rowContentList = new ArrayList<>();
        initLists();

    }

    void initLists() {
        databaseReference.child(selectedYear + "/" + playerCategory).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title = dataSnapshot.child("title").getValue().toString();

                for (DataSnapshot snapshot : dataSnapshot.child("headers").getChildren()) {
                    colHeadersList.add(snapshot.getValue().toString());
                }

                for (DataSnapshot snapshot : dataSnapshot.child("rowContents").getChildren()) {
                    int count = -1;
                    for (DataSnapshot snapshotChild : snapshot.getChildren()) {
                        ++count;
                        colContentList.add(colHeadersList.get(count));
                        rowContentList.add(snapshotChild.getValue().toString());
                    }
                }
                initRecyclerView();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void initRecyclerView() {
        rankingListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RankingListActivity.this);
        rankingListRecyclerView.setLayoutManager(linearLayoutManager);
        RankingListAdapter rankingListAdapter = new RankingListAdapter(RankingListActivity.this, colContentList, rowContentList);
        rankingListRecyclerView.setAdapter(rankingListAdapter);
    }
/*
    void showContent() {
        String title = dataSnapshot.child("title").getValue().toString();
        int noOfHeadersCount = (int) dataSnapshot.child("headers").getChildrenCount();
        DataSnapshot headerSnapshot = dataSnapshot.child("headers");
        TableRow headerRow = new TableRow(RankingListActivity.this);
        headerRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < noOfHeadersCount; i++) {
            TextView headerRowValue = new TextView(RankingListActivity.this);
            headerRowValue.setText(headerSnapshot.child("" + i).getValue().toString());
            headerRowValue.setTextColor(Color.BLACK);
            headerRowValue.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            headerRowValue.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            headerRowValue.setPadding(4, 0, 0, 0);
            headerRow.addView(headerRow);
        }
        rankingListTable.addView(headerRow);




    }
    */

}
