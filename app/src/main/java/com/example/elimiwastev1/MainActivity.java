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

public class MainActivity extends AppCompatActivity {
    //Variables for Notifications
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;


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
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
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
                Intent test = new Intent(MainActivity.this, Manual_Test.class);
                startActivity(test);
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("MainActivity", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException());
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


    //Create and send notification through notification channel 1 on click of the corresponding button. Should show up as a circle thing
    public void sendOnChannel1(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, "notifications")
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }
    //Create and send notification through notification channel 2 on click of the corresponding button. Should show up as a grass thing
    public void sendOnChannel2(View v){
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, NotifChannels.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_no_food_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }

}