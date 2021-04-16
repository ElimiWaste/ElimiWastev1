package com.example.elimiwastev1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

// Time: https://stackoverflow.com/questions/5369682/how-to-get-current-time-and-date-in-android
// Pop-up design adapted from: https://www.youtube.com/watch?v=cNVAhzaLYtw&t=70s
// Simple Manual Entry from: https://www.youtube.com/watch?v=1vN_wuAahqA
//TODO: convert to work as fragment or add code to fragment_Manual for it to work, (this will solve navigation issues)
//TODO: add SQLite to this class to store user data (in progress see
//TODO: save data as subItem list, need to create adapter class (in progress see
//TODO: Work on UI
public class manual_Test extends AppCompatActivity {
    Button addUserEntry;
    ArrayList<String> addArray = new ArrayList<String>();
    EditText txt;
    EditText date;
    ListView groceryList;

    //Pop-up
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__manual);
        txt = (EditText) findViewById(R.id.itemName);
        groceryList = (ListView) findViewById(R.id.itemList);
        date = (EditText) findViewById(R.id.dateEntry);

        addUserEntry = (Button) findViewById(R.id.addEntry);
        addUserEntry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String getInput = txt.getText().toString() + " " + date.getText().toString();

                if (getInput == null || getInput.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Empty Input", Toast.LENGTH_LONG).show();
                } else if (date.equals("")) {
                    Date currentTime = Calendar.getInstance().getTime();
                    addArray.add(getInput + " " + currentTime);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(manual_Test.this, android.R.layout.simple_list_item_1, addArray);
                    groceryList.setAdapter(adapter);
                    ((EditText) findViewById(R.id.itemName)).setText(" ");
                } else {
                    addArray.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(manual_Test.this, android.R.layout.simple_list_item_1, addArray);
                    groceryList.setAdapter(adapter);
                    ((EditText) findViewById(R.id.itemName)).setText(" ");
                }
            }

        });

        groceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    box(addArray.get(position), position);

            }
        });


        }
        public void box(String currentItem, int itemIndex) {
            Intent intent = new Intent(this, edit_delete_text_page.class);
            startActivity(intent);


        }
      /*  edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                addArray.set(itemIndex, newEdit.getText().toString());
                adapter.notifyDataSetChanged();

            }
                                });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                addArray.remove(itemIndex);
                adapter.notifyDataSetChanged();
            }
                                  }); */

        }


  //Experimenting with other tutorial, doesn't work as expected, kept for future reference

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

