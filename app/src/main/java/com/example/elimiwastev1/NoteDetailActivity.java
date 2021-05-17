package com.example.elimiwastev1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

    private EditText titleEditText;
    private Button deleteButton;
    private Note selectedNote;

    //Purchase Date Vars
    TextView dateView;
    Button dateEnter;
    Calendar calendar;
    DatePickerDialog datepicker;

    int day;
    int month;
    int year;
    int theDay;
    int theMonth;
    int theYear;


    //Expiration Date Vars
    TextView dateView2;
    Button dateEnter2;
    Calendar calendar2;
    DatePickerDialog datepicker2;

    int day2;
    int month2;
    int year2;
    int theDayExpire;
    int theMonthExpire;
    int theYearExpire;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initWidgets();
        checkForEditNote();

        final AutoCompleteTextView foodEntry = (AutoCompleteTextView)findViewById(R.id.titleEditText);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoListMaker());
        foodEntry.setAdapter(adapter);

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
                        theDay = mDayOfMonth;
                        theMonth = mMonth+1;
                        theYear = mYear;
                        dateView.setText(mMonth + 1 + "/" + mDayOfMonth + "/" + mYear);
                    }
                },year, month, day);
                datepicker.show();
            }
        });

        dateView2 = (TextView)findViewById((R.id.exEditText));
        dateEnter2 = (Button) findViewById((R.id.addEx));

        dateEnter2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                DateConvert convertEnterDate = new DateConvert(theDay, theMonth, theYear);
                long dateEnteredMillis = 43200000L + 86400000L * (convertEnterDate.monthAndDayConverter() + convertEnterDate.yearConverter());
                calendar2 = Calendar.getInstance();
                day2 = calendar2.get(Calendar.DAY_OF_MONTH);
                month2 = calendar2.get(Calendar.MONTH);
                year2 = calendar2.get(Calendar.YEAR);
                Log.d("tag", "message" + dateView2.getText().toString());

                datepicker2 = new DatePickerDialog(NoteDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        theDayExpire = mDayOfMonth;
                        theMonthExpire = mMonth+1;
                        theYearExpire = mYear;
                        dateView2.setText(mMonth + 1 + "/" + mDayOfMonth + "/" + mYear);
                    }
                },year2, month2, day2);
                datepicker2.getDatePicker().setMinDate(dateEnteredMillis);
                datepicker2.show();
            }
        });



    }

    private String[] autoListMaker(){
        final Controller aController = (Controller) getApplicationContext();
        ArrayList<Food> firebaseFoods = aController.getFood();
        int size = firebaseFoods.size();
        String[] foodList = new String[size];
        for(int i = 0; i < firebaseFoods.size(); i++){
            foodList[i] = firebaseFoods.get(i).getName();
        }
        return foodList;
    }


    private void initWidgets()
    {
        titleEditText = findViewById(R.id.titleEditText);
        dateView = findViewById((R.id.descriptionEditText)); //??? - edited but not sure
        dateView2 = (TextView)findViewById((R.id.exEditText)); // added
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
            dateView.setText(selectedNote.getDescription()); //Changed
            dateView2.setText(selectedNote.getExpiration()); //added
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
        String desc = String.valueOf(dateView.getText()); //changed
        String expiry = String.valueOf(dateView2.getText()); //added

        //boolean insertData = userEntryDB.addData(name, currentDate);
        if(selectedNote == null) {
            if (desc.isEmpty()) {
                Toast.makeText(NoteDetailActivity.this, "Purchase Date not found! Item not saved! Please re-enter entry.", Toast.LENGTH_LONG).show();

            }
            else {
                int id = Note.noteArrayList.size();
                Note newNote = new Note(id, title, desc, expiry);
                Note.noteArrayList.add(newNote);
                sqLiteManager.addNoteToDatabase(newNote);


                int theLife = -1;
                String nameEntered = titleEditText.getText().toString();
                String dateEntered = dateView.getText().toString();
                String expiryEntered = dateView2.getText().toString(); //added

                DateConvert convertEnterDate = new DateConvert(theDay, theMonth, theYear);
                DateConvert convertExpireDate = new DateConvert(theDayExpire, theMonthExpire, theYearExpire);
                final Controller aController = (Controller) getApplicationContext();

            //converts enter date at 12 noon to milliseconds since the UNIX epoch
            //https://currentmillis.com/
            long dateEnteredMillis = 43200000L + 86400000L * (convertEnterDate.monthAndDayConverter() + convertEnterDate.yearConverter());
            long dateExpireMillis =  86400000L * (convertExpireDate.monthAndDayConverter() + convertExpireDate.yearConverter());

                Log.d("Barney0.6", "dateEnteredMillis of: " + dateEnteredMillis);
                Log.d("Barney0.6", "dateExpireMillis of: " + dateExpireMillis);

                ArrayList<Food> firebaseFoods = aController.getFood();
                for (int i = 0; i < firebaseFoods.size(); i++) {
                    if (nameEntered.equalsIgnoreCase(firebaseFoods.get(i).getName())) {
                        String foodAndDateFirebase = firebaseFoods.get(i).getLife();
                        theLife = convertEnterDate.convertInputDate(foodAndDateFirebase);
                        break;
                    }
                }
                Log.d("theLifeL", String.valueOf(expiryEntered));
            long theLifeL = 86400000L * theLife;

            if(!expiryEntered.isEmpty()){
                theLifeL = dateExpireMillis - dateEnteredMillis;
            }

            Log.d("theLifeL", String.valueOf(theLifeL));
            Toast.makeText(NoteDetailActivity.this, "Data Successfully Inserted and Reminder Set!", Toast.LENGTH_LONG).show();
            //HALFLIFE NOTIFICATION
            //Will send out a notification with a wait time determined by variable long waitTime.
            Intent intent = new Intent(NoteDetailActivity.this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
            AlarmManager AlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            //Will wake up the device to send the notification at this time. Does not matter whether or not the application is closed.
            AlarmManager.set(android.app.AlarmManager.RTC_WAKEUP,
                    NotificationsLogic.halfLifeNotif(theLifeL, dateEnteredMillis),
                    pendingIntent);

            //TWO DAYS BEFORE NOTIFICATION
                Intent intent2 = new Intent(NoteDetailActivity.this, ReminderBroadcast2.class);
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, id, intent2, 0);

                //Will wake up the device to send the notification at this time. Does not matter whether or not the application is closed.
                AlarmManager.set(android.app.AlarmManager.RTC_WAKEUP,
                        NotificationsLogic.twoDayNotif(theLifeL, dateEnteredMillis),
                        pendingIntent2);
            }
        }

        else
        {
            selectedNote.setTitle(title);
            selectedNote.setDescription(desc);
            selectedNote.setExpiration(expiry); //added
            sqLiteManager.updateNoteInDB(selectedNote);
        }
        finish();
    }

    /**
     * Deletes a food item along with the corresponding notification
     * @param view
     */
    public void deleteNote(View view)
    {
        int id = selectedNote.getId();

        //Should delete the halfLife notification
        Intent myIntent = new Intent(NoteDetailActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NoteDetailActivity.this, id, myIntent,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        //Should delete the two-days before notification
        Intent myIntent2 = new Intent(NoteDetailActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(NoteDetailActivity.this, id, myIntent2,0);
        AlarmManager alarmManager2 = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager2.cancel(pendingIntent2);

        selectedNote.setDeleted(new Date());
        DatabaseHelper sqLiteManager = DatabaseHelper.instanceOfDatabase(this);
        sqLiteManager.updateNoteInDB(selectedNote);

        finish();
    }
}
