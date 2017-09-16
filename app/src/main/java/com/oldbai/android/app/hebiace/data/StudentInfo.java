package com.oldbai.android.app.hebiace.data;

/**
 * 个人信息
 * Created by BaiGuoyong on 9/11/2017.
 */

public final class StudentInfo {
    //学号
    private String mStudentId;

    //教务密码
    private String mStudentPwd;

    //学校
    private School mSchoolEmnu;

    //名字
    private String mName;

    //所属学院
    private String mCollege;

    //专业
    private String mMajor;

    //班级
    private String mMyClass;

    //学校
    private String mSchoolName;

    public StudentInfo(){};

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCollege() {
        return mCollege;
    }

    public void setCollege(String college) {
        mCollege = college;
    }

    public String getMajor() {
        return mMajor;
    }

    public void setMajor(String major) {
        mMajor = major;
    }

    public String getMyClass() {
        return mMyClass;
    }

    public void setMyClass(String myClass) {
        mMyClass = myClass;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        mStudentId = studentId;
    }

    public String getStudentPwd() {
        return mStudentPwd;
    }

    public void setStudentPwd(String studentPwd) {
        mStudentPwd = studentPwd;
    }

    public School getSchoolEmnu() {
        return mSchoolEmnu;
    }

    public void setSchoolEmnu(School schoolEmnu) {
        mSchoolEmnu = schoolEmnu;
    }

    public String getSchoolName() {
        return mSchoolName;
    }

    public void setSchoolName(String schoolName) {
        mSchoolName = schoolName;
    }
}
