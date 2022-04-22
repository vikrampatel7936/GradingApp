package com.example.vikramassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "assignment2";

    // table name
    private static final String TABLE_CONTENT = "Grading";

    // column names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "firstname";
    private static final String KEY_LNAME = "lastname";
    private static final String KEY_COURSE = "course";
    private static final String KEY_CREDIT = "credit";
    private static final String KEY_GRADES = "grades";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GradeS_TABLE = "CREATE TABLE "
                + TABLE_CONTENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FNAME + " TEXT,"
                + KEY_LNAME + " TEXT,"
                + KEY_COURSE + " TEXT,"
                + KEY_CREDIT + " INTEGER,"
                + KEY_GRADES + " DOUBLE"
                + ")";
        db.execSQL(CREATE_GradeS_TABLE);
    }

    // database handling
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENT);
        onCreate(db);
    }

    //adding data to database
    public boolean addGrade(Modal grade) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, grade.fName);
        values.put(KEY_LNAME, grade.lName);
        values.put(KEY_COURSE, grade.course);
        values.put(KEY_CREDIT, grade.credit);
        values.put(KEY_GRADES, grade.grades);

        long result = db.insert(TABLE_CONTENT, null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }


    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Grading where id=" + id + "", null);
        return res;
    }

    // getting grades using program code
    public Cursor getAllGradesWithSubjectCode(String code) {

        SQLiteDatabase db = this.getWritableDatabase();
        List<Modal> gradeList = new ArrayList<Modal>();

        String query = "SELECT * FROM Grading WHERE course = '" + code + "'";

        Cursor cursor = db.rawQuery(query, null);

        return cursor;

    }


    // getting all grades
    public List<Modal> getAllGrades() {
        List<Modal> gradeList = new ArrayList<Modal>();

        String selectQuery = "SELECT * FROM " + TABLE_CONTENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Modal grade = new Modal();
                grade.fName = cursor.getString(1);
                grade.lName = cursor.getString(2);
                grade.course = cursor.getString(3);
                grade.credit = cursor.getInt(4);
                grade.grades = cursor.getInt(5);

                gradeList.add(grade);
            } while (cursor.moveToNext());
        }

        return gradeList;
    }

}
