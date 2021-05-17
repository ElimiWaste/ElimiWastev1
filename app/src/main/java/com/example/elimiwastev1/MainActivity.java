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

        //onClickListener to open to new activity list of food items
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent test = new Intent(MainActivity.this, Manual_V2.class);
               startActivity(test);
            }
        });
    }


    /**
     * onStart method invoked with onStart method call of MainActivity
     */
        @Override
    protected void onStart() {
        super.onStart();
    }


}