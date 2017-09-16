package com.oldbai.android.app.hebiace.data.source;

import com.oldbai.android.app.hebiace.data.Grade;
import com.oldbai.android.app.hebiace.data.StudentInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by BaiGuoyong on 9/5/2017.
 */

public interface UserDataSource {

    //获得学年
    List<String> getAllYearList();

    //删除数据
    void deleteAllGrades();

    //存储数据
    void saveGrades(Map<String, Grade> gradeMap);

    //通过年得到成绩
    List<Grade> getGradeWithYear(String year);

    //保存个人信息
    void saveStudentInfo(StudentInfo studentInfo);

    //得到个人信息
    StudentInfo getStudentInfo();

    //是否第一次登陆
    boolean getSettingIsFirstLogin();

    //存储登陆状态
    void saveSettingIsFirstLogin(boolean b);

}
