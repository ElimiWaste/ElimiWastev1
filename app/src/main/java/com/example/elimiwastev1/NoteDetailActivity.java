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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.lang.Long.MAX_VALUE;

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

        dateView = (TextView)findViewById((R.id.descriptionEditText));
        dateEnter = (Button) findViewById((R.id.addDate));

        dateEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                Log.d("tag", "message" + dateView.getText().toString());

                datepicker = new DatePickerDialog(NoteDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        dateView.setText(mMonth + "/" + mDayOfMonth + "/" + mYear);
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

//&& descEditText.getText().toString().contains("/")

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
            convertedLife = 0;
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

            int theLife = 0;
                String nameEntered = titleEditText.getText().toString();
                String dateEntered = dateView.getText().toString();
                Log.d("Barney0.5", "ShelfLife of: " + dateEntered);

                DateConvert convertMonthDay = new DateConvert(day, month, year);
                //converts enter date to milliseconds since the UNIX epoch
                //https://currentmillis.com/
                long dateEnteredMillis = 1000 * 60 * 60 *(24 *(convertMonthDay.monthConverter() + 365 * (year-1970))+16);

                Log.d("Barney0.6", "dateEnteredMillis of: " + dateEnteredMillis);

            final Controller aController = (Controller) getApplicationContext();
                ArrayList<Food> firebaseFoods = aController.getFood();
                for(int i = 0; i < firebaseFoods.size(); i++){
                    if(nameEntered.equalsIgnoreCase(firebaseFoods.get(i).getName())){
                        String date = firebaseFoods.get(i).getLife();
                        theLife = convertDate(date);
                        break;
                    }
                    else{
                        theLife = 0;
                    }
                }
            Toast.makeText(NoteDetailActivity.this, "Data Successfully Inserted and Reminder Set!", Toast.LENGTH_LONG).show();
            //HALFLIFE NOTIFICATION
            //Will send out a notification with a wait time determined by variable long waitTime.
            Intent intent = new Intent(NoteDetailActivity.this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            AlarmManager AlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long theLifeL = 86400000L * theLife;
            Log.d("Barney3", "theLifeL: " + theLifeL);
            Log.d("Barney3", "current time: " + System.currentTimeMillis());
            long sum = dateEnteredMillis+ theLifeL/2;
            Log.d("Barney0.6", "sum: " + sum);
            //Will wake up the device to send the notification at this time. Does not matter whether or not the application is closed.
            AlarmManager.set(android.app.AlarmManager.RTC_WAKEUP,
                    0,
                    pendingIntent);

            //TWO DAYS BEFORE NOTIFICATION
            Intent intent2 = new Intent(NoteDetailActivity.this, ReminderBroadcast2.class);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0);

            long theLifeL2 = 86400000L * (theLife-2);
            long sum2 = dateEnteredMillis + theLifeL2;
            Log.d("Barney0.6", "sum2: " + sum2);

            //Will wake up the device to send the notification at this time. Does not matter whether or not the application is closed.
            AlarmManager.set(android.app.AlarmManager.RTC_WAKEUP,
                    0,
                    pendingIntent2);
            //dateView.setText("Your food expires in " + theLife + " days");
            // selectedNote.setDescription();

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
