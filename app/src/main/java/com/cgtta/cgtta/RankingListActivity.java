package com.cgtta.cgtta;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class RankingListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static RecyclerView rankingListRecyclerView;
    TextView titleTextView;
    Spinner playerSpinner, yearSpinner;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<String> colHeadersList;
    String selectedYear;
    String playerCategory;
    List<String> rowContentList;
    List<String> colContentList;

    List<String> yearList;
    List<String> playerCategoriesList;

    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_list);
        initNavigationDrawer();
        init();

    }

    void initNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rank);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_rank);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open_text, R.string.drawer_closed_text);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_rank);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        ImageView navImageHeader = (ImageView) hView.findViewById(R.id.navigation_drawer_header);
        Glide.with(this).load(R.drawable.cgtta_logo).into(navImageHeader);
    }

    void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(FirebaseReferences.FIREBASE_RANKING_LIST);
        databaseReference.keepSynced(true);


        playerSpinner = (Spinner) findViewById(R.id.rl_player_category_spinner);
        yearSpinner = (Spinner) findViewById(R.id.rl_year_spinner);

        playerSpinner.setVisibility(View.GONE);
        yearSpinner.setVisibility(View.GONE);


        titleTextView = (TextView) findViewById(R.id.rl_title_text_view);
        rankingListRecyclerView = (RecyclerView) findViewById(R.id.rl_recyclerview);
        colHeadersList = new ArrayList<>();
        colContentList = new ArrayList<>();
        rowContentList = new ArrayList<>();
        yearList = new ArrayList<>();
        playerCategoriesList = new ArrayList<>();
        initContent();

    }

    void initContent() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    yearList.add(snapshot.getKey());
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        playerCategoriesList.add(snap.getKey());
                    }
                }
                initSpinners();
                selectedYear = yearList.get(yearList.size() - 1);
                playerCategory = playerCategoriesList.get(playerCategoriesList.size() - 1);
                initLists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void initSpinners() {

        playerSpinner.setVisibility(View.VISIBLE);
        yearSpinner.setVisibility(View.VISIBLE);

        ArrayAdapter<String> playerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, playerCategoriesList);
        playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerSpinner.setAdapter(playerAdapter);
        playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                playerCategory = parent.getItemAtPosition(position).toString();
                initLists();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = parent.getItemAtPosition(position).toString();
                initLists();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                        if (count == colHeadersList.size()) {
                            colContentList.add("NIL");
                        } else {
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.association_members_menu: {
                intent = new Intent(RankingListActivity.this, AssociationMemberDetailsActivity.class);
                startActivity(intent);
                break;

            }
            case R.id.player_details_menu: {
                intent = new Intent(RankingListActivity.this, PlayerMembersActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.ranking_list_menu: {
                intent = new Intent(RankingListActivity.this, RankingListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.contact_menu: {
                intent = new Intent(RankingListActivity.this, Contact.class);
                startActivity(intent);
                break;
            }


        }
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout_rank);
        drawer1.closeDrawer(GravityCompat.START);
        return false;
    }
}
