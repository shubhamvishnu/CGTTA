package com.cgtta.cgtta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.classes.AssociationDetails;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.viewholders.AssociationMemberContentViewHolder;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class AssociationMemberDetailsActivity extends AppCompatActivity {
    TextView title, name, position, address, number, email;
    ImageView preview;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_member_details);
        init();
    }

    void init() {
        storageReference = FirebaseStorage.getInstance().getReference();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_association_detail);
        toolbar.setTitle("Association Member");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        title = (TextView) findViewById(R.id.ad_title);
        name = (TextView) findViewById(R.id.ad_name);
        position = (TextView) findViewById(R.id.ad_position);
        address = (TextView) findViewById(R.id.ad_address);
        number = (TextView) findViewById(R.id.ad_number);
        email = (TextView) findViewById(R.id.ad_email);
        preview = (ImageView) findViewById(R.id.ad_profile_image_view);

        initView();


    }

    void initView() {
        Bundle extras = getIntent().getExtras();
        try {
            if (extras != null) {
                String titleString = extras.getString("title");
                String nameString = extras.getString("name");
                String positionString = extras.getString("position");
                String addressString = extras.getString("address");
                String numberString = extras.getString("number");
                String emailString = extras.getString("email");
                String urlString = extras.getString("url");

                title.setText(titleString);
                name.setText(nameString);
                position.setText(positionString);
                address.setText(addressString);
                number.setText(numberString);
                email.setText(emailString);
                Glide.with(this /* context */)
                        .using(new FirebaseImageLoader())
                        .load(storageReference.child(FirebaseReferences.FIREBASE_ASSOCIATION_PROFILE_PICTURES + "/" + urlString + ".jpg"))
                        .into(preview);
            }
        } catch (NullPointerException e) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AssociationMemberDetailsActivity.this, AssociationMemberActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
