package com.cgtta.cgtta.viewholders;

import android.graphics.Paint;
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
    public TextView titleTextView, contentTextView, byTextView, sourceTextView, dateTextView;

    public ImageView articleCircleView;
    public NewsBulletinArticleViewHolder(View itemView) {
        super(itemView);
        titleTextView = (TextView) itemView.findViewById(R.id.article_title_textview);
        titleTextView.setPaintFlags( titleTextView.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        contentTextView = (TextView) itemView.findViewById(R.id.article_content_textview);
        articleCircleView = (ImageView) itemView.findViewById(R.id.article_imageview);
        byTextView = (TextView) itemView.findViewById(R.id.article_by);
        sourceTextView = (TextView) itemView.findViewById(R.id.article_source);
        dateTextView = (TextView) itemView.findViewById(R.id.article_date);

    }
}
