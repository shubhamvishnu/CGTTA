package com.cgtta.cgtta.adapters;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cgtta.cgtta.AssociationMemberActivity;
import com.cgtta.cgtta.AssociationMemberDetailsActivity;
import com.cgtta.cgtta.R;
import com.cgtta.cgtta.classes.AssociationDetails;
import com.cgtta.cgtta.classes.FirebaseReferences;

import com.cgtta.cgtta.classes.OtherAssociationMember;
import com.cgtta.cgtta.viewholders.AssociationMemberContentViewHolder;
import com.cgtta.cgtta.viewholders.NewsBulletinArticleViewHolder;
import com.cgtta.cgtta.viewholders.NewsBulletinMatchViewHolder;
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

public class AssociationMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public static int VIEW_BASIC = 1;
    public static int VIEW_MINIMAL = 2;

    Context context;
    private LayoutInflater inflator;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    List<AssociationDetails> basicMemberList;

    List<OtherAssociationMember> otherMemberList;

    List<String> typeList;


    public AssociationMemberAdapter(Context context) {
        this.context = context;
        this.inflator = LayoutInflater.from(context);
        init();
    }

    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = firebaseDatabase.getReference(FirebaseReferences.FIREBASE_ASSOCIATION_MEMBER);
        databaseReference.keepSynced(true);

        basicMemberList = new ArrayList<>();
        typeList = new ArrayList<>();
        otherMemberList = new ArrayList<>();


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
                    basicMemberList.add(associationDetails);
                    typeList.add("association_type_detailed");
                    notifyItemInserted(position);
                } else if (dataSnapshot.child("type").getValue().toString().equals("association_type_no_detail")) {
                    OtherAssociationMember otherAssociationMember = dataSnapshot.getValue(OtherAssociationMember.class);
                    otherMemberList.add(otherAssociationMember);
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
            return VIEW_BASIC;
        } else if (typeList.get(position).equals("association_type_no_detail")) {
            return VIEW_MINIMAL;
        }
        return 0;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_BASIC) {
            View view = inflator.inflate(R.layout.recyclerview_association_content_row_layout, parent, false);
            view.setOnClickListener(this);
            AssociationMemberContentViewHolder viewHolder = new AssociationMemberContentViewHolder(view);
            return viewHolder;
        } else if (viewType == VIEW_MINIMAL) {
            View view = inflator.inflate(R.layout.recyclerview_other_association_members_row_layout, parent, false);
            view.setOnClickListener(this);
            OtherAssociationViewHolder viewHolder = new OtherAssociationViewHolder(view);
            return viewHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == VIEW_BASIC) {
            int basicMemberCount = getSpecificPosition(position);
            ((AssociationMemberContentViewHolder) holder).titleTextView.setText(basicMemberList.get(basicMemberCount).getTitle());
            ((AssociationMemberContentViewHolder) holder).positionTextView.setText(basicMemberList.get(basicMemberCount).getPosition());
            ((AssociationMemberContentViewHolder) holder).nameTextView.setText(basicMemberList.get(basicMemberCount).getName());

            Glide.with(context /* context */)
                    .using(new FirebaseImageLoader())
                    .load(storageReference.child(FirebaseReferences.FIREBASE_ASSOCIATION_PROFILE_PICTURES + "/" + basicMemberList.get(basicMemberCount).getProfile_url() + ".jpg"))
                    .into(((AssociationMemberContentViewHolder) holder).previewProfileImageView);

            ++basicMemberCount;

        } else if (getItemViewType(position) == VIEW_MINIMAL) {
            int minimalMemberCount = getOtherSpecificPosition(position);

            ((OtherAssociationViewHolder) holder).nameTextView.setText(otherMemberList.get(minimalMemberCount).getName());
            ((OtherAssociationViewHolder) holder).cityTextView.setText(otherMemberList.get(minimalMemberCount).getCity());
            ((OtherAssociationViewHolder) holder).titleTextView.setText(otherMemberList.get(minimalMemberCount).getTitle());

            ++minimalMemberCount;
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = AssociationMemberActivity.associationMemberRecyclerView.getChildLayoutPosition(v);
        if (getItemViewType(itemPosition) == VIEW_BASIC) {
            int memberCount = getSpecificPosition(itemPosition);
            Intent intent = new Intent(context, AssociationMemberDetailsActivity.class);
            intent.putExtra("title", basicMemberList.get(memberCount).getTitle());
            intent.putExtra("name", basicMemberList.get(memberCount).getName());
            intent.putExtra("position", basicMemberList.get(memberCount).getPosition());
            intent.putExtra("address", basicMemberList.get(memberCount).getAddress());
            intent.putExtra("number", basicMemberList.get(memberCount).getNumber());
            intent.putExtra("email", basicMemberList.get(memberCount).getEmail());
            intent.putExtra("url", basicMemberList.get(memberCount).getProfile_url());
            context.startActivity(intent);
        }
    }
    int getSpecificPosition(int position){
        int memberCount = -1;
        for (int i = 0; i <= position; i++) {
            if (typeList.get(i).equals("association_type_detailed")) {
                ++memberCount;
            }
        }
        return memberCount;
    }
    int getOtherSpecificPosition(int position){
        int memberCount = -1;
        for (int i = 0; i <= position; i++) {
            if (typeList.get(i).equals("association_type_no_detail")) {
                ++memberCount;
            }
        }
        return memberCount;
    }
}