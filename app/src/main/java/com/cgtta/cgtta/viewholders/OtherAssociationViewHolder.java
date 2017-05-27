package com.cgtta.cgtta.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cgtta.cgtta.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shubh on 5/27/2017.
 */

public class OtherAssociationViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView, cityTextView, titleTextView;
    public OtherAssociationViewHolder(View itemView) {
        super(itemView);
        nameTextView = (TextView) itemView.findViewById(R.id.oa_name_textview);
        cityTextView = (TextView) itemView.findViewById(R.id.oa_city_textview);
        titleTextView = (TextView) itemView.findViewById(R.id.oa_title_textview);
    }
}
