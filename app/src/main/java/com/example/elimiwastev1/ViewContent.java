package com.example.elimiwastev1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewContent extends AppCompatActivity {

    DatabaseHelper DB;
    ArrayList<User> userList;
    ListView listView;
    User user;
    private static final String TAG = "ListDataActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);
        listView = (ListView) findViewById(R.id.listView);
        DB = new DatabaseHelper(this);


        userList = new ArrayList<>();
        Cursor data = DB.showData();
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(ViewContent.this, "There is nothing in this database", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                user = new User(data.getString(1), data.getString(2));
                userList.add(user);
            }
            ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this, R.layout.list_adapter_view, userList);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            //set an onItemClickListener to the ListView
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = adapterView.getItemAtPosition(i).toString();
                    Log.d(TAG, "onItemClick: You Clicked On " + name);

                    Cursor data = DB.getItemID(name);

                    int itemID = -1;
                    while (data.moveToNext()) {
                        itemID = data.getInt(0);
                    }
                        Log.d(TAG, "ontItemClick: The ID is: " + itemID);
                        Intent editScreenIntent = new Intent(ViewContent.this, EditDataActivity.class);
                        editScreenIntent.putExtra("id", itemID);
                        editScreenIntent.putExtra("name", name);
                        startActivity(editScreenIntent);


                }
            });

        }


        populateListView();

    }


    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = DB.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }


    }
}