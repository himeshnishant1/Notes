package com.example.notes.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.Nullable;

import com.example.notes.DataModel;

import java.util.ArrayList;
import java.util.Random;

public class dbHandler extends SQLiteOpenHelper {

    // below variable is for our database name.
    private static final String DB_NAME = "NotesDb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "notes_table";

    private static final String Note_ID = "id";

    private static final String NOTE_TITLE_COL = "title";

    private static final String NOTE_DATETIME_COL = "datetime";

    private static final String NOTE_MESSAGE_COL = "message";

    public dbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + Note_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOTE_TITLE_COL + " TEXT,"
                + NOTE_DATETIME_COL + " TEXT,"
                + NOTE_MESSAGE_COL + " TEXT)";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addNewNote(String title, String datetime, String message){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOTE_TITLE_COL, title);
        values.put(NOTE_DATETIME_COL, datetime);
        values.put(NOTE_MESSAGE_COL, message);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<DataModel> readNotes(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<DataModel> dataModels = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                int r = new Random().nextInt(255);
                int g = new Random().nextInt(255);
                int b = new Random().nextInt(255);
                String hex = String.format("#73%02x%02x%02x", r, g, b);
                DataModel dataModel = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataModel = new DataModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Color.parseColor(hex));
                }
                else    dataModel = new DataModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Color.GREEN);
                dataModels.add(dataModel);
            }
            while (cursor.moveToNext());
        }

        db.close();

        return dataModels;
    }

    public DataModel getSingleModel(String timeStamp){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NOTE_DATETIME_COL + " = " + "'" + timeStamp + "'", null);
        DataModel dataModel = null;

        if(cursor.moveToFirst()){
            do{
                int r = new Random().nextInt(255);
                int g = new Random().nextInt(255);
                int b = new Random().nextInt(255);
                String hex = String.format("#73%02x%02x%02x", r, g, b);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataModel = new DataModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Color.parseColor(hex));
                }
                else    dataModel = new DataModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Color.GREEN);
            }
            while (cursor.moveToNext());
        }
        db.close();

        return dataModel;
    }

    public void updateNote(String title, String datetime, String message){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOTE_TITLE_COL, title);
        values.put(NOTE_DATETIME_COL, datetime);
        values.put(NOTE_MESSAGE_COL, message);

        db.update(TABLE_NAME, values, "datetime=?", new String[]{datetime});
        db.close();
    }

    public void DeleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=" + id, null);
        db.close();
    }
}
