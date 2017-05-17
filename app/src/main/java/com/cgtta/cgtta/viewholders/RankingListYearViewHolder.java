package com.cgtta.cgtta.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cgtta.cgtta.R;

/**
 * Created by shubh on 5/17/2017.
 */

public class RankingListYearViewHolder extends RecyclerView.ViewHolder {
    public TextView yearTextView;

    public RankingListYearViewHolder(View itemView) {
        super(itemView);
        yearTextView = (TextView) itemView.findViewById(R.id.ranking_list_years_textview);
    }
}
