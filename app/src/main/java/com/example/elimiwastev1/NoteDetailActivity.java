package com.example.elimiwastev1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
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

//Tutorial followed to create class: https://www.youtube.com/watch?v=4k1ZMpO9Zn0&t=884s
public class NoteDetailActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initWidgets();
        //Checks if editing note or creating new one
        checkForEditNote();

        //Autocomplete on food entering feature
        final AutoCompleteTextView foodEntry = (AutoCompleteTextView) findViewById(R.id.titleEditText);
        //Makes the list for the autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoListMaker());
        foodEntry.setAdapter(adapter);

        //Buttons for the first calendar
        dateView = (TextView) findViewById((R.id.descriptionEditText));
        dateEnter = (Button) findViewById((R.id.addDate));

        //On click, the calendar to enter the date of purchase pops up
        dateEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gets instance of calendar
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                //Sets up the datepicker for the user to chose the date of purchase
                datepicker = new DatePickerDialog(NoteDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    /**
                     * Once the user chooses the date, the textview will update with the date chosen. Also variables that will be used for notifications are defined
                     */
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        theDay = mDayOfMonth;
                        theMonth = mMonth + 1;
                        theYear = mYear;
                        dateView.setText(mMonth + 1 + "/" + mDayOfMonth + "/" + mYear);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });
        //Second calendar has mostly same things as first. This calendar is for the expiration dates
        dateView2 = (TextView) findViewById((R.id.exEditText));
        dateEnter2 = (Button) findViewById((R.id.addEx));

        dateEnter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateEntered = dateView.getText().toString();
                //Checks if the user is editing or making a new entry. If they are editing, then the restriction on what the user can select has to be parsed from the user entered purchase date
                if (!dateEntered.isEmpty()) {
                    String[] dayMonthYearEntered = dateEntered.split("/");
                    theDay = Integer.parseInt(dayMonthYearEntered[1]);
                    theMonth = Integer.parseInt(dayMonthYearEntered[0]);
                    theYear = Integer.parseInt(dayMonthYearEntered[2]);
                }
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
                        theMonthExpire = mMonth + 1;
                        theYearExpire = mYear;
                        dateView2.setText(mMonth + 1 + "/" + mDayOfMonth + "/" + mYear);
                    }
                }, year2, month2, day2);
                //The dates the user can pick have to be the same day or after the date of purchase
                datepicker2.getDatePicker().setMinDate(dateEnteredMillis);
                datepicker2.show();
            }
        });


    }

    /**
     * @return
     */
    private String[] autoListMaker() {
        final Controller aController = (Controller) getApplicationContext();
        ArrayList<Food> firebaseFoods = aController.getFood();
        int size = firebaseFoods.size();
        String[] foodList = new String[size];
        for (int i = 0; i < firebaseFoods.size(); i++) {
            foodList[i] = firebaseFoods.get(i).getName();
        }
        return foodList;
    }


    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        dateView = findViewById((R.id.descriptionEditText)); //??? - edited but not sure
        dateView2 = (TextView) findViewById((R.id.exEditText)); // added
        deleteButton = findViewById(R.id.deleteNoteButton);


    }

    /**
     * Checks if a note is being edited or not.
     */
    private void checkForEditNote() {
        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA, -1);
        selectedNote = Note.getNoteForID(passedNoteID);

//&& descEditText.getText().toString().contains("/")

        if (selectedNote != null) {
            titleEditText.setText(selectedNote.getTitle());
            dateView.setText(selectedNote.getDescription()); //Changed
            dateView2.setText(selectedNote.getExpiration()); //added
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Saves the current note to the SQL database and sets up the notifications with Alarmmanagers
     *
     * @param view
     */
    public void saveNote(View view) {
        //database object
        DatabaseHelper sqLiteManager = DatabaseHelper.instanceOfDatabase(this);
        //the name of the food
        String title = String.valueOf(titleEditText.getText());
        //the date purchased
        String desc = String.valueOf(dateView.getText()); //changed
        //the date of expiration
        String expiry = String.valueOf(dateView2.getText()); //added
        //Checks if editing or saving a new note
        if (selectedNote == null) {

            String nameEntered = titleEditText.getText().toString();
            DateConvert convertEnterDate = new DateConvert(theDay, theMonth, theYear);
            //If the food is not found in the database, then this will be -1 at the end of the for loop
            int theLife = -1;
            final Controller aController = (Controller) getApplicationContext();
            ArrayList<Food> firebaseFoods = aController.getFood();
            //Checks for the corresponding food from the firebase
            for (int i = 0; i < firebaseFoods.size(); i++) {
                if (nameEntered.equalsIgnoreCase(firebaseFoods.get(i).getName())) {
                    String foodAndDateFirebase = firebaseFoods.get(i).getLife();
                    theLife = convertEnterDate.convertInputDate(foodAndDateFirebase);
                    break;
                }
            }
            //Checks if the user is entering in a date/food item and also if there is a valid expiration date to be calculated.
            if (desc.isEmpty() || title.isEmpty() || (theLife == -1 && expiry.isEmpty())) {
                Toast.makeText(NoteDetailActivity.this, "Purchase Date or Food Name or Expiration Date not found! Item not saved! Please re-enter with all the necessary information!", Toast.LENGTH_LONG).show();
            } else {
                //Saves the current note to the SQL database
                int id = Note.noteArrayList.size();
                Note newNote = new Note(id, title, desc, expiry);
                Note.noteArrayList.add(newNote);
                sqLiteManager.addNoteToDatabase(newNote);

                //If no expiration date is provided and the food is not found in the database, theLife will = -1
                //Sets the
                String expiryEntered = dateView2.getText().toString(); //added
                DateConvert convertExpireDate = new DateConvert(theDayExpire, theMonthExpire, theYearExpire);

                //converts enter date at 12 noon to milliseconds since the UNIX epoch
                //https://currentmillis.com/
                long dateEnteredMillis = 43200000L + 86400000L * (convertEnterDate.monthAndDayConverter() + convertEnterDate.yearConverter());
                //converts expire date into its corresponding milliseconds since UNIX epoch
                long dateExpireMillis = 86400000L * (convertExpireDate.monthAndDayConverter() + convertExpireDate.yearConverter());
                Log.d("Barney0.6", "dateEnteredMillis of: " + dateEnteredMillis);
                Log.d("Barney0.6", "dateExpireMillis of: " + dateExpireMillis);
                Log.d("theLifeL", String.valueOf(expiryEntered));
                //Converts the life of the food into milliseconds
                long theLifeL = 86400000L * theLife;
                //Checks if there is an entry for the expiration. If there is, then the life of the food is set to the that
                if (!expiryEntered.isEmpty()) {
                    theLifeL = dateExpireMillis - dateEnteredMillis;
                }
                Log.d("theLifeL", String.valueOf(theLifeL));
                //Lets the user knows that their efforts were successful
                Toast.makeText(NoteDetailActivity.this, "Data Successfully Inserted and Reminder Set!", Toast.LENGTH_LONG).show();

                //HALFLIFE NOTIFICATION
                //Will wake up the device to send the notification at the time determined by NotificationsLogic.halfLifeNotif(theLifeL, dateEnteredMillis) time. Does not matter whether or not the application is closed.
                Intent intent = new Intent(NoteDetailActivity.this, HalfLifeNotif.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
                AlarmManager AlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                //Will wake up the device to send the notification at this time. Does not matter whether or not the application is closed.
                AlarmManager.set(android.app.AlarmManager.RTC_WAKEUP,
                        NotificationsLogic.halfLifeNotif(theLifeL, dateEnteredMillis),
                        pendingIntent);

                Log.d("Barney1.4", String.valueOf(NotificationsLogic.halfLifeNotif(theLifeL, dateEnteredMillis)));

                //TWO DAYS BEFORE NOTIFICATION
                Intent intent2 = new Intent(NoteDetailActivity.this, TwoDayNotif.class);
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, id, intent2, 0);

                //Will wake up the device to send the notification at the time determined by NotificationsLogic.twoDayNotif(theLifeL, dateEnteredMillis) time. Does not matter whether or not the application is closed.
                AlarmManager.set(android.app.AlarmManager.RTC_WAKEUP,
                        NotificationsLogic.twoDayNotif(theLifeL, dateEnteredMillis),
                        pendingIntent2);

                Log.d("Barney1.4", String.valueOf(NotificationsLogic.twoDayNotif(theLifeL, dateEnteredMillis)));
            }
        }
//If the user is editing an entry instead of making a new one
        else {
            //Updates the SQL database with the new user provided information
            selectedNote.setTitle(title);
            selectedNote.setDescription(desc);
            selectedNote.setExpiration(expiry); //added
            sqLiteManager.updateNoteInDB(selectedNote);
            //Gets the ID of the selected note
            int id = selectedNote.getId();
            //Same reasoning as above
            int theLife = -1;

            String nameEntered = titleEditText.getText().toString();
            String dateEntered = dateView.getText().toString();
            String expiryEntered = dateView2.getText().toString();


            String[] dayMonthYearEntered = dateEntered.split("/");
            String[] dayMonthYearExpire = expiryEntered.split("/");

            //Gets the user input even if Calendar is not pressed
            theDay = Integer.parseInt(dayMonthYearEntered[1]);
            theMonth = Integer.parseInt(dayMonthYearEntered[0]);
            theYear = Integer.parseInt(dayMonthYearEntered[2]);

            //Gets the user input even if Calendar is not pressed
            theDayExpire = Integer.parseInt(dayMonthYearExpire[1]);
            theMonthExpire = Integer.parseInt(dayMonthYearExpire[0]);
            theYearExpire = Integer.parseInt(dayMonthYearExpire[2]);

            //Converts enter and expire date into millis
            DateConvert convertEnterDate = new DateConvert(theDay, theMonth, theYear);
            DateConvert convertExpireDate = new DateConvert(theDayExpire, theMonthExpire, theYearExpire);

            final Controller aController = (Controller) getApplicationContext();
            //converts enter date at 12 noon to milliseconds since the UNIX epoch
            //https://currentmillis.com/
            long dateEnteredMillis = 43200000L + 86400000L * (convertEnterDate.monthAndDayConverter() + convertEnterDate.yearConverter());
            //converts expire date into milliseconds
            long dateExpireMillis = 86400000L * (convertExpireDate.monthAndDayConverter() + convertExpireDate.yearConverter());

            Log.d("Barney0.6", "dateEnteredMillis of: " + dateEnteredMillis);
            Log.d("Barney0.6", "dateExpireMillis of: " + dateExpireMillis);
            //Same as above. No checking needed as the user must have already entered all the necessary information for this part to be able to run
            ArrayList<Food> firebaseFoods = aController.getFood();
            for (int i = 0; i < firebaseFoods.size(); i++) {
                if (nameEntered.equalsIgnoreCase(firebaseFoods.get(i).getName())) {
                    String foodAndDateFirebase = firebaseFoods.get(i).getLife();
                    theLife = convertEnterDate.convertInputDate(foodAndDateFirebase);
                    break;
                }
            }

            Log.d("theLifeL", String.valueOf(expiryEntered));
            //Same as above
            long theLifeL = 86400000L * theLife;

            if (!expiryEntered.isEmpty()) {
                theLifeL = dateExpireMillis - dateEnteredMillis;
            }

            Log.d("theLifeL", String.valueOf(theLifeL));
            Toast.makeText(NoteDetailActivity.this, "Changes saved successfully!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(NoteDetailActivity.this, HalfLifeNotif.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
            AlarmManager AlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            //Will wake up the device to send the notification at this time. Does not matter whether or not the application is closed.
            AlarmManager.set(android.app.AlarmManager.RTC_WAKEUP,
                    NotificationsLogic.halfLifeNotif(theLifeL, dateEnteredMillis),
                    pendingIntent);
            Log.d("Barney1.5", String.valueOf(NotificationsLogic.halfLifeNotif(theLifeL, dateEnteredMillis)));

            Intent intent2 = new Intent(NoteDetailActivity.this, TwoDayNotif.class);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, id, intent2, 0);

            AlarmManager.set(android.app.AlarmManager.RTC_WAKEUP,
                    NotificationsLogic.twoDayNotif(theLifeL, dateEnteredMillis),
                    pendingIntent2);
            Log.d("Barney1.5", String.valueOf(NotificationsLogic.twoDayNotif(theLifeL, dateEnteredMillis)));
            Log.d("This is a test", "I said this is a test");
        }
        finish();
    }

    /**
     * Deletes a food item along with the corresponding notification
     *
     * @param view
     */
    public void deleteNote(View view) {
        int id = selectedNote.getId();

        //Should delete the halfLife notification
        Intent myIntent = new Intent(NoteDetailActivity.this, HalfLifeNotif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NoteDetailActivity.this, id, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        //Should delete the two-days before notification
        Intent myIntent2 = new Intent(NoteDetailActivity.this, HalfLifeNotif.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(NoteDetailActivity.this, id, myIntent2, 0);
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager2.cancel(pendingIntent2);

        selectedNote.setDeleted(new Date());
        DatabaseHelper sqLiteManager = DatabaseHelper.instanceOfDatabase(this);
        sqLiteManager.updateNoteInDB(selectedNote);

        finish();
    }
}
