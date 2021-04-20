package com.example.elimiwastev1;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class Manual_Test extends AppCompatActivity {
    Button addUserEntry;
    Button currentDateItem;
    ArrayList<String> addArray = new ArrayList<String>();
    EditText txt;
    EditText date;
    ListView groceryList;
    PopupWindow newWin;
    int index;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__manual);

        txt = (EditText) findViewById(R.id.itemName);
        groceryList = (ListView) findViewById(R.id.itemList);
        date = (EditText) findViewById(R.id.dateEntry);
        addUserEntry = (Button) findViewById(R.id.addEntry);
        currentDateItem = (Button) findViewById(R.id.addWithDate);

        //TODO: Re-format current Date
        currentDateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = txt.getText().toString() + " " + date.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                addArray.add(in + " " + currentTime);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Manual_Test.this, android.R.layout.simple_list_item_1, addArray);
                groceryList.setAdapter(adapter);
                ((EditText) findViewById(R.id.itemName)).setText(" ");
            }

        });

        addUserEntry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String getInput = txt.getText().toString() + " " + date.getText().toString();

                if (getInput == null || date.getText().toString() == null || txt.getText().toString() == null || getInput.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Empty Input", Toast.LENGTH_LONG).show();
                }

                else {
                    addArray.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Manual_Test.this, android.R.layout.simple_list_item_1, addArray);
                    groceryList.setAdapter(adapter);
                    ((EditText) findViewById(R.id.itemName)).setText(" ");
                }
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



}

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








} */

