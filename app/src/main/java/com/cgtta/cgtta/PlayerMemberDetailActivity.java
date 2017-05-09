package com.cgtta.cgtta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlayerMemberDetailActivity extends AppCompatActivity {
    TextView nameTextView, rankTextView, bioTextView, emailTextView, numberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_member_detail);
        init();
    }

    void init() {
        nameTextView = (TextView) findViewById(R.id.pd_name_textview);
        rankTextView = (TextView) findViewById(R.id.pd_rank_textview);
        bioTextView = (TextView) findViewById(R.id.pd_bio_textview);
        emailTextView = (TextView) findViewById(R.id.pd_email_textview);
        numberTextView = (TextView) findViewById(R.id.pd_number_textview);
    }

}
