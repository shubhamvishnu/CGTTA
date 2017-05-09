package com.cgtta.cgtta.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cgtta.cgtta.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shubh on 5/9/2017.
 */

public class PlayerMembersViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView, rankTextView;
    public CircleImageView profileImageView;
    public PlayerMembersViewHolder(View itemView) {
        super(itemView);
        nameTextView = (TextView) itemView.findViewById(R.id.p_name_textview);
        rankTextView = (TextView) itemView.findViewById(R.id.p_rank_textview);
        profileImageView = (CircleImageView) itemView.findViewById(R.id.p_profile_image_view);
    }
}
