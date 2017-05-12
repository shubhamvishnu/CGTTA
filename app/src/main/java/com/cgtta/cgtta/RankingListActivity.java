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

import java.util.HashMap;
import java.util.Map;

public class RankingListActivity extends AppCompatActivity {
    TableLayout rankingListTable;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Map<Integer, DataSnapshot> yearListSnapShot;
    int selectedYear = 2017;
    String playerCategory = "Cadet_Girls";

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
        yearListSnapShot = new HashMap<>();
        initLists();

    }

    void initLists() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                yearListSnapShot.put(Integer.parseInt(dataSnapshot.getKey()), dataSnapshot.child(dataSnapshot.getKey()));
                showContent();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void showContent() {

        DataSnapshot datasnapshot = yearListSnapShot.get(selectedYear).child(playerCategory);


        String title = datasnapshot.child("title").getValue().toString();
        int noOfHeadersCount = (int) datasnapshot.child("headers").getChildrenCount();
        DataSnapshot headerSnapshot = datasnapshot.child("headers");
        TableRow headerRow = new TableRow(this);
        headerRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < noOfHeadersCount; i++) {
            TextView headerRowValue = new TextView(this);
            headerRowValue.setText(headerSnapshot.child("" + i).getValue().toString());
            headerRowValue.setTextColor(Color.BLACK);
            headerRowValue.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            headerRowValue.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            headerRowValue.setPadding(4, 0, 0, 0);
            headerRow.addView(headerRow);
        }
        rankingListTable.addView(headerRow);


    }

}
