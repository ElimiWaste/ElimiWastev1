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
import java.util.Date;

// import androidx.annotation.Nullable;
// TODO: Add one more date field

// Update 2
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseHelper;
    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "NoteDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Note";
    private static final String COUNTER = "Counter";


    public static final String ID_FIELD = "ID";
    public static final String TITLE_FIELD = "NAME";
    public static final String DESC_FIELD = "DATE ";
    public static final String EX_FIELD = "EXPIRATION "; //added
    private static final String DELETED_FIELD = "deleted";


    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper instanceOfDatabase(Context context) {
        if (databaseHelper == null)
            databaseHelper = new DatabaseHelper(context);

        return databaseHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase databaseHelper) {
      /*  String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NAME TEXT, DATE TEXT)";
        db.execSQL(createTable); */

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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //  switch (oldVersion) {
        //   case 1:
        //        sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
        //     case 2:
        //        sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
        //   }
          /*  db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); */

    }

  /*  public boolean addData(Note note) {
      /*  SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, note.getId());
        contentValues.put(TITLE_FIELD, note.getTitle());
        contentValues.put(DESC_FIELD, note.getDescription());
        contentValues.put(DELETED_FIELD, getStringFromDate(note.getDeleted()));

        db.insert(TABLE_NAME, null, contentValues); */
          /* long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
        return result != -1;


        Log.d(TAG, "addData: Adding " + note.getTitle() + " to " + TABLE_NAME);


    } */


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
     *
     * @return
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public Cursor showData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    /**
     * Returns the ID that corresponds to the input String name
     *
     * @param name, user defined name String
     * @return
     */
    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + ID_FIELD + " FROM " + TABLE_NAME +
                " WHERE " + TITLE_FIELD + " = '" + name + "'";

        return db.rawQuery(query, null);

    }

    public int getIdFromClassName(String className) {
        String query = "SELECT ID" +
                " FROM " + ID_FIELD +
                " WHERE " + TITLE_FIELD + " = '" + className + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        int query2 = Integer.parseInt(query);

        return query2;
    }

    public Cursor getItemName(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TITLE_FIELD + " FROM " + TABLE_NAME +
                " WHERE " + ID_FIELD + " = '" + id + "'";
        return db.rawQuery(query, null);
    }


    public long getRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }

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

    private String getStringFromDate(Date date) {
        if (date == null)
            return null;
        return dateFormat.format(date);
    }

    public Cursor getItemDate(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + DESC_FIELD + " FROM " + TABLE_NAME +
                " WHERE " + ID_FIELD + " = '" + id + "'";
        return db.rawQuery(query, null);
    }


    private Date getDateFromString(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }

    public Cursor getItemName(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TITLE_FIELD + " FROM " + TABLE_NAME +
                " WHERE " + ID_FIELD + " = '" + id + "'";
        return db.rawQuery(query, null);
    }


    //added Getter Method
    public Cursor getExpiration(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + EX_FIELD + " FROM " + TABLE_NAME +
                " WHERE " + ID_FIELD + " = '" + id + "'";
        return db.rawQuery(query, null);
    }


}

