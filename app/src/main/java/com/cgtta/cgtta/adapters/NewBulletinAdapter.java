package com.cgtta.cgtta.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.NewsBulletinActivity;
import com.cgtta.cgtta.PlayerMembersActivity;
import com.cgtta.cgtta.R;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.classes.NewsArticlePOJO;
import com.cgtta.cgtta.viewholders.NewsBulletinArticleViewHolder;
import com.cgtta.cgtta.viewholders.PlayerMembersViewHolder;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shubh on 5/16/2017.
 */

public class NewBulletinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    public static int VIEW_BASIC_ARTICLE = 1;
    Context context;
    private LayoutInflater inflator;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    List<NewsArticlePOJO> newArticleList;
    List<String> articleTypeList;


    public NewBulletinAdapter(Context context) {
        this.context = context;
        this.inflator = LayoutInflater.from(context);
        init();
    }

    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = firebaseDatabase.getReference(FirebaseReferences.FIREBASE_ARTICLES);
        databaseReference.keepSynced(true);

        newArticleList = new ArrayList<>();
        articleTypeList = new ArrayList<>();

        initItems();
    }

    void initItems() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int position = articleTypeList.size();
                if (dataSnapshot.child("type").getValue().toString().equals("article")) {
                    NewsArticlePOJO newsArticlePOJO = dataSnapshot.getValue(NewsArticlePOJO.class);
                    newArticleList.add(newsArticlePOJO);
                    articleTypeList.add("article");
                    notifyItemInserted(position);
                }

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

    @Override
    public int getItemViewType(int position) {
        if(articleTypeList.get(position).equals("article")){
            return VIEW_BASIC_ARTICLE;
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
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_BASIC_ARTICLE) {
            ((NewsBulletinArticleViewHolder) holder).titleTextView.setText(newArticleList.get(position).getTitle());
            ((NewsBulletinArticleViewHolder) holder).contentTextView.setText(newArticleList.get(position).getContent());

            Glide.with(context /* context */)
                    .using(new FirebaseImageLoader())
                    .load(storageReference.child(FirebaseReferences.FIREBASE_ARTICLE_PICTURES + "/" + newArticleList.get(position).getImageUrl() + ".jpg"))
                    .into(((NewsBulletinArticleViewHolder) holder).articleCircleView);


        }
    }

    @Override
    public int getItemCount() {
        return articleTypeList.size();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = NewsBulletinActivity.newsBulletinArticleRecyclerView.getChildLayoutPosition(v);
        Toast.makeText(context, "" + itemPosition, Toast.LENGTH_SHORT).show();
    }
}
