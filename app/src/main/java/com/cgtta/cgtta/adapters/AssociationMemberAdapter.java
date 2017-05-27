package com.cgtta.cgtta.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.ArticleActivity;
import com.cgtta.cgtta.AssociationMemberActivity;

import com.cgtta.cgtta.AssociationMemberDetailsActivity;
import com.cgtta.cgtta.R;
import com.cgtta.cgtta.classes.AssociationDetails;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.classes.OtherAssociationMember;
import com.cgtta.cgtta.viewholders.AssociationMemberContentViewHolder;

import com.cgtta.cgtta.viewholders.OtherAssociationViewHolder;
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
    public static int VIEW_TYPE_ASSOC_MINIMAL_DETAIL = 2;

    Context context;
    private LayoutInflater inflator;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    List<AssociationDetails> associationDetailsList;
    List<OtherAssociationMember> otherAssociationMemberList;
    List<String> typeList;

    int associationDetailsCount;
    int otherAssociationDetailsCount;

    public AssociationMemberAdapter(Context context) {
        this.context = context;
        this.inflator = LayoutInflater.from(context);
        associationDetailsList = new ArrayList<>();
        otherAssociationMemberList = new ArrayList<>();
        typeList = new ArrayList<>();

        associationDetailsCount = 0;
        otherAssociationDetailsCount = 0;
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
                int position = typeList.size();
                if (dataSnapshot.child("type").getValue().toString().equals("association_type_detailed")) {

                    AssociationDetails associationDetails = dataSnapshot.getValue(AssociationDetails.class);
                    associationDetails.setProfile_url(dataSnapshot.getKey());
                    associationDetailsList.add(associationDetails);
                    typeList.add("association_type_detailed");
                    Toast.makeText(context, associationDetails.toString(), Toast.LENGTH_SHORT).show();
                    notifyItemInserted(position);
                } else if (dataSnapshot.child("type").getValue().toString().equals("association_type_no_detail")) {
                    OtherAssociationMember otherAssociationMember = dataSnapshot.getValue(OtherAssociationMember.class);
                    otherAssociationMemberList.add(otherAssociationMember);
                    typeList.add("association_type_no_detail");
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
        if (typeList.get(position).equals("association_type_detailed")) {
            return VIEW_TYPE_ASSOC_BASIC_DETAIL;
        } else if (typeList.get(position).equals("association_type_no_detail")) {
            return VIEW_TYPE_ASSOC_MINIMAL_DETAIL;
        } else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ASSOC_BASIC_DETAIL) {
            View view = inflator.inflate(R.layout.recyclerview_association_content_row_layout, parent, false);
            view.setOnClickListener(this);
            AssociationMemberContentViewHolder viewHolder = new AssociationMemberContentViewHolder(view);
            return viewHolder;
        } else if (viewType == VIEW_TYPE_ASSOC_MINIMAL_DETAIL) {
            View view = inflator.inflate(R.layout.recyclerview_other_association_members_row_layout, parent, false);
            view.setOnClickListener(this);
            OtherAssociationViewHolder viewHolder = new OtherAssociationViewHolder(view);
            return viewHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ASSOC_BASIC_DETAIL) {
            ((AssociationMemberContentViewHolder) holder).titleTextView.setText(associationDetailsList.get(associationDetailsCount).getTitle());

            ((AssociationMemberContentViewHolder) holder).nameTextView.setText(associationDetailsList.get(associationDetailsCount).getName());
            ((AssociationMemberContentViewHolder) holder).positionTextView.setText(associationDetailsList.get(associationDetailsCount).getPosition());

            Glide.with(context /* context */)
                    .using(new FirebaseImageLoader())
                    .load(storageReference.child(FirebaseReferences.FIREBASE_ASSOCIATION_PROFILE_PICTURES + "/" + associationDetailsList.get(associationDetailsCount).getProfile_url() + ".jpg"))
                    .into(((AssociationMemberContentViewHolder) holder).previewProfileImageView);
            ++associationDetailsCount;
        } else {
            ((OtherAssociationViewHolder) holder).titleTextView.setText(otherAssociationMemberList.get(otherAssociationDetailsCount).getTitle());
            ((OtherAssociationViewHolder) holder).cityTextView.setText(otherAssociationMemberList.get(otherAssociationDetailsCount).getCity());
            ((OtherAssociationViewHolder) holder).nameTextView.setText(otherAssociationMemberList.get(otherAssociationDetailsCount).getName());
            ++otherAssociationDetailsCount;
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = AssociationMemberActivity.associationMemberRecyclerView.getChildLayoutPosition(v);
        if (getItemViewType(itemPosition) == VIEW_TYPE_ASSOC_BASIC_DETAIL) {
            int memberCount = getSpecificCount(itemPosition);
            Intent intent = new Intent(context, AssociationMemberDetailsActivity.class);
            intent.putExtra("title", associationDetailsList.get(memberCount).getTitle());
            intent.putExtra("name", associationDetailsList.get(memberCount).getName());
            intent.putExtra("position", associationDetailsList.get(memberCount).getPosition());
            intent.putExtra("address", associationDetailsList.get(memberCount).getAddress());
            intent.putExtra("number", associationDetailsList.get(memberCount).getNumber());
            intent.putExtra("email", associationDetailsList.get(memberCount).getEmail());
            intent.putExtra("url", associationDetailsList.get(memberCount).getProfile_url());
            context.startActivity(intent);
        } else {

        }

    }
    int getSpecificCount(int position) {
        int memberCount = -1;
        for (int i = 0; i <= position; i++) {
            if (typeList.get(i).equals("association_type_detailed")) {
                ++memberCount;
            }
        }
        return memberCount;
    }
}
