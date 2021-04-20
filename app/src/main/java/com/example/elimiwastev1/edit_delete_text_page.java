package com.example.elimiwastev1;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//Adapted from: https://www.youtube.com/watch?v=fn5OlqQuOCk
//Works, but need to make background transparent
public class edit_delete_text_page extends manual_Test {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_delete_box);

        TextView newText = (TextView)findViewById(R.id.txtmessage);
        newText.setText("Edit Item");

        EditText newEdit = (EditText)findViewById(R.id.txtinput);

        Button edit = (Button)findViewById(R.id.btdone);
        Button delete = (Button)findViewById(R.id.btndelete);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w = dm.widthPixels;
        int h = dm.heightPixels;

        getWindow().setLayout((int)(w*0.8), (int)(h*0.6));

        //ERROR, need to debug, it crashes when run
       /*  edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArray.set(getIndex(), newEdit.getText().toString());
                adapter.notifyDataSetChanged();

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArray.remove(getIndex());
                adapter.notifyDataSetChanged();
            }
        }); */




    }


}