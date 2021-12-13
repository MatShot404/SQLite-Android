package com.example.questionnaire2;

import android.content.ContentValues;
import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "questionnaire.db";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "participants";

    // below variable is for our id column.
    private static final String ID_PARTICIPANT = "id";

    // below variable is for our course name column
    private static final String NAME_PARTICIPANT = "name";

    // below variable is for our course duration column.
    private static final String SCORE_PARTICIPANT = "score";

    // below variable for our course description column.
    private static final String DATE_SCORE_PARTICIPANT = "date";

    private static final String PASSWORD = "";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {

        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_PARTICIPANT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_PARTICIPANT + " TEXT,"
                + SCORE_PARTICIPANT + " INTEGER,"
                + DATE_SCORE_PARTICIPANT + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewParticipant(String participantName, Integer participantScore) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase(PASSWORD);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String participantDateScore = df.format(c);

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_PARTICIPANT, participantName);
        values.put(SCORE_PARTICIPANT, participantScore);
        values.put(DATE_SCORE_PARTICIPANT, participantDateScore);


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}