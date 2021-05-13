package com.example.elimiwastev1;
//3
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//CANCEL A NOTIF
//https://stackoverflow.com/questions/2665634/how-to-clear-a-notification-in-android#:~:text=Use%20the%20following%20code%20to,cancel(NOTIFICATION_ID)%3B
public class MainActivity extends AppCompatActivity {
    //Variables for Notifications
    private NotificationManagerCompat notificationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navBar = findViewById(R.id.navBar);
        navBar.setOnNavigationItemSelectedListener(navListener);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        //NavigationUI.setupWithNavController(navBar,navController);

        //Set<Integer> topLevelDestinations = new HashSet<>();
        //topLevelDestinations.add(R.id.fragment_OCR);
        //topLevelDestinations.add(R.id.fragment_Manual);
        //topLevelDestinations.add(R.id.fragment_Home);
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //assign notification variables with context from Notification ManagerCompat
        notificationManager = NotificationManagerCompat.from(this);


        // editTextTitle = findViewById((R.id.edit_text_title));
        // editTextMessage = findViewById(R.id.edit_text_message);
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
        //Testing Firebase Connection
//        myRef.setValue("Yeet");

     /*   val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navBar);
        val navController = findNavController(R.id.nav_host_fragment_container);
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.fragment_Home, R.id.fragment_Manual, R.id.fragment_OCR));
        setupActionBarWithNavController(navController, appBarConfiguration);

        bottomNavigationView.setUpWithNavController(navController); */

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(MainActivity.this, Manual_V2.class);
                startActivity(test);
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.fragment_Home:
                            selectedFragment = new fragment_Home();
                            break;
                        case R.id.fragment_Manual:
                            selectedFragment = new fragment_Manual();
                            break;
                        case R.id.fragment_OCR:
                            selectedFragment = new fragment_OCR();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();

                    return true;
                }
            };
//    private void readQuestionDataFile() {
//        // Reads trivia data from file
//        InputStream is = getResources().openRawResource(R.raw.fooddatabase);
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//
//        // Get Global Controller Class object
//        final Controller aController = (Controller) getApplicationContext();
//
//        String line = "";
//        try {
//            while ((line = reader.readLine()) != null) {
//                // Split by ','
//                String[] fields = line.split(",");
//
//                //Log.v("MainActivity", fields[0] + " " + fields[1]);
//                Food q = new Food(fields[0], fields[1]);
//                aController.addFood(q);
//            }
//        } catch (IOException e) {
//            Log.wtf("MainActivity", "Error reading data on line: " + line);
//        }
//    }

        @Override
    protected void onStart() {
        super.onStart();
//        readQuestionDataFile();
//        readQuestionDataFB();
//            // Get Global Controller Class object
//            final Controller aController = (Controller) getApplicationContext();
//
//            // Write a message to the database
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = database.getReference("Foods");
//
//            for(Food q : aController.getFood()) {
//                Log.v("MainActivity", "Name: " + q.getName() + " Life: " + q.getLife());
////                myRef.push().setValue(q);
//            }
//        for(Food q : aController.getFood())
//            Log.v("MainActivity","OnStart Name: " + q.getName() + "OnStart Shelf Life: " + q.getLife());
    }


}