package com.cgtta.cgtta.viewholders;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cgtta.cgtta.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shubh on 5/16/2017.
 */

public class NewsBulletinArticleViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView, contentTextView;
    public ImageView articleCircleView;
    public NewsBulletinArticleViewHolder(View itemView) {
        super(itemView);
        titleTextView = (TextView) itemView.findViewById(R.id.article_title_textview);
        contentTextView = (TextView) itemView.findViewById(R.id.article_content_textview);
        articleCircleView = (ImageView) itemView.findViewById(R.id.article_imageview);
    }
}
