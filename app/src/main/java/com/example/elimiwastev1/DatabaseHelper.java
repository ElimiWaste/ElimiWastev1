package com.example.elimiwastev1;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.CaseMap;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



//Resources used:
//https://stackoverflow.com/questions/18097748/how-to-get-row-count-in-sqlite-using-android
//Tutorial followed to create class: https://www.youtube.com/watch?v=4k1ZMpO9Zn0&t=884s

/**
 * DatabaseHelper class stores, adds, and deletes user entered objects in a SQLite database
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseHelper; //Method of DatabaseHelper class
    private static final String TAG = "DatabaseHelper"; //Tag "DatabaseHelper"

    private static final String DATABASE_NAME = "NoteDB"; //The name of the database stored as a String
    private static final int DATABASE_VERSION = 1; //The version of the database stored as an int
    private static final String TABLE_NAME = "Note"; //The table name of the database stored as a note
    private static final String COUNTER = "Counter"; //The counter of the database stored as a String


    public static final String ID_FIELD = "ID"; //The ID column of the database stored as a String
    public static final String TITLE_FIELD = "NAME"; //The Title_Field column of the database stored as a String
    public static final String DESC_FIELD = "DATE "; //The Desc_Field column of the database stored as a String
    public static final String EX_FIELD = "EXPIRATION "; //The Ex_Field column of the database stored as a String
    private static final String DELETED_FIELD = "deleted"; //The Deleted_Field column of the database stored as a String


    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss"); //Creates date to store deleted date of object


    /**
     * Parameter constructor
     * Takes the following 1 input:
     * @param context the Context of the DatabaseHelper class, used to construct
     */
    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * instanceOfDatabase method, takes in context to create DatabaseHelper object
     * if DatabaseHelper is already constructed, it will return the databaseHelper
     * @param context keyword  used to check if reference variable contains given type of object reference
     * @return databaseHelper of type DatabaseHelper with given context
     */
    public static DatabaseHelper instanceOfDatabase(Context context) {
        if (databaseHelper == null)
            databaseHelper = new DatabaseHelper(context);

        return databaseHelper;
    }

    Calendar calendar = Calendar.getInstance(); //Creating a Calendar object

    int mYear = calendar.get(Calendar.YEAR); //Year of the Calendar
    int mMonth = calendar.get(Calendar.MONTH); //Month of the Calendar
    int mDay = calendar.get(Calendar.DAY_OF_MONTH); //Day of the Calendar

    /**
     * onCreate method
     * Creates table of DatabaseHelper with Table Name and primary key of counter with 5 columns
     * @param databaseHelper a SQLiteDatabase object
     */
    @Override
    public void onCreate(SQLiteDatabase databaseHelper) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(TITLE_FIELD)
                .append(" TEXT, ")
                .append(DESC_FIELD)
                .append(" TEXT, ")
                .append(EX_FIELD)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" TEXT)");

        databaseHelper.execSQL(sql.toString());

    }

    /**
     * If new field will be added, onUpgrade will be used
     * switches oldVersion of database to newVersion
     * @param sqLiteDatabase the sqLiteDatabase the will be switched to a new version
     * @param oldVersion the old version of the sqlite database, stored as int
     * @param newVersion the new version of the sqlite database, stored as int
     */
    //Note this class is commented out as it is needed for creating a DatabaseHelper, but is not used in the current iteration of ElimiWaste
    //We may use this method with future extensions, so we have left it here
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        switch (oldVersion) {
//          case 1:
//               sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//          case 2:
//              sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//          }
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);

    }


    /**
     * Takes object of Note class and adds to database
     * @param note user-defined note, stores ID, title, desc, expiry, and deleted date in database as 5 columns
     */
    public void addNoteToDatabase(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, note.getId());
        contentValues.put(TITLE_FIELD, note.getTitle());
        contentValues.put(DESC_FIELD, note.getDescription());
        contentValues.put(EX_FIELD, note.getExpiration()); //Added
        contentValues.put(DELETED_FIELD, getStringFromDate(note.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

    }

    /**
     * Returns all the data from database
     * @return all the data from the database
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    /**
     * Shows all the data in a database
     * @return all the data from the database
     */
    public Cursor showData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    /**
     * Returns the ID that corresponds to the input String name
     * @param name, user defined name String
     * @return
     */
    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + ID_FIELD + " FROM " + TABLE_NAME +
                " WHERE " + TITLE_FIELD + " = '" + name + "'";

        return db.rawQuery(query, null);

    }

    /**
     * Gets ID from className
     * @param className the class name stored as String
     * @return int query of ID
     */
    public int getIdFromClassName(String className) {
        String query = "SELECT ID" +
                " FROM " + ID_FIELD +
                " WHERE " + TITLE_FIELD + " = '" + className + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        int query2 = Integer.parseInt(query);

        return query2;
    }

    /**
     * Gets item name based on its corresponding ID
     * @param id the ID of the the note object
     * @return the itemName from column TITLE_FIELD
     */
    public Cursor getItemName(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TITLE_FIELD + " FROM " + TABLE_NAME +
                " WHERE " + COUNTER + " = '" + id + "'";
        return db.rawQuery(query, null);
    }

    /**
     * Gets item date based on its corresponding ID
     * @param id the ID of the note object
     * @return the item purchase date from DESC_FIELD column
     */
    public Cursor getItemDate(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + DESC_FIELD + " FROM " + TABLE_NAME +
                " WHERE " + COUNTER + " = '" + id + "'";
        return db.rawQuery(query, null);
    }


    /**
     * Gets item expiration date based on its corresponding ID
     * @param id the ID of the note object
     * @return the expiration date from EX_FIELD column
     */
    public Cursor getExpiration(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + EX_FIELD + " FROM " + TABLE_NAME +
                " WHERE " + COUNTER + " = '" + id + "'";
        return db.rawQuery(query, null);
    }

    /**
     * Gets a count of the number of rows in the current database
     * @return the number of rows as long
     */
    public long getRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }

    /**
     * method repopulates memory of database and adds Note object to noteArrayList
     */
    public void populateNoteListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String desc = result.getString(3);
                    String expiry = result.getString(4); // added
                    String stringDeleted = result.getString(5);
                    Date deleted = getDateFromString(stringDeleted);
                    Note note = new Note(id, title, desc, expiry, deleted); //added
                    Note.noteArrayList.add(note);
                }
            }
        }
    }


    /**
     * When a note is edited, a note is passed into the method to update it's columns
     * ID, title, desc, expiry, and deleted date are modified
     * @param note the note object being edited
     */
    public void updateNoteInDB(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, note.getId());
        contentValues.put(TITLE_FIELD, note.getTitle());
        contentValues.put(DESC_FIELD, note.getDescription());
        contentValues.put(EX_FIELD, note.getExpiration()); //added
        contentValues.put(DELETED_FIELD, getStringFromDate(note.getDeleted()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(note.getId())});
    }

    /**
     * Takes in a date and returns a String
     * @param date the input Date
     * @return thes the date format as String otherwise, returns null
     */
    private String getStringFromDate(Date date) {
        if (date == null)
            return null;
        return dateFormat.format(date);
    }


    /**
     * Takes String and returns date
     * @param string the input String that will return as date
     * @return dateFormat as String or null
     */
    private Date getDateFromString(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }

}

