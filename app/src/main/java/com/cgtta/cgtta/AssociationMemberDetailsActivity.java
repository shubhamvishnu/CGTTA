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
import com.cgtta.cgtta.adapters.AssociationMemberAdapter;
import com.cgtta.cgtta.adapters.PlayerMemberAdapter;
import com.cgtta.cgtta.classes.AssociationDetails;

public class AssociationMemberDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static RecyclerView associationMemberRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_member_details);
        initNavigationDrawer();
        init();
    }

    void initNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_assoc);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_assoc);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open_text, R.string.drawer_closed_text);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_assoc);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        ImageView navImageHeader = (ImageView) hView.findViewById(R.id.navigation_drawer_header);
        Glide.with(this).load(R.drawable.cgtta_logo).into(navImageHeader);
    }

    void init() {
        associationMemberRecyclerView = (RecyclerView) findViewById(R.id.am_recyclerview);
        associationMemberRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AssociationMemberDetailsActivity.this);
        associationMemberRecyclerView.setLayoutManager(linearLayoutManager);
        AssociationMemberAdapter associationMemberAdapter = new AssociationMemberAdapter(AssociationMemberDetailsActivity.this);
        associationMemberRecyclerView.setAdapter(associationMemberAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.association_members_menu: {
                intent = new Intent(AssociationMemberDetailsActivity.this, AssociationMemberDetailsActivity.class);
                startActivity(intent);
                break;

            }
            case R.id.player_details_menu: {
                intent = new Intent(AssociationMemberDetailsActivity.this, PlayerMembersActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.ranking_list_menu: {
                intent = new Intent(AssociationMemberDetailsActivity.this, RankingListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.contact_menu: {
                intent = new Intent(AssociationMemberDetailsActivity.this, Contact.class);
                startActivity(intent);
                break;
            }

        }
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout_assoc);
        drawer1.closeDrawer(GravityCompat.START);
        return false;
    }
}
