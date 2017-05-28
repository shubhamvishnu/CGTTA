package com.cgtta.cgtta.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.cgtta.cgtta.ArticleActivity;
import com.cgtta.cgtta.MainActivity;
import com.cgtta.cgtta.R;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.classes.NewsArticlePOJO;
import com.cgtta.cgtta.classes.NewsMatchPOJO;
import com.cgtta.cgtta.classes.SharedPreferencesConstants;
import com.cgtta.cgtta.viewholders.NewsBulletinArticleViewHolder;
import com.cgtta.cgtta.viewholders.NewsBulletinMatchViewHolder;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubh on 5/16/2017.
 */

public class NewBulletinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    public static int VIEW_BASIC_ARTICLE = 1;
    public static int VIEW_BASIC_MATCH = 2;

    Context context;
    private LayoutInflater inflator;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    List<NewsArticlePOJO> newArticleList;

    List<NewsMatchPOJO> newMatchList;

    List<String> typeList;
    LinearLayoutManager linearLayoutManager;


    public NewBulletinAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        this.context = context;
        this.inflator = LayoutInflater.from(context);
        this.linearLayoutManager = linearLayoutManager;
        init();
    }

    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = firebaseDatabase.getReference(FirebaseReferences.FIREBASE_BULLETIN);
        databaseReference.keepSynced(true);

        newArticleList = new ArrayList<>();
        typeList = new ArrayList<>();
        newMatchList = new ArrayList<>();


        initItems();
    }

    void initItems() {
        updateOverallCount();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int position = typeList.size();
                if (dataSnapshot.child("type").getValue().toString().equals("article")) {
                    NewsArticlePOJO newsArticlePOJO = dataSnapshot.getValue(NewsArticlePOJO.class);
                    newsArticlePOJO.setImageUrl(dataSnapshot.getKey());
                    newArticleList.add(newsArticlePOJO);
                    typeList.add("article");
                    notifyItemInserted(position);
                } else if (dataSnapshot.child("type").getValue().toString().equals("match")) {
                    NewsMatchPOJO newsMatchPOJO = dataSnapshot.getValue(NewsMatchPOJO.class);
                    newMatchList.add(newsMatchPOJO);
                    typeList.add("match");
                    notifyItemInserted(position);
                }
                linearLayoutManager.scrollToPosition(position);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void updateOverallCount() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int newsCount = (int) dataSnapshot.getChildrenCount();
                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesConstants.NEWS_PREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("news_count", newsCount);
                editor.apply();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (typeList.get(position).equals("article")) {
            return VIEW_BASIC_ARTICLE;
        } else if (typeList.get(position).equals("match")) {
            return VIEW_BASIC_MATCH;
        }
        return 0;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_BASIC_ARTICLE) {
            View view = inflator.inflate(R.layout.recyclerview_new_bulletin_article_row_layout, parent, false);
            view.setOnClickListener(this);
            NewsBulletinArticleViewHolder viewHolder = new NewsBulletinArticleViewHolder(view);
            return viewHolder;
        } else if (viewType == VIEW_BASIC_MATCH) {
            View view = inflator.inflate(R.layout.recyclerview_new_bulletin_match_row_layout, parent, false);
            view.setOnClickListener(this);
            NewsBulletinMatchViewHolder viewHolder = new NewsBulletinMatchViewHolder(view);
            return viewHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_BASIC_ARTICLE) {
            int articlesCount = getSpecificCount(position);
            ((NewsBulletinArticleViewHolder) holder).titleTextView.setText(newArticleList.get(articlesCount).getTitle());
            ((NewsBulletinArticleViewHolder) holder).contentTextView.setText(newArticleList.get(articlesCount).getContent());
            ((NewsBulletinArticleViewHolder) holder).byTextView.setText(newArticleList.get(articlesCount).getBy());
            ((NewsBulletinArticleViewHolder) holder).sourceTextView.setText(newArticleList.get(articlesCount).getSource());
            ((NewsBulletinArticleViewHolder) holder).dateTextView.setText(newArticleList.get(articlesCount).getTime());

            Glide.with(context /* context */)
                    .using(new FirebaseImageLoader())
                    .load(storageReference.child(FirebaseReferences.FIREBASE_ARTICLE_PICTURES + "/" + newArticleList.get(articlesCount).getImageUrl() + ".jpg"))
                    .into(((NewsBulletinArticleViewHolder) holder).articleCircleView);

            ++articlesCount;

        } else if (getItemViewType(position) == VIEW_BASIC_MATCH) {
            int matchCount = getMatchCount(position);
            ((NewsBulletinMatchViewHolder) holder).title.setText(newMatchList.get(matchCount).getTitle());
            ((NewsBulletinMatchViewHolder) holder).location.setText(newMatchList.get(matchCount).getLocation());
            ((NewsBulletinMatchViewHolder) holder).date.setText(newMatchList.get(matchCount).getDate());
            ((NewsBulletinMatchViewHolder) holder).status.setText(newMatchList.get(matchCount).getStatus());

            ++matchCount;
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = MainActivity.newsBulletinArticleRecyclerView.getChildLayoutPosition(v);
        if (getItemViewType(itemPosition) == VIEW_BASIC_ARTICLE) {
            int articlePosition = getSpecificCount(itemPosition);
            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra("title", newArticleList.get(articlePosition).getTitle());
            intent.putExtra("content", newArticleList.get(articlePosition).getContent());
            intent.putExtra("by", newArticleList.get(articlePosition).getBy());
            intent.putExtra("source", newArticleList.get(articlePosition).getSource());
            intent.putExtra("date", newArticleList.get(articlePosition).getTime());
            intent.putExtra("url", newArticleList.get(articlePosition).getImageUrl());
            context.startActivity(intent);


        }
    }

    int getSpecificCount(int position) {
        int articleCount = -1;
        for (int i = 0; i <= position; i++) {
            if (typeList.get(i).equals("article")) {
                ++articleCount;
            }
        }
        return articleCount;
    }

    int getMatchCount(int position) {
        int count = -1;
        for (int i = 0; i <= position; i++) {
            if (typeList.get(i).equals("match")) {
                ++count;
            }
        }
        return count;
    }
}
