package com.example.elimiwastev1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


//Update
public class Manual_V2 extends AppCompatActivity {

    private ListView noteListView;
    static boolean loadDB = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_v2_activity);
        initWidgets();
        if (loadDB){
            loadFromDBToMemory();    // The SQL data is loaded here
        }
        setNoteAdapter();
        setOnClickListener();
    }

    private void initWidgets()
    {
        noteListView = findViewById(R.id.noteListView);
    }

    private void loadFromDBToMemory()
    {
        DatabaseHelper sqLiteManager = DatabaseHelper.instanceOfDatabase(this);
        sqLiteManager.populateNoteListArray();
        loadDB = false;
    }

    private void setNoteAdapter()
    {
        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), Note.nonDeletedNotes());
        noteListView.setAdapter(noteAdapter);
    }


    private void setOnClickListener()
    {
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Note selectedNote = (Note) noteListView.getItemAtPosition(position);
                Intent editNoteIntent = new Intent(getApplicationContext(), NoteDetailActivity.class);
                editNoteIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectedNote.getId());
                startActivity(editNoteIntent);
            }
        });
    }


    public void newNote(View view)
    {
        Intent newNoteIntent = new Intent(this, NoteDetailActivity.class);
        startActivity(newNoteIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setNoteAdapter();
    }
}
