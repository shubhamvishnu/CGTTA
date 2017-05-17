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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.adapters.PlayerMemberAdapter;

public class PlayerMembersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
        public static RecyclerView playerRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_members);
        initNavigationDrawer();
        init();
    }
    void initNavigationDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_player);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_player);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open_text, R.string.drawer_closed_text);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_player);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        ImageView navImageHeader = (ImageView) hView.findViewById(R.id.navigation_drawer_header);
        Glide.with(this).load(R.drawable.cgtta_logo).into(navImageHeader);
    }
    void init(){
        playerRecyclerView = (RecyclerView) findViewById(R.id.p_recyclerview);
        playerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PlayerMembersActivity.this);
        playerRecyclerView.setLayoutManager(linearLayoutManager);
        PlayerMemberAdapter playerMemberAdapter = new PlayerMemberAdapter(PlayerMembersActivity.this);
        playerRecyclerView.setAdapter(playerMemberAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.association_members_menu: {
                intent = new Intent(PlayerMembersActivity.this, AssociationMemberDetailsActivity.class);
                startActivity(intent);
                break;

            }
            case R.id.player_details_menu: {
                intent = new Intent(PlayerMembersActivity.this, PlayerMembersActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.ranking_list_menu: {
                intent = new Intent(PlayerMembersActivity.this, RankingListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.contact_menu: {
                intent = new Intent(PlayerMembersActivity.this, Contact.class);
                startActivity(intent);
                break;
            }


        }
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout_player);
        drawer1.closeDrawer(GravityCompat.START);
        return false;
    }
}
