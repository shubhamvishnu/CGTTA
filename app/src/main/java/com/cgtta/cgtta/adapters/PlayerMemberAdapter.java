package com.cgtta.cgtta.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.cgtta.cgtta.AssociationMemberDetailsActivity;
import com.cgtta.cgtta.PlayerMemberDetailActivity;
import com.cgtta.cgtta.PlayerMembersActivity;
import com.cgtta.cgtta.R;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.viewholders.PlayerMembersViewHolder;

import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shubh on 5/9/2017.
 */

public class PlayerMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    public static int VIEW_TYPE_PLAYER_BASIC_DETAIL = 1;
    Context context;
    private LayoutInflater inflator;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Map<String, String> memberMap;
    List<Map<String, String>> memberMapList;


    public PlayerMemberAdapter(Context context) {
        this.context = context;
        this.inflator = LayoutInflater.from(context);
        init();
    }

    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = firebaseDatabase.getReference(FirebaseReferences.FIREBASE_PLAYER_MEMBER);
        databaseReference.keepSynced(true);
        memberMapList = new ArrayList<>();

        initItems();
    }

    void initItems() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                memberMap = new HashMap<>();
                int position = memberMapList.size();
                memberMap.put("name", dataSnapshot.child("name").getValue().toString());
                memberMap.put("id", dataSnapshot.child("id").getValue().toString());
                memberMap.put("state", dataSnapshot.child("state").getValue().toString());
                memberMap.put("gender", dataSnapshot.child("gender").getValue().toString());
                memberMap.put("dob", dataSnapshot.child("dob").getValue().toString());
                memberMap.put("url", dataSnapshot.getKey());
                memberMapList.add(memberMap);
                Collections.sort(memberMapList, new Comparator<Map<String, String>>() {
                    public int compare(Map<String, String> m1, Map<String, String> m2) {
                        return m1.get("name").compareTo(m2.get("name"));
                    }
                });
                notifyItemInserted(position);
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
        return VIEW_TYPE_PLAYER_BASIC_DETAIL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PLAYER_BASIC_DETAIL) {
            View view = inflator.inflate(R.layout.recyclerview_player_member_row_layout, parent, false);
            view.setOnClickListener(this);
            PlayerMembersViewHolder viewHolder = new PlayerMembersViewHolder(view);
            return viewHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_PLAYER_BASIC_DETAIL) {
            Map<String, String> member = memberMapList.get(position);
            ((PlayerMembersViewHolder) holder).nameTextView.setText(member.get("name"));
            ((PlayerMembersViewHolder) holder).playerIdTextView.setText(member.get("id"));

            Glide.with(context /* context */)
                    .using(new FirebaseImageLoader())
                    .load(storageReference.child(FirebaseReferences.FIREBASE_PROFILE_PICTURES + "/"+member.get("url")+".jpg"))
                    .into(((PlayerMembersViewHolder) holder).profileImageView);


        }
    }

    @Override
    public int getItemCount() {
        return memberMapList.size();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = PlayerMembersActivity.playerRecyclerView.getChildLayoutPosition(v);
        Map<String, String> member = memberMapList.get(itemPosition);
        Intent intent = new Intent(context, PlayerMemberDetailActivity.class);
        intent.putExtra("name", member.get("name"));
        intent.putExtra("state", member.get("state"));
        intent.putExtra("gender", member.get("gender"));
        intent.putExtra("dob", member.get("dob"));
        intent.putExtra("id", member.get("id"));
        intent.putExtra("url", member.get("url"));
        context.startActivity(intent);
    }
}
