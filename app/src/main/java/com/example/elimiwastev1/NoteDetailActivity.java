package com.example.elimiwastev1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//Update
public class NoteDetailActivity  extends AppCompatActivity {

    private EditText titleEditText, descEditText;
    private Button deleteButton;
    private Note selectedNote;

    TextView dateView;
    Button dateEnter;
    Calendar calendar;
    DatePickerDialog datepicker;

    int day;
    int month;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initWidgets();
        checkForEditNote();

        dateView = (TextView)findViewById((R.id.seeDate));
        dateEnter = (Button) findViewById((R.id.addDate));

        dateEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                Log.d("tag", "message" +dateView.getText().toString());

                datepicker = new DatePickerDialog(NoteDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        dateView.setText(mDayOfMonth + "/" + mMonth + "/" + mYear);
                    }
                },year, month, day);
                datepicker.show();


            }
        });

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
    public int convertDate(String shelfLife){
        shelfLife = shelfLife.toUpperCase();
        Log.d("albatross", shelfLife);
        int convertedLife = 0;
        if(shelfLife.contains("YEAR")){
            String cleanShelfLife = shelfLife.replaceAll("\\D+",""); //remove non-digits
            Log.d("albatross1", cleanShelfLife);
            convertedLife = 365 * Integer.parseInt(cleanShelfLife);
        }
        else if(shelfLife.contains("MONTH")){
            String cleanShelfLife = shelfLife.replaceAll("\\D+",""); //remove non-digits
            Log.d("albatross2", cleanShelfLife);
            convertedLife = 30 * Integer.parseInt(cleanShelfLife);
        }
        else if(shelfLife.contains("WEEK")){
            String cleanShelfLife = shelfLife.replaceAll("\\D+",""); //remove non-digits
            Log.d("albatross3", cleanShelfLife);
            convertedLife = 7 * Integer.parseInt(cleanShelfLife);
        }
        else if(shelfLife.contains("DAY")){
            String cleanShelfLife = shelfLife.replaceAll("\\D+",""); //remove non-digits
            Log.d("albatross4", cleanShelfLife);
            convertedLife = Integer.parseInt(cleanShelfLife);
        }
        else if(shelfLife.contains("INDEFINITELY")){
            String cleanShelfLife = shelfLife.replaceAll("\\D+",""); //remove non-digits
            convertedLife = 999999999;
        }
        return convertedLife;
    }
    public void saveNote(View view)
    {
        DatabaseHelper sqLiteManager = DatabaseHelper.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descEditText.getText());


        //boolean insertData = userEntryDB.addData(name, currentDate);
        if(selectedNote == null)
        {
            int id = Note.noteArrayList.size();
            Note newNote = new Note(id, title, desc);
            Note.noteArrayList.add(newNote);
            sqLiteManager.addNoteToDatabase(newNote);

            String date;
            int shelfLife = 7;
                //String nameEntered = txt.getText().toString();
                String dateEntered = dateView.getText().toString();

                final Controller aController = (Controller) getApplicationContext();
                ArrayList<Food> firebaseFoods = aController.getFood();
                for(int i = 0; i < firebaseFoods.size(); i++){
                    if(newNote.getTitle().equals(firebaseFoods.get(i).getName())){
                        date = firebaseFoods.get(i).getLife();
                        shelfLife = convertDate(date);
                        Log.d("Barney1", "ShelfLife of: " + shelfLife);
                        break;
                    }
                    else{
                        shelfLife = 7;
                    }
                }
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
