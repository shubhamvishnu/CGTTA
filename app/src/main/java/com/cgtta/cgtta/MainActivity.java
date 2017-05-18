package com.cgtta.cgtta;

import android.content.Intent;
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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.adapters.NewBulletinAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static RecyclerView newsBulletinArticleRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_bulletin);
        initNavigationDrawer();
        init();
    }

    void initNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("News Bulletin");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open_text, R.string.drawer_closed_text);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        ImageView navImageHeader = (ImageView) hView.findViewById(R.id.navigation_drawer_header);
        Glide.with(this).load(R.drawable.cgtta_logo).into(navImageHeader);
    }

    void init() {
        newsBulletinArticleRecyclerView = (RecyclerView) findViewById(R.id.nb_recyclerview);
        newsBulletinArticleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        newsBulletinArticleRecyclerView.setLayoutManager(linearLayoutManager);
        NewBulletinAdapter newBulletinAdapter = new NewBulletinAdapter(MainActivity.this, linearLayoutManager);
        newsBulletinArticleRecyclerView.setAdapter(newBulletinAdapter);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.association_members_menu: {
                intent = new Intent(MainActivity.this, AssociationMemberActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            }
            case R.id.player_details_menu: {
                intent = new Intent(MainActivity.this, PlayerMembersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.ranking_list_menu: {
                intent = new Intent(MainActivity.this, RankingListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.contact_menu: {
                intent = new Intent(MainActivity.this, Contact.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.news_menu: {
                intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }




        }
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawer1.closeDrawer(GravityCompat.START);
        return false;
    }

}