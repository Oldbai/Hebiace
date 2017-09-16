package com.oldbai.android.app.hebiace.data.source;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BaiGuoyong on 9/5/2017.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "User.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserPersistenceContract.DbGradeEntry.TABLE_NAME + " (" +
                    UserPersistenceContract.DbGradeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_ID + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_STUDENTID + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_YEAR + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_TERM + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_ID + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_NAME + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_ARR + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_BELONGTO + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_CREDIT + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_GPA + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REGULAR_GRADE + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MIDTERM_GRADE + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_FINAL_GRADE + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_EXPERIMENTAL_GRADE + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_TOTAL_GRADE + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MINOR_MARK + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MAKEUP_GRADE + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REBUILD_GRADE + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_BELONG_TO_COLLEGE + ", " +
                    UserPersistenceContract.DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REBUILD_MARK + ")";


    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
