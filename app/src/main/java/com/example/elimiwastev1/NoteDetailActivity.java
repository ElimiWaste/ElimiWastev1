package com.example.elimiwastev1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class NoteDetailActivity  extends AppCompatActivity {

    private EditText titleEditText, descEditText;
    private Button deleteButton;
    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initWidgets();
        checkForEditNote();
    }

    private void initWidgets()
    {
        titleEditText = findViewById(R.id.titleEditText);
        descEditText = findViewById(R.id.descriptionEditText);
        deleteButton = findViewById(R.id.deleteNoteButton);
    }

    private void checkForEditNote()
    {
        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA, -1);
        selectedNote = Note.getNoteForID(passedNoteID);

        if (selectedNote != null)
        {
            titleEditText.setText(selectedNote.getTitle());
            descEditText.setText(selectedNote.getDescription());
        }
        else
        {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void saveNote(View view)
    {
        DatabaseHelper sqLiteManager = DatabaseHelper.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descEditText.getText());

        if(selectedNote == null)
        {
            int id = Note.noteArrayList.size();
            Note newNote = new Note(id, title, desc);
            Note.noteArrayList.add(newNote);
            sqLiteManager.addNoteToDatabase(newNote);

            Toast.makeText(NoteDetailActivity.this, "Data Successfully Inserted and Reminder Set!", Toast.LENGTH_LONG).show();
            //Will send out a notification with a wait time determined by variable long waitTime
            Intent intent = new Intent(NoteDetailActivity.this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            AlarmManager AlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long timeOfEnter = System.currentTimeMillis();

            long waitTime = 1000 * (0);
            //Will wake up the device to send the notification at this time. Does not matter whether or not the application is closed.
            AlarmManager.set(android.app.AlarmManager.RTC_WAKEUP,
                    timeOfEnter + waitTime,
                    pendingIntent);
        }
        else
        {
            selectedNote.setTitle(title);
            selectedNote.setDescription(desc);
            sqLiteManager.updateNoteInDB(selectedNote);
        }

        finish();
    }

    public void deleteNote(View view)
    {
        selectedNote.setDeleted(new Date());
       DatabaseHelper sqLiteManager = DatabaseHelper.instanceOfDatabase(this);
        sqLiteManager.updateNoteInDB(selectedNote);
        finish();
    }
}
