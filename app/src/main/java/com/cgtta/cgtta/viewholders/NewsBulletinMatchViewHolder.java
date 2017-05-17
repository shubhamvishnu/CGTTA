package com.cgtta.cgtta.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cgtta.cgtta.R;

/**
 * Created by shubh on 5/17/2017.
 */

public class NewsBulletinMatchViewHolder extends RecyclerView.ViewHolder {
    public TextView title, location, team1, team2, date;
    public NewsBulletinMatchViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.match_title_textview);
        location = (TextView) itemView.findViewById(R.id.match_location_textview);
        team1 = (TextView) itemView.findViewById(R.id.match_team1_name_textview);
        team2 = (TextView) itemView.findViewById(R.id.match_team2_name_textview);
        date = (TextView) itemView.findViewById(R.id.match_date_textview);
    }
}
