package com.example.elimiwastev1;

import android.database.Cursor;
import android.os.Bundle;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        DB = new DatabaseHelper(this);
        userList = new ArrayList<>();
        Cursor data = DB.showData();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(ViewContent.this, "There is nothing in this database", Toast.LENGTH_LONG).show();
        }

        else {
            while(data.moveToNext()){
                user = new User(data.getString(1), data.getString(2));
                 userList.add(user);
            }
            ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this, R.layout.list_adapter_view,userList);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }

    }


}
