package com.example.elimiwastev1;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;

import static com.example.elimiwastev1.R.layout.edit_delete_box;

// Current Time: https://stackoverflow.com/questions/5369682/how-to-get-current-time-and-date-in-android
// Pop-up design adapted from: https://www.youtube.com/watch?v=cNVAhzaLYtw&t=70s
// Simple Manual Entry adapted from: https://www.youtube.com/watch?v=1vN_wuAahqA
// Pop up Window Adapted from: https://stackoverflow.com/questions/23028480/android-how-to-create-popup-window
//TODO: convert to work as fragment or add code to fragment_Manual for it to work, (this will solve navigation issues)
//TODO: add SQLite to this class to store user data, tutorial in MSPlanner
//TODO: save data as subItem list, need to create adapter class (in progress see code below)
//TODO: Work on UI

//Notes:
//Right now, the data is not saved in a class so it does not save when the edit_delete_box activity is opened
//Currently working on the popup feature for editing and deleting text!
//Additionally, there are errors in using the showPopup class

//Update
public class Manual_Test extends AppCompatActivity {
    //For notifications

    DatabaseHelper userEntryDB;
    Button addUserEntry;
    Button currentDateItem;
    Button btnView;
    ArrayList<String> addArray = new ArrayList<String>();
    public EditText txt;
    ListView groceryList;
    PopupWindow newWin;
    int index;
    ArrayAdapter<String> adapter;

    TextView dateView;
    Button dateEnter;
    Calendar calendar;
    DatePickerDialog datepicker;

    int day;
    int month;
    int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__manual);

        userEntryDB = new DatabaseHelper(this);

        txt = (EditText) findViewById(R.id.itemName);
        groceryList = (ListView) findViewById(R.id.itemList);
        addUserEntry = (Button) findViewById(R.id.addEntry);
        currentDateItem = (Button) findViewById(R.id.addWithDate);
        btnView = (Button) findViewById(R.id.viewContent);

        dateView = (TextView) findViewById((R.id.seeDate));
        dateEnter = (Button) findViewById((R.id.addDate));

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manual_Test.this, ViewContent.class);
                startActivity(intent);
            }
        });

        dateEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                Log.d("tag", "message" + dateView.getText().toString());

                datepicker = new DatePickerDialog(Manual_Test.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        dateView.setText(mDayOfMonth + "/" + mMonth + "/" + mYear);
                    }
                }, year, month, day);
                datepicker.show();


            }
        });

        addUserEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInput = txt.getText().toString();
                String getDate = dateView.getText().toString();

                if ((getInput.length() != 0) && (getDate.length() != 0) && !getDate.contains("l")) {
                    //AddData(getInput, getDate);
                    txt.setText("");
                    dateView.setText("");
                } else {
                    Toast.makeText(Manual_Test.this, "You must put in a food item name and date!", Toast.LENGTH_LONG).show();
                }


            }

        });

        //TODO: Re-format current Date
        currentDateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = txt.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                addArray.add(in + " " + currentTime);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Manual_Test.this, android.R.layout.simple_list_item_1, addArray);
                groceryList.setAdapter(adapter);
                ((EditText) findViewById(R.id.itemName)).setText(" ");
            }

        });

        groceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                box(addArray.get(position), position);
                // showPopup(addArray.get(position), position); - Method does not work
                // setIndex(position);
            }
        });
    }

    public void box(String currentItem, int itemIndex) {
        Intent intent = new Intent(this, PopupWindow.class);
        startActivityForResult(intent, itemIndex);

    }
}

  /*  public void AddData(String name, String currentDate) {
        boolean insertData = userEntryDB.updateNoteInDB(name, currentDate);

        if(insertData == true) {
            String dateEntered = dateView.getText().toString();
            Toast.makeText(Manual_Test.this, "Data Successfully Inserted and Reminder Set!", Toast.LENGTH_LONG).show();
            //Will send out a notification with a wait time determined by variable long waitTime
            Intent intent = new Intent(Manual_Test.this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            AlarmManager AlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long timeOfEnter = System.currentTimeMillis();

            long waitTime = 1000 * (0);
            //Will wake up the device to send the notification at this time. Does not matter whether or not the application is closed.
            AlarmManager.set(android.app.AlarmManager.RTC_WAKEUP,
                    timeOfEnter + waitTime,
                    pendingIntent);
        }
        else {
            Toast.makeText(Manual_Test.this, "Aww Shucks! :(.", Toast.LENGTH_LONG).show();

        }

        addUserEntry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String getInput = txt.getText().toString();
                String getDate = dateView.getText().toString();

                boolean insertData = userEntryDB.addData(getInput, getDate);

                if (insertData) {
                    Toast.makeText(Manual_Test.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                }
                else if (getInput.equals("") || getDate.equals("")|| getDate.equals("Click above to enter date")){
                    Toast.makeText(Manual_Test.this, "Aww Shucks! :(.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Manual_Test.this, "Aww Shucks! :(.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    }

   /*  if (getInput == null || date.getText().toString() == null || txt.getText().toString() == null || getInput.trim().equals("")) {
                Toast.makeText(getBaseContext(), "Empty Input", Toast.LENGTH_LONG).show();
            }

    }

    //Setter to to set index for edit_delete_text_page
   /* public void setIndex(int num){
        index = num;
    }

    //Getter to get index for edit_delete_text_page
    public int getIndex(){
        return index;
    } */

    //TODO: Find a way to store data to test for editing and deleting
    //Method does not work completely, there is an error, it crashes
  /*  public void showPopup(String currentItem, int itemIndex) {
        LayoutInflater newInflate = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View edDelBox = newInflate.inflate(R.layout.edit_delete_box, null);
        newWin = new PopupWindow(edDelBox, 480,500,true);
        newWin.showAtLocation(edDelBox, Gravity.CENTER, 0, 40);

        TextView newText;
        newText = (TextView) edDelBox.findViewById(R.id.txtmessage);
        newText.setText("Edit Item");

        EditText newEdit = (EditText) findViewById(R.id.txtinput);
        newEdit.setText(addArray.get(itemIndex));

        Button edit = (Button) findViewById(R.id.btdone);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArray.set(itemIndex, newEdit.getText().toString());
                adapter.notifyDataSetChanged();
                newWin.dismiss();

            }
        });


        Button delete = (Button) findViewById(R.id.btndelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArray.remove(itemIndex);
                adapter.notifyDataSetChanged();
                newWin.dismiss();

            }
        });

    } */





  //Experimenting with other tutorial, doesn't work as expected, kept for future reference to have two column list

   /* EditText itemName, itemDate;
    Button save;
    ListView groceryList;
    String itemName2, itemDate2;
    ArrayList<LvItem>arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__manual);


        itemName = findViewById(R.id.item);
        itemDate = findViewById(R.id.date);
        save = findViewById(R.id.save);
        groceryList = findViewById(R.id.LW);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                itemName2 = itemName.getText().toString();
                itemDate2 = itemDate.getText().toString();

                LvItem lvItem = new LvItem();
                lvItem.setItemName(itemName2);
                lvItem.setItemName(itemDate2);
                arrayList.add(lvItem);


               ContactAdapter contactAdapter = new ContactAdapter(arrayList,manual_Test.this);
               groceryList.setAdapter(contactAdapter);






            }
        });

    }
*/









