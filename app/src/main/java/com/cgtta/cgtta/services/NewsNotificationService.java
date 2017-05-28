package com.cgtta.cgtta.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.cgtta.cgtta.MainActivity;
import com.cgtta.cgtta.R;
import com.cgtta.cgtta.classes.CgttaConstants;
import com.cgtta.cgtta.classes.FirebaseReferences;
import com.cgtta.cgtta.classes.InitIcon;
import com.cgtta.cgtta.classes.NewsArticlePOJO;
import com.cgtta.cgtta.classes.NewsMatchPOJO;
import com.cgtta.cgtta.classes.SharedPreferencesConstants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by shubh on 5/28/2017.
 */

public class NewsNotificationService extends Service {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    int newsCount;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();
        return START_STICKY;
    }

    void init() {
        newsCount = 0;
        databaseReference = firebaseDatabase.getReference(FirebaseReferences.FIREBASE_BULLETIN);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newsCount = (int) dataSnapshot.getChildrenCount();

                if ((newsCount != 0) && checkForNewItem()) {

                    int tempCounter = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ++tempCounter;
                        if (tempCounter == newsCount) {

                            if (snapshot.child("type").getValue().toString().equals("article")) {
                                NewsArticlePOJO newsArticlePOJO = snapshot.getValue(NewsArticlePOJO.class);
                                showNewArticleNotification(newsArticlePOJO);
                            } else if (snapshot.child("type").getValue().toString().equals("match")) {
                                NewsMatchPOJO newsMatchPOJO = snapshot.getValue(NewsMatchPOJO.class);
                                showNewMatchNotification(newsMatchPOJO);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    boolean checkForNewItem() {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesConstants.NEWS_PREFERENCE, MODE_PRIVATE);
        if (sharedPreferences.contains("news_count")) {

            int currentCount = sharedPreferences.getInt("news_count", 0);
            if (newsCount > currentCount) {

                return true;
            } else {
                return false;
            }
        } else {

            return true;
        }
    }

    void showNewArticleNotification(NewsArticlePOJO newsArticlePOJO) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_new_png_64);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_new_png_64);
        mBuilder.setLargeIcon(largeIcon);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentTitle(newsArticlePOJO.getTitle());
        mBuilder.setContentText("You have an unread news!");

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle(newsArticlePOJO.getTitle());
        inboxStyle.addLine(newsArticlePOJO.getContent());
        mBuilder.setStyle(inboxStyle);

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(CgttaConstants.NEWS_ARTICLE_NOTIFICATION_ID, mBuilder.build());
    }

    void showNewMatchNotification(NewsMatchPOJO newsMatchPOJO) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_trophy_png_64);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trophy_png_64);
        mBuilder.setLargeIcon(largeIcon);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentTitle(newsMatchPOJO.getTitle());
        mBuilder.setContentText(newsMatchPOJO.getLocation() + ", " + newsMatchPOJO.getDate());

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(CgttaConstants.NEWS_MATCH_NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
