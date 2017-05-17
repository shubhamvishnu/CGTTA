package com.cgtta.cgtta;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Contact extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView head, position, add1, add2, add3, no1, no2, email1, email2;
    EditText feedbackEditText;
    ImageButton sendButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        initNavigationDrawer();
        init();


    }
    void initNavigationDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_contact);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_contact);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open_text, R.string.drawer_closed_text);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_contact);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        ImageView navImageHeader = (ImageView) hView.findViewById(R.id.navigation_drawer_header);
        Glide.with(this).load(R.drawable.cgtta_logo).into(navImageHeader);
    }
    void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(FirebaseReferences.FIREBASE_CONTACT);
        databaseReference.keepSynced(true);

        head = (TextView) findViewById(R.id.c_head_textview);
        position = (TextView) findViewById(R.id.c_position_textview);
        add1 = (TextView) findViewById(R.id.c_address1_textview);
        add2 = (TextView) findViewById(R.id.c_address2_textview);
        add3 = (TextView) findViewById(R.id.c_address3_textview);
        no1 = (TextView) findViewById(R.id.c_number1_textview);
        no2 = (TextView) findViewById(R.id.c_number2_textview);
        email1 = (TextView) findViewById(R.id.c_email1_textview);
        email2 = (TextView) findViewById(R.id.c_email2_textview);

        feedbackEditText = (EditText) findViewById(R.id.feedback_edittext);
        sendButton = (ImageButton) findViewById(R.id.send_feedback_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = feedbackEditText.getText().toString();
                if (!feedback.isEmpty()) {
                    Map<String, Object> feedbackMap = new HashMap<>();
                    DatabaseReference reference = databaseReference.child("feedbacks").push();
                    feedbackMap.put(reference.getKey(), feedback);
                    reference.setValue(feedbackMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            feedbackEditText.setText(null);
                            Toast.makeText(Contact.this, "Thank you.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        initView();
    }

    void initView() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                head.setText(dataSnapshot.child("head").getValue().toString());
                position.setText(dataSnapshot.child("position").getValue().toString());
                add1.setText(dataSnapshot.child("address1").getValue().toString());
                add2.setText(dataSnapshot.child("address2").getValue().toString());
                add3.setText(dataSnapshot.child("address3").getValue().toString());
                no1.setText(dataSnapshot.child("number1").getValue().toString());
                no2.setText(dataSnapshot.child("number2").getValue().toString());
                email1.setText(dataSnapshot.child("email1").getValue().toString());
                email2.setText(dataSnapshot.child("email2").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.association_members_menu: {
                intent = new Intent(Contact.this, AssociationMemberDetailsActivity.class);
                startActivity(intent);
                break;

            }
            case R.id.player_details_menu: {
                intent = new Intent(Contact.this, PlayerMembersActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.ranking_list_menu: {
                intent = new Intent(Contact.this, RankingListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.contact_menu: {
                intent = new Intent(Contact.this, Contact.class);
                startActivity(intent);
                break;
            }


        }
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout_rank);
        drawer1.closeDrawer(GravityCompat.START);
        return false;
    }
}

