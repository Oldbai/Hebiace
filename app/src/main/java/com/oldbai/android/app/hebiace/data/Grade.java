package com.oldbai.android.app.hebiace.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * 成绩
 * Created by BaiGuoyong on 9/5/2017.
 */

public final class Grade implements Serializable{

    private final String mId;

    private String mStudentId;

    private String mYear;

    private String mTerm;

    private String mCourseId;

    private String mCourseName;

    private String mCourseArr;

    private String mCourseBelongTo;

    private String mCourseCredit;

    private String mCourseGPA;

    //平时成绩
    private String mCourseRegularGrade;

    private String mCourseMidtermGrade;

    private String mCourseFinalGrade;

    private String mCourseExperimentalGrade;

    private String mCourseTotalGrade;

    private String mCourseMinorMark;

    private String mCourseMakeupGrade;

    private String mCourseRebuildGrade;

    private String mCourseBelongToCollege;

    private String mCourseRebuildMark;

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public String getTerm() {
        return mTerm;
    }

    public void setTerm(String term) {
        mTerm = term;
    }

    public String getCourseId() {
        return mCourseId;
    }

    public void setCourseId(String courseId) {
        mCourseId = courseId;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String courseName) {
        mCourseName = courseName;
    }

    public String getCourseArr() {
        return mCourseArr;
    }

    public void setCourseArr(String courseArr) {
        mCourseArr = courseArr;
    }

    public String getCourseBelongTo() {
        return mCourseBelongTo;
    }

    public void setCourseBelongTo(String courseBelongTo) {
        mCourseBelongTo = courseBelongTo;
    }

    public String getCourseCredit() {
        return mCourseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        mCourseCredit = courseCredit;
    }

    public String getCourseGPA() {
        return mCourseGPA;
    }

    public void setCourseGPA(String courseGPA) {
        mCourseGPA = courseGPA;
    }

    public String getCourseRegularGrade() {
        return mCourseRegularGrade;
    }

    public void setCourseRegularGrade(String courseRegularGrade) {
        mCourseRegularGrade = courseRegularGrade;
    }

    public String getCourseMidtermGrade() {
        return mCourseMidtermGrade;
    }

    public void setCourseMidtermGrade(String courseMidtermGrade) {
        mCourseMidtermGrade = courseMidtermGrade;
    }

    public String getCourseFinalGrade() {
        return mCourseFinalGrade;
    }

    public void setCourseFinalGrade(String courseFinalGrade) {
        mCourseFinalGrade = courseFinalGrade;
    }

    public String getCourseExperimentalGrade() {
        return mCourseExperimentalGrade;
    }

    public void setCourseExperimentalGrade(String courseExperimentalGrade) {
        mCourseExperimentalGrade = courseExperimentalGrade;
    }

    public String getCourseTotalGrade() {
        return mCourseTotalGrade;
    }

    public void setCourseTotalGrade(String courseTotalGrade) {
        mCourseTotalGrade = courseTotalGrade;
    }

    public String getCourseMinorMark() {
        return mCourseMinorMark;
    }

    public void setCourseMinorMark(String courseMinorMark) {
        mCourseMinorMark = courseMinorMark;
    }

    public String getCourseMakeupGrade() {
        return mCourseMakeupGrade;
    }

    public void setCourseMakeupGrade(String courseMakeupGrade) {
        mCourseMakeupGrade = courseMakeupGrade;
    }

    public String getCourseRebuildGrade() {
        return mCourseRebuildGrade;
    }

    public void setCourseRebuildGrade(String courseRebuildGrade) {
        mCourseRebuildGrade = courseRebuildGrade;
    }

    public String getCourseBelongToCollege() {
        return mCourseBelongToCollege;
    }

    public void setCourseBelongToCollege(String courseBelongToCollege) {
        mCourseBelongToCollege = courseBelongToCollege;
    }

    public String getCourseRebuildMark() {
        return mCourseRebuildMark;
    }

    public void setCourseRebuildMark(String courseRebuildMark) {
        mCourseRebuildMark = courseRebuildMark;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public Grade(String studentId) {
        this(UUID.randomUUID().toString(), studentId);
    }

    private Grade(String id, String studentId) {
        this.mId = id;
        this.mStudentId = studentId;
    }

    public String getId() {
        return mId;
    }
}
