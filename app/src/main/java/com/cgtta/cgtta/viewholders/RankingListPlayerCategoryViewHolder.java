package com.cgtta.cgtta.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cgtta.cgtta.R;

/**
 * Created by shubh on 5/18/2017.
 */

public class RankingListPlayerCategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView playerTextView;

    public RankingListPlayerCategoryViewHolder(View itemView) {
        super(itemView);
        playerTextView = (TextView) itemView.findViewById(R.id.ranking_list_player_category_textview);
    }
}
