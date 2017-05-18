package com.cgtta.cgtta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.adapters.RankingListAdapter;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.viewholders.RankingListPlayerCategoryViewHolder;
import com.cgtta.cgtta.viewholders.RankingListYearViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RankingListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static RecyclerView rankingListRecyclerView;
    TextView titleTextView;
    public static RecyclerView rankingListYearsRecyclerView, rankingListPlayerCategoryRecyclerView;

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
        toolbar.setTitle("Ranking List");
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

        titleTextView = (TextView) findViewById(R.id.rl_title_text_view);
        titleTextView.setPaintFlags(titleTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        rankingListRecyclerView = (RecyclerView) findViewById(R.id.rl_recyclerview);
        rankingListYearsRecyclerView = (RecyclerView) findViewById(R.id.ranking_list_years_recyclerview);
        rankingListPlayerCategoryRecyclerView = (RecyclerView) findViewById(R.id.ranking_list_player_category_recyclerview);

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
                initYears();
                initPlayerCategories();
                selectedYear = yearList.get(yearList.size() - 1);
                playerCategory = playerCategoriesList.get(playerCategoriesList.size() - 1);
                initLists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void initPlayerCategories() {
        rankingListPlayerCategoryRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rankingListPlayerCategoryRecyclerView.setLayoutManager(linearLayoutManager);
        RankingListPlayerCategoryAdapter rankingListAdapter = new RankingListPlayerCategoryAdapter(RankingListActivity.this, playerCategoriesList);
        rankingListPlayerCategoryRecyclerView.setAdapter(rankingListAdapter);
    }

    void initYears() {
        rankingListYearsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rankingListYearsRecyclerView.setLayoutManager(linearLayoutManager);
        RankingListYearsAdapter rankingListAdapter = new RankingListYearsAdapter(RankingListActivity.this, yearList);
        rankingListYearsRecyclerView.setAdapter(rankingListAdapter);
    }


    void initLists() {
        colHeadersList = new ArrayList<>();
        colContentList = new ArrayList<>();
        rowContentList = new ArrayList<>();

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
                intent = new Intent(RankingListActivity.this, AssociationMemberActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            }
            case R.id.player_details_menu: {
                intent = new Intent(RankingListActivity.this, PlayerMembersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.ranking_list_menu: {
                intent = new Intent(RankingListActivity.this, RankingListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.contact_menu: {
                intent = new Intent(RankingListActivity.this, Contact.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.news_menu: {
                intent = new Intent(RankingListActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }


        }
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout_rank);
        drawer1.closeDrawer(GravityCompat.START);
        return false;
    }


    public class RankingListYearsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
        Context context;
        List<String> yearsList;
        private LayoutInflater inflator;

        public RankingListYearsAdapter(Context context, List<String> yearsList) {
            this.context = context;
            this.inflator = LayoutInflater.from(context);
            this.yearsList = yearsList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflator.inflate(R.layout.recyclerview_ranking_list_year_row_layout, parent, false);
            view.setOnClickListener(this);
            RankingListYearViewHolder viewHolder = new RankingListYearViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((RankingListYearViewHolder) holder).yearTextView.setText(yearsList.get(position));

        }

        @Override
        public int getItemCount() {
            return yearsList.size();
        }

        @Override
        public void onClick(View v) {
            int itemPosition = RankingListActivity.rankingListYearsRecyclerView.getChildLayoutPosition(v);
            selectedYear = yearList.get(itemPosition);
            initLists();
        }
    }

    public class RankingListPlayerCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
        Context context;
        List<String> playerList;
        private LayoutInflater inflator;

        public RankingListPlayerCategoryAdapter(Context context, List<String> playerList) {
            this.context = context;
            this.inflator = LayoutInflater.from(context);
            this.playerList = playerList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflator.inflate(R.layout.recyclerview_ranking_list_player_categories_row_layout, parent, false);
            view.setOnClickListener(this);
            RankingListPlayerCategoryViewHolder viewHolder = new RankingListPlayerCategoryViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((RankingListPlayerCategoryViewHolder) holder).playerTextView.setText(playerList.get(position));

        }

        @Override
        public int getItemCount() {
            return playerList.size();
        }

        @Override
        public void onClick(View v) {
            int itemPosition = RankingListActivity.rankingListPlayerCategoryRecyclerView.getChildLayoutPosition(v);
            playerCategory = playerList.get(itemPosition);
            initLists();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RankingListActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
