package com.cgtta.cgtta.adapters;

import android.content.Context;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shubh on 5/9/2017.
 */

public class PlayerMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int VIEW_TYPE_PLAYER_BASIC_DETAIL = 1;
    Context context;
    private LayoutInflater inflator;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Map<String, Object> memberMap;
    List<Map<String, Object>> memberMapList;
    StorageReference storageReference;

    public PlayerMemberAdapter(Context context) {
        this.context = context;
        this.inflator = LayoutInflater.from(context);
        init();
    }
    private void init(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = firebaseDatabase.getReference(FirebaseReferences.FIREBASE_PLAYER_MEMBER);
        databaseReference.keepSynced(true);


        memberMapList = new ArrayList<>();

        initItems();
    }
    void initItems(){
        memberMap = new HashMap<>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int position = memberMapList.size();
                memberMap.put("name", dataSnapshot.child("name").getValue().toString());
                memberMap.put("rank", dataSnapshot.child("rank").getValue().toString());
                memberMap.put("profile_url", dataSnapshot.child("profile_url").toString());
                memberMapList.add(memberMap);
                Toast.makeText(context, "content:" + memberMap.toString(), Toast.LENGTH_SHORT).show();
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
            PlayerMembersViewHolder viewHolder = new PlayerMembersViewHolder(view);
            return viewHolder;
        }else{
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_PLAYER_BASIC_DETAIL){
            Map<String, Object> member = memberMapList.get(position);
            ((PlayerMembersViewHolder) holder).nameTextView.setText(member.get("name").toString());
            ((PlayerMembersViewHolder) holder).rankTextView.setText(member.get("rank").toString());
            Toast.makeText(context,member.get("profile_url").toString() , Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public int getItemCount() {
        return memberMapList.size();
    }
}
