package com.cgtta.cgtta.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cgtta.cgtta.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shubh on 5/13/2017.
 */

public class RankingListViewHolder extends RecyclerView.ViewHolder {
    public TextView colTextView, rowTextView;

    public RankingListViewHolder(View itemView) {
        super(itemView);
        colTextView = (TextView) itemView.findViewById(R.id.rl_col_title_textview);
        rowTextView = (TextView) itemView.findViewById(R.id.rl_row_value_textview);

    }
}
