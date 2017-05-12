package com.cgtta.cgtta.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cgtta.cgtta.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shubh on 5/12/2017.
 */

public class AssociationMemberContentViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView, positionTextView, titleTextView;
    public CircleImageView previewProfileImageView;
    public AssociationMemberContentViewHolder(View itemView) {
        super(itemView);
        nameTextView = (TextView) itemView.findViewById(R.id.a_name_textview);
        positionTextView = (TextView) itemView.findViewById(R.id.a_position_textview);
        titleTextView = (TextView) itemView.findViewById(R.id.a_title_textview);
        previewProfileImageView = (CircleImageView) itemView.findViewById(R.id.a_profile_image_view);
    }
}
