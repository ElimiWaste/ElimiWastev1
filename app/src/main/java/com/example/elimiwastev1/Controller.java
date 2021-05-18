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

    /**
     * Getter method for Controller class
     * @return foodList, the array list filled with all the food objects
     */
    public ArrayList<Food> getFood() {
        return foodList;
    }

    /**
     * Adds Food objects to the arraylist
     * @param yummy, the variable for the incoming food objects into the Food arraylist
     */
    public void addFood(Food yummy) {
        foodList.add(yummy);
    }

    //Strings to define the two channels for notifications
    public static final String CHANNEL_1_ID = "notifications";
    public static final String CHANNEL_2_ID = "notifications2";

    /**
     * OnCreate method for Controller class
     * Performs super.onCreate in addition to reading the entire firebase and making an arraylist of all the food objects
     * Finally creates the notification channels for the application
     */
    @Override
    public void onCreate() {
        super.onCreate();
        readFoodDataFB();
        createNotificationsChannels();
    }

    /**
     * Reading the entire firebase directory and storing it locally in an arraylist
     * Helps to parse through the firebase data and create individual Food objects for all the items
     * A need to make this more efficient is a future extension
     */
    private void readFoodDataFB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Foods");

//        // Get Global Controller Class object
//        final Controller aController = (Controller) getApplicationContext();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            /**
             * A DataSnapshot is performed and then stored into the arraylist that is previously defined
             */
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

            /**
             * If the data in firebase is not able to be read or parsed, this method is invoked
             * @param error
             */
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value and logs it into the Logcat
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        });

    }

    /**
     * Method to create the notification channels
     * One channel aids in the notifications for the half life, while the other notification channel aids in the notifications for the 2 day warning (Importance is set accordingly)
     */
    private void createNotificationsChannels(){
        // Creates the NotificationChannel, but only on API 26+ because NotificationChannel is not supported in the previous APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "ReminderChannel",
                    //sets priority of the channel to be high
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("This is the first channel");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "ReminderChannel2",
                    //sets priority of the second channel to be low
                    NotificationManager.IMPORTANCE_HIGH

            );
            channel2.setDescription("This is the second channel");

            // Registers the channels of the notifications with the emulator/device. This helps operate the notifications afterwards
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }

}
