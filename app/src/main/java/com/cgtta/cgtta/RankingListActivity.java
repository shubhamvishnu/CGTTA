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
    public static RecyclerView rankingListRecyclerView;
    TextView titleTextView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<String> colHeadersList;
    String selectedYear = "2017";
    String playerCategory = "Cadet_Girls";
    List<String> rowContentList;
    List<String> colContentList;

    List<String> yearList;
    List<String> playerCategoriesList;

    String title;


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

        titleTextView = (TextView) findViewById(R.id.rl_title_text_view);
        rankingListRecyclerView = (RecyclerView) findViewById(R.id.rl_recyclerview);
        colHeadersList = new ArrayList<>();
        colContentList = new ArrayList<>();
        rowContentList = new ArrayList<>();
        yearList = new ArrayList<>();
        playerCategoriesList = new ArrayList<>();
        initContent();

    }
    void initContent(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    yearList.add(snapshot.getKey());
                    for(DataSnapshot snap: snapshot.getChildren()){
                        playerCategoriesList.add(snap.getKey());
                    }
                }
                selectedYear = yearList.get(yearList.size()-1);
                playerCategory = playerCategoriesList.get(playerCategoriesList.size() - 1);
                initLists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void initLists() {
        databaseReference.child(selectedYear + "/" + playerCategory).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title = dataSnapshot.child("title").getValue().toString();

                for (DataSnapshot snapshot : dataSnapshot.child("headers").getChildren()) {
                    colHeadersList.add(snapshot.getValue().toString());
                }
                for (DataSnapshot snapshot : dataSnapshot.child("rowContents").getChildren()) {
                    int count = -1;
                    for (DataSnapshot snapshotChild : snapshot.getChildren()) {
                        ++count;
                        if(count == colHeadersList.size()){
                            colContentList.add("NIL");
                        }else{
                            colContentList.add(colHeadersList.get(count));
                        }

                            rowContentList.add(snapshotChild.getValue().toString());

                    }
                }
                initView();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void initView() {
        titleTextView.setText(title);
        rankingListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RankingListActivity.this);
        rankingListRecyclerView.setLayoutManager(linearLayoutManager);
        RankingListAdapter rankingListAdapter = new RankingListAdapter(RankingListActivity.this, colContentList, rowContentList);
        rankingListRecyclerView.setAdapter(rankingListAdapter);
    }
}
