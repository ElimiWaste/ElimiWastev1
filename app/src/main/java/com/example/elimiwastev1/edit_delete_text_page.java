package com.example.elimiwastev1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//TODO: Add functionality, just a UI
public class edit_delete_text_page extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_delete_box);

        TextView newText = (TextView)findViewById(R.id.txtmessage);
        newText.setText("Edit Item");

        EditText newEdit = (EditText)findViewById(R.id.txtinput);

        Button edit = (Button)findViewById(R.id.btdone);
        Button delete = (Button)findViewById(R.id.btndelete);

    }
}