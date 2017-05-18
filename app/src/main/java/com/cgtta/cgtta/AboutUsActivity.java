package com.cgtta.cgtta;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AboutUsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView versionTextView, developerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abous_us);
        initNavigationDrawer();
        init();
    }

    void initNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_au);
        toolbar.setTitle("About Us");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_au);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open_text, R.string.drawer_closed_text);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_au);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        ImageView navImageHeader = (ImageView) hView.findViewById(R.id.navigation_drawer_header);
        Glide.with(this).load(R.drawable.cgtta_logo).into(navImageHeader);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.association_members_menu: {
                intent = new Intent(AboutUsActivity.this, AssociationMemberActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            }
            case R.id.player_details_menu: {
                intent = new Intent(AboutUsActivity.this, PlayerMembersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.ranking_list_menu: {
                intent = new Intent(AboutUsActivity.this, RankingListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.contact_menu: {
                intent = new Intent(AboutUsActivity.this, Contact.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.news_menu: {
                intent = new Intent(AboutUsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }

            case R.id.about_us_menu: {
                break;
            }


        }
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout_au);
        drawer1.closeDrawer(GravityCompat.START);
        return false;
    }

    void init() {
        versionTextView = (TextView) findViewById(R.id.version_textview);
        developerTextView = (TextView) findViewById(R.id.developer_textview);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "pacifico_regular.ttf");
        versionTextView.setTypeface(typeface);
        versionTextView.setText("Version: " + BuildConfig.VERSION_NAME);
        developerTextView.setTypeface(typeface);
        developerTextView.setText("Shubham Khandelwal");

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
