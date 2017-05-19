package com.cgtta.cgtta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.viewholders.PlayerMembersViewHolder;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerMemberDetailActivity extends AppCompatActivity {
    TextView nameTextView, playerId, state, gender, dob;
    CircleImageView profileImageView;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_member_detail);
        init();
    }

    void init() {
        storageReference = FirebaseStorage.getInstance().getReference();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_player);
        toolbar.setTitle("Player Details");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        profileImageView = (CircleImageView) findViewById(R.id.player_profile_image_view);
        nameTextView = (TextView) findViewById(R.id.player_name);
        playerId = (TextView) findViewById(R.id.id_textview);
        state = (TextView) findViewById(R.id.state_textview);
        dob = (TextView) findViewById(R.id.dob_textview);
        gender = (TextView) findViewById(R.id.gender_textview);


        initView();

    }
    void initView(){
        Bundle extras = getIntent().getExtras();
        try {
            if (extras != null) {
                String nameString = extras.getString("name");
                String playerIdString =extras.getString("id");
                String stateString =extras.getString("state");
                String genderString =extras.getString("gender");
                String dobString =extras.getString("dob");
                String urlString = extras.getString("url");

                nameTextView.setText(nameString);
                playerId.setText(playerIdString);
                state.setText(stateString);
                dob.setText(dobString);
                gender.setText(genderString);




                Glide.with(this /* context */)
                        .using(new FirebaseImageLoader())
                        .load(storageReference.child(FirebaseReferences.FIREBASE_PROFILE_PICTURES + "/"+urlString+".jpg"))
                        .into(profileImageView);
            }
        } catch (NullPointerException e) {
            onBackPressed();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PlayerMemberDetailActivity.this, PlayerMembersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
