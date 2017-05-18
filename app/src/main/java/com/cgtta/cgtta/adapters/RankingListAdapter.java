package com.cgtta.cgtta.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cgtta.cgtta.PlayerMembersActivity;
import com.cgtta.cgtta.R;
import com.cgtta.cgtta.RankingListActivity;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.viewholders.PlayerMembersViewHolder;
import com.cgtta.cgtta.viewholders.RankingListViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shubh on 5/13/2017.
 */

public class RankingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    Context context;
    private LayoutInflater inflator;
    List<String> colList;
    List<String> rowList;
    public RankingListAdapter(Context context, List<String> colList, List<String> rowList) {
        this.context = context;
        this.inflator = LayoutInflater.from(context);
        this.colList = colList;
        this.rowList = rowList;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.recyclerview_ranking_list_row_layout, parent, false);
        view.setOnClickListener(this);
        RankingListViewHolder viewHolder = new RankingListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RankingListViewHolder) holder).colTextView.setText(colList.get(position));
        ((RankingListViewHolder) holder).rowTextView.setText(rowList.get(position));

    }

    @Override
    public int getItemCount() {
        return rowList.size();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = RankingListActivity.rankingListRecyclerView.getChildLayoutPosition(v);
    }
}
