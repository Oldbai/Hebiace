package com.oldbai.android.app.hebiace.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.oldbai.android.app.hebiace.data.Grade;
import com.oldbai.android.app.hebiace.data.School;
import com.oldbai.android.app.hebiace.data.StudentInfo;
import com.oldbai.android.app.hebiace.data.source.UserPersistenceContract.DbGradeEntry;
import com.oldbai.android.app.hebiace.util.JsoupHtml;
import com.oldbai.android.app.hebiace.util.OkHttpUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.support.v4.util.Preconditions.checkNotNull;
import static com.oldbai.android.app.hebiace.data.source.UserPersistenceContract.PrefUserInfo.STUDENT_INFO_CLASS;
import static com.oldbai.android.app.hebiace.data.source.UserPersistenceContract.PrefUserInfo.STUDENT_INFO_COLLEGE;
import static com.oldbai.android.app.hebiace.data.source.UserPersistenceContract.PrefUserInfo.STUDENT_INFO_ID;
import static com.oldbai.android.app.hebiace.data.source.UserPersistenceContract.PrefUserInfo.STUDENT_INFO_MAJOR;
import static com.oldbai.android.app.hebiace.data.source.UserPersistenceContract.PrefUserInfo.STUDENT_INFO_NAME;
import static com.oldbai.android.app.hebiace.data.source.UserPersistenceContract.PrefUserInfo.STUDENT_INFO_PWD;
import static com.oldbai.android.app.hebiace.data.source.UserPersistenceContract.PrefUserInfo.STUDENT_INFO_SCHOOL;
import static com.oldbai.android.app.hebiace.data.source.UserPersistenceContract.PrefUserInfo.STUDENT_INFO_SCHOOL_EMNU;

/**
 * Created by BaiGuoyong on 9/5/2017.
 */

public class UserRepository implements UserDataSource {

    private static UserRepository INSTANTCE = null;

    private UserDbHelper mDbHelper;

    private StudentInfo mStudentInfo;

    private Context mContext;

    public UserRepository(Context context) {
        mContext = context;
        checkNotNull(context);
        mDbHelper = new UserDbHelper(context);
        mStudentInfo = getStudentInfo();
    }

    public static UserRepository getInstance(Context context) {
        if (INSTANTCE == null) {
            INSTANTCE = new UserRepository(context);
        }
        return INSTANTCE;
    }

    public static void destroyInstance() {
        INSTANTCE = null;
    }

    @Override
    public List<String> getAllYearList() {
        List<String> years = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.query(true, DbGradeEntry.TABLE_NAME, new String[]{DbGradeEntry.COLUMN_NAME_GRADE_YEAR},
                null, null,
                DbGradeEntry.COLUMN_NAME_GRADE_YEAR,
                null,
                null,
                null);
        if (c != null && c.getCount() > 0) {

            while (c.moveToNext()) {
                years.add(c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_YEAR)));
            }
        }

        if (c != null) {
            c.close();
        }
        db.close();
        if (years.isEmpty()) {
            return null;
        }
        return years;
    }

    @Override
    public void deleteAllGrades() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(DbGradeEntry.TABLE_NAME, null, null);

    }


    @Override
    public void saveGrades(Map<String, Grade> gradeMap) {
        checkNotNull(gradeMap);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        for (Map.Entry<String, Grade> entry : gradeMap.entrySet()) {
            ContentValues values = new ContentValues();
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_ID, entry.getValue().getId());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_STUDENTID, entry.getValue().getStudentId());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_YEAR, entry.getValue().getYear());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_TERM, entry.getValue().getTerm());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_ID, entry.getValue().getCourseId());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_NAME, entry.getValue().getCourseName());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_ARR, entry.getValue().getCourseArr());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_BELONGTO, entry.getValue().getCourseBelongTo());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_CREDIT, entry.getValue().getCourseCredit());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_GPA, entry.getValue().getCourseGPA());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REGULAR_GRADE, entry.getValue().getCourseRegularGrade());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MIDTERM_GRADE, entry.getValue().getCourseMidtermGrade());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_FINAL_GRADE, entry.getValue().getCourseFinalGrade());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_EXPERIMENTAL_GRADE, entry.getValue().getCourseExperimentalGrade());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_TOTAL_GRADE, entry.getValue().getCourseTotalGrade());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MINOR_MARK, entry.getValue().getCourseMinorMark());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MAKEUP_GRADE, entry.getValue().getCourseMakeupGrade());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REBUILD_GRADE, entry.getValue().getCourseRebuildGrade());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_BELONG_TO_COLLEGE, entry.getValue().getCourseBelongToCollege());
            values.put(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REBUILD_MARK, entry.getValue().getCourseRebuildMark());
            db.insert(DbGradeEntry.TABLE_NAME, null, values);
        }
        db.close();
    }

    @Override
    public List<Grade> getGradeWithYear(String year) {
        List<Grade> grades = new ArrayList<Grade>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DbGradeEntry.COLUMN_NAME_GRADE_YEAR,
                DbGradeEntry.COLUMN_NAME_GRADE_TERM,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_ID,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_NAME,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_ARR,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_BELONGTO,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_CREDIT,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_GPA,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REGULAR_GRADE,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MIDTERM_GRADE,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_FINAL_GRADE,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_EXPERIMENTAL_GRADE,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_TOTAL_GRADE,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MINOR_MARK,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MAKEUP_GRADE,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REBUILD_GRADE,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_BELONG_TO_COLLEGE,
                DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REBUILD_MARK
        };

        String selection = DbGradeEntry.COLUMN_NAME_GRADE_YEAR + " LIKE ?";
        String[] selectionArgs = { year };

        Cursor c = db.query(
                DbGradeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);


        if (c != null && c.getCount() > 0) {
            while(c.moveToNext()) {
                Grade grade = new Grade(null);
                grade.setYear(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_YEAR))
                );
                grade.setTerm(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_TERM))
                );
                grade.setCourseId(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_ID))
                );
                grade.setCourseName(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_NAME))
                );
                grade.setCourseArr(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_ARR))
                );
                grade.setCourseBelongTo(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_BELONGTO))
                );
                grade.setCourseCredit(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_CREDIT))
                );
                grade.setCourseGPA(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_GPA))
                );
                grade.setCourseRegularGrade(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REGULAR_GRADE))
                );
                grade.setCourseMidtermGrade(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MIDTERM_GRADE))
                );
                grade.setCourseExperimentalGrade(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_EXPERIMENTAL_GRADE))
                );
                grade.setCourseExperimentalGrade(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_FINAL_GRADE))
                );
                grade.setCourseTotalGrade(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_TOTAL_GRADE))
                );
                grade.setCourseMinorMark(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MINOR_MARK))
                );
                grade.setCourseMakeupGrade(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_MAKEUP_GRADE))
                );
                grade.setCourseRebuildGrade(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REBUILD_GRADE))
                );
                grade.setCourseBelongToCollege(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_BELONG_TO_COLLEGE))
                );
                grade.setCourseRebuildMark(
                        c.getString(c.getColumnIndexOrThrow(DbGradeEntry.COLUMN_NAME_GRADE_COURSE_REBUILD_MARK))
                );
                grades.add(grade);
            }
        }

        if (c != null) {
            c.close();
        }
        db.close();
        if (grades.isEmpty()) {
            return null;
        }

        return grades;
    }

    @Override
    public void saveStudentInfo(StudentInfo studentInfo) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("Userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(STUDENT_INFO_ID, studentInfo.getStudentId());
        editor.putString(STUDENT_INFO_PWD, studentInfo.getStudentPwd());
        editor.putString(STUDENT_INFO_SCHOOL_EMNU, studentInfo.getSchoolEmnu().toString());
        editor.putString(STUDENT_INFO_SCHOOL, studentInfo.getSchoolName());
        editor.putString(STUDENT_INFO_NAME, studentInfo.getName());
        editor.putString(STUDENT_INFO_MAJOR, studentInfo.getMajor());
        editor.putString(STUDENT_INFO_CLASS, studentInfo.getMyClass());
        editor.putString(STUDENT_INFO_COLLEGE, studentInfo.getCollege());

        editor.commit();
    }

    @Override
    public StudentInfo getStudentInfo() {
        StudentInfo studentInfo = new StudentInfo();
        SharedPreferences manager = mContext.getSharedPreferences("Userinfo", Context.MODE_PRIVATE);
        studentInfo.setStudentId(manager.getString(STUDENT_INFO_ID, null));
        studentInfo.setStudentPwd( manager.getString(STUDENT_INFO_PWD, null));
        studentInfo.setSchoolEmnu(
                School.valueOf(manager.
                        getString(STUDENT_INFO_SCHOOL_EMNU,
                                School.HEBEINU.toString()))
        );
        studentInfo.setSchoolName(manager.getString(STUDENT_INFO_SCHOOL, null));
        studentInfo.setName(manager.getString(STUDENT_INFO_NAME, null));
        studentInfo.setMajor(manager.getString(STUDENT_INFO_MAJOR, null));
        studentInfo.setMyClass(manager.getString(STUDENT_INFO_CLASS, null));
        studentInfo.setCollege(manager.getString(STUDENT_INFO_COLLEGE, null));

        return studentInfo;
    }

    @Override
    public boolean getSettingIsFirstLogin() {
        return mContext.getSharedPreferences(
                UserPersistenceContract.PrefSetting.SETTING_FILE_NAME,
                Context.MODE_PRIVATE)
                .getBoolean(UserPersistenceContract.PrefSetting.IS_FIRST_LOGIN, true);
    }

    @Override
    public void saveSettingIsFirstLogin(boolean b) {
        mContext.getSharedPreferences(
                UserPersistenceContract.PrefSetting.SETTING_FILE_NAME,
                Context.MODE_PRIVATE).edit()
                .putBoolean(UserPersistenceContract.PrefSetting.IS_FIRST_LOGIN, b).apply();
    }


    private void refreshLocalDataSource(List<Grade> grades) {

    }

}
