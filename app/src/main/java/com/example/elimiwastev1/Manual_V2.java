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


//Tutorial followed to create class: https://www.youtube.com/watch?v=4k1ZMpO9Zn0&t=884s

/**
 * This class stores and displays user-entered food items, their purchase date, and their expiration date as a ListView
 */
public class Manual_V2 extends AppCompatActivity {


    private ListView noteListView; //ListView of item name, date, and expiration date
    static boolean loadDB = true; //Boolean to check if database is loaded, set to true

    /**
     * onCreate method, sets ContentView to manual_v2_activity.xml
     * initializes objects used in class
     * loads the SQLite database from memory
     * creates NoteAdapter object
     * sets onItemClickListener for items in ListView
     * @param savedInstanceState is the saved state of the activity, loaded during onCreate
     */
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

    /**
     * initializes noteListView object from manual_v2_activity layout
     */
    private void initWidgets()
    {
        noteListView = findViewById(R.id.noteListView);
    }

    /**
     * Gets instance of database from DatabaseHelper class
     * populates ListView with items
     * sets boolean loadDB to false
     */
    private void loadFromDBToMemory()
    {
        DatabaseHelper sqLiteManager = DatabaseHelper.instanceOfDatabase(this);
        sqLiteManager.populateNoteListArray();
        loadDB = false;
    }


    /**
     * Creates NoteAdapter object
     */
    private void setNoteAdapter()
    {
        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), Note.nonDeletedNotes());
        noteListView.setAdapter(noteAdapter);
    }


    /**
     * Sets onItemClickListener of selected object in ListView
     */
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


    /**
     * Creates a new intent to start NoteDetailActivity activity
     * @param view
     */
    public void newNote(View view)
    {
        Intent newNoteIntent = new Intent(this, NoteDetailActivity.class);
        startActivity(newNoteIntent);
    }

    /**
     * When activity is paused, and user returns to activity onResume is invoked
     * setNoteAdapter is run
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        setNoteAdapter();
    }
}
