package com.cgtta.cgtta.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.AssociationMemberDetailsActivity;

import com.cgtta.cgtta.R;
import com.cgtta.cgtta.classes.AssociationDetails;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.viewholders.AssociationMemberContentViewHolder;

import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import java.util.List;


/**
 * Created by shubh on 5/12/2017.
 */

public class AssociationMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    public static int VIEW_TYPE_ASSOC_BASIC_DETAIL = 1;

    Context context;
    private LayoutInflater inflator;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    List<AssociationDetails> associationDetailsList;

    public AssociationMemberAdapter(Context context) {
        this.context = context;
        this.inflator = LayoutInflater.from(context);
        associationDetailsList = new ArrayList<>();
        init();
    }

    void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        databaseReference = firebaseDatabase.getReference(FirebaseReferences.FIREBASE_ASSOCIATION_MEMBER);
        databaseReference.keepSynced(true);

        initItems();
    }

    void initItems() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int position = associationDetailsList.size();

                AssociationDetails associationDetails = new AssociationDetails(dataSnapshot.child("title").getValue().toString(), dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("position").getValue().toString(), dataSnapshot.getKey());
                associationDetailsList.add(associationDetails);

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
        return VIEW_TYPE_ASSOC_BASIC_DETAIL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ASSOC_BASIC_DETAIL) {
            View view = inflator.inflate(R.layout.recyclerview_association_content_row_layout, parent, false);
            view.setOnClickListener(this);
            AssociationMemberContentViewHolder viewHolder = new AssociationMemberContentViewHolder(view);
            return viewHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ASSOC_BASIC_DETAIL) {
            ((AssociationMemberContentViewHolder) holder).titleTextView.setText(associationDetailsList.get(position).getTitle());
            ((AssociationMemberContentViewHolder) holder).nameTextView.setText(associationDetailsList.get(position).getName());
            ((AssociationMemberContentViewHolder) holder).positionTextView.setText(associationDetailsList.get(position).getPosition());

            Glide.with(context /* context */)
                    .using(new FirebaseImageLoader())
                    .load(storageReference.child(FirebaseReferences.FIREBASE_ASSOCIATION_PROFILE_PICTURES + "/" + associationDetailsList.get(position).getProfile_url() + ".jpg"))
                    .into(((AssociationMemberContentViewHolder) holder).previewProfileImageView);

        }
    }

    @Override
    public int getItemCount() {
        return associationDetailsList.size();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = AssociationMemberDetailsActivity.associationMemberRecyclerView.getChildLayoutPosition(v);
        Toast.makeText(context, "" + itemPosition, Toast.LENGTH_SHORT).show();
    }
}
