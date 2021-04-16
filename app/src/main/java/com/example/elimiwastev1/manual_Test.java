package com.example.elimiwastev1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ListMenuItemView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class manual_Test extends AppCompatActivity {
    EditText itemName, itemDate;
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


               // ContactAdapter contactAdapter = new ContactAdapter(arrayList,fragment_Manual.this);
              //  groceryList.setAdapter(contactAdapter);






            }
        });

    }








}

