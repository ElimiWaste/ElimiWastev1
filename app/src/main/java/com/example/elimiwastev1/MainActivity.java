package com.example.elimiwastev1;

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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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


/**
 * MainActivity class models the behavior of the home screen of the ElimiWaste app
 */
public class MainActivity extends AppCompatActivity {



    //Variables for Animation
    Animation topAnim, bottomAnim; //Splash Animations, topAnimation for ImageView and bottomAnimation for button2
    ImageView image; //ImageView of ElimiWaste icon
    Button button2; //Button to navigate to manual entry page

    /**
     * onCreate method, initializes features in activity_main layout file and starts animation
     * @param savedInstanceState is the saved instance of the Main Activity page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Removes date, and charging information from user view on home screen of app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        button2 = findViewById(R.id.button2);

        //Sets animations to button and image
        image.setAnimation(topAnim);
        button2.setAnimation(bottomAnim);




//
//        BottomNavigationView navBar = findViewById(R.id.navBar);
//        navBar.setOnNavigationItemSelectedListener(navListener);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        //NavigationUI.setupWithNavController(navBar,navController);

        //Set<Integer> topLevelDestinations = new HashSet<>();
        //topLevelDestinations.add(R.id.fragment_OCR);
        //topLevelDestinations.add(R.id.fragment_Manual);
        //topLevelDestinations.add(R.id.fragment_Home);
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

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

        //button2 onClickListener opens Manual_V2 page for user to see their food items
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent test = new Intent(MainActivity.this, Manual_V2.class);
               startActivity(test);
            }
        });
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                    Fragment selectedFragment = null;
//
//                    switch (item.getItemId()) {
//                        case R.id.fragment_Home:
//                            selectedFragment = new fragment_Home();
//                            break;
//                        case R.id.fragment_Manual:
//                            selectedFragment = new fragment_Manual();
//                            break;
//                        case R.id.fragment_OCR:
//                            selectedFragment = new fragment_OCR();
//                            break;
//                    }
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
//
//                    return true;
//                }
//            };
//    private void readQuestionDataFile() {
//        // Reads trivia data from file
//        InputStream is = getResources().openRawResource(R.raw.fooddatabase);
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//
//        // Get Global Controller Class object
//       final Controller aController = (Controller) getApplicationContext();
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

    /**
     * onStart method invoked with onStart method call of MainActivity
     */
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