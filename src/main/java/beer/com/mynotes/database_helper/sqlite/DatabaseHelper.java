package beer.com.mynotes.database_helper.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import beer.com.mynotes.Constants.Constants;
import beer.com.mynotes.Model.Note;


/**
 * Created by 1405473 on 18-04-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //Create the album table
            db.execSQL("CREATE TABLE " + Constants.USER_TABLE
                    + "(" + Constants.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Constants.USER_EMAIL + " TEXT, "
                    + Constants.USER_PASS + " TEXT )");

            //Create the image table
            db.execSQL("CREATE TABLE " + Constants.NOTES_TABLE
                    + "(" + Constants.NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Constants.NOTE_DATE + " TEXT, "
                    + Constants.NOTE + " TEXT )");

        } catch (SQLException e) {
            Log.d("Database_CREATE", e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.NOTES_TABLE);
        onCreate(db);
    }

    public int updateNote(String note, long id) {
        Log.d("updateNotePos :", String.valueOf(id));
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.NOTE, note);
        cv.put(Constants.NOTE_DATE, setInitialDate());
        return db.update(Constants.NOTES_TABLE, cv, Constants.NOTE_ID + "= '" + id + "'", null);
    }

    public ArrayList<Note> getNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cur = db.rawQuery("SELECT * FROM " + Constants.NOTES_TABLE, null);
            ArrayList<Note> noteList = new ArrayList<>();
            if (cur != null) {
                cur.moveToLast();
                while (!cur.isBeforeFirst()) {
                    Note note = new Note();
                    note.setText(cur.getString(cur.getColumnIndex(Constants.NOTE)));
                    note.setId(cur.getLong(cur.getColumnIndex(Constants.NOTE_ID)));
                    note.setDate(cur.getString(cur.getColumnIndex(Constants.NOTE_DATE)));
                    cur.moveToPrevious();
                    noteList.add(note);
                }
                cur.close();
                return noteList;
            } else {
                cur.close();
                return null;
            }
        } catch (Exception e) {
            Log.d("GET NOTES", e.getMessage());
            return null;
        }
    }

    public long addNote() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.NOTE_DATE, setInitialDate());
        return db.insert(Constants.NOTES_TABLE, null, cv);
    }


    private String setInitialDate() {
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("E hh:mm a");
        return df.format(calendar.getTime());
    }

    public long deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Constants.NOTES_TABLE, Constants.NOTE_ID + "= ?", new String[]{String.valueOf(id)});
    }
}
