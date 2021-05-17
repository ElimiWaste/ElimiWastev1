package com.example.elimiwastev1;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class Controller extends Application {

    //declares arraylist of Food objects
    ArrayList<Food> foodList = new ArrayList<Food>();

    public ArrayList<Food> getFood() {
        return foodList;
    }

    public void addFood(Food yummy) {
        foodList.add(yummy);
    }

    public static final String CHANNEL_1_ID = "notifications";
    public static final String CHANNEL_2_ID = "notifications2";


    @Override
    public void onCreate() {
        super.onCreate();
        readQuestionDataFB();
        createNotificationsChannels();
    }
    private void readQuestionDataFB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Foods");

//        // Get Global Controller Class object
//        final Controller aController = (Controller) getApplicationContext();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d("MainActivity", "Number of" + dataSnapshot.getChildrenCount());
                for(DataSnapshot ds : dataSnapshot.getChildren() ){
                    Food f = ds.getValue(Food.class);
//                    aController.addFood(f);
                    addFood(f);
                    Log.d("MainActivity", "onDataChange Food Name: " + f.getName() + "OnDataChange Shelf Life: " + f.getLife());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        });

    }

    private void createNotificationsChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "ReminderChannel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("This is the first channel");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "ReminderChannel2",
                    NotificationManager.IMPORTANCE_LOW

            );
            channel2.setDescription("This is the second channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }

}
