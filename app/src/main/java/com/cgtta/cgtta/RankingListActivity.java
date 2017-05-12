package com.cgtta.cgtta;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    TableLayout rankingListTable;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<String> colHeadersList;
    String selectedYear = "2017";
    String playerCategory = "Cadet_Girls";
    List<String> rowContentList;
    List<List<String>> rowsList;


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

        rankingListTable = (TableLayout) findViewById(R.id.ranking_list_table_layout);

        colHeadersList = new ArrayList<>();
        rowContentList = new ArrayList<>();
        rowsList = new ArrayList<>();
        initLists();

    }

    void initLists() {
      databaseReference.child(selectedYear+"/"+playerCategory).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              String title = dataSnapshot.child("title").getValue().toString();

              for (DataSnapshot snapshot : dataSnapshot.child("headers").getChildren()) {
                  colHeadersList.add(snapshot.getValue().toString());
              }

              for(DataSnapshot snapshot : dataSnapshot.child("rowContents").getChildren()){
                for(DataSnapshot snapshotChild : snapshot.getChildren()){
                    rowContentList.add(snapshotChild.getValue().toString());
                }
                rowsList.add(rowContentList);
              }
              Toast.makeText(RankingListActivity.this, rowsList.toString(), Toast.LENGTH_SHORT).show();


          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });
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
