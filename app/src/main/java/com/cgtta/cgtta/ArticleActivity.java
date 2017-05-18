package com.cgtta.cgtta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.viewholders.NewsBulletinArticleViewHolder;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ArticleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView title, content, by, source, date;
    ImageView preview;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        init();
    }

    void init() {
        storageReference = FirebaseStorage.getInstance().getReference();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        title = (TextView) findViewById(R.id.article_view_title);
        by = (TextView) findViewById(R.id.article_view_by);
        content = (TextView) findViewById(R.id.article_view_content);
        date = (TextView) findViewById(R.id.article_view_time);
        source = (TextView) findViewById(R.id.article_view_source);
        preview = (ImageView) findViewById(R.id.article_view_image);

        initView();

    }

    void initView() {
        Bundle extras = getIntent().getExtras();
        try {
            if (extras != null) {
                String titleString = extras.getString("title");
                String contentString = extras.getString("content");
                String byString = extras.getString("by");
                String sourceString = extras.getString("source");
                String dateString = extras.getString("date");
                String urlString = extras.getString("url");
                title.setText(titleString);
                content.setText(contentString);
                by.setText(byString);
                source.setText(sourceString);
                date.setText(dateString);
                Glide.with(this /* context */)
                        .using(new FirebaseImageLoader())
                        .load(storageReference.child(FirebaseReferences.FIREBASE_ARTICLE_PICTURES + "/" + urlString + ".jpg"))
                        .into(preview);


            }
        } catch (NullPointerException e) {
            onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

}
