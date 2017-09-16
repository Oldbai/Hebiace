package com.oldbai.android.app.hebiace.main.StudentInfo;

import android.content.Context;

import com.oldbai.android.app.hebiace.data.Grade;
import com.oldbai.android.app.hebiace.data.StudentInfo;
import com.oldbai.android.app.hebiace.data.source.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by BaiGuoyong on 9/12/2017.
 */

public class StudentInfoPresenter implements StudentInfoContract.Presenter {

    private StudentInfoContract.View mView;

    private UserRepository mUserRepository;

    private StudentInfo mStudentInfo;

    public StudentInfoPresenter(StudentInfoContract.View view, Context context) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
        mUserRepository = UserRepository.getInstance(context);
    }

    @Override
    public void start() {
        mStudentInfo = mUserRepository.getStudentInfo();
        setInfo();
    }

    @Override
    public List<String> getYear() {
        //数据库意外删除
        List<String> list = mUserRepository.getAllYearList();
        return list;
    }

    @Override
    public void setInfo() {
        mView.showBaseInfo(mStudentInfo);

        List<String> average = new ArrayList<String>();
        List<String> lists = getYear();
        Float creditAndGPAAll = 0f;
        Float creditAll = 0f;
        for (String list : lists) {
            List<Grade> grades= mUserRepository.getGradeWithYear(list);
            Float creditAndGPA = 0f;
            Float credit = 0f;
            for (Grade grade : grades){
                //全角空格
                credit += Float.parseFloat(grade.getCourseGPA().equals(" ") ? String.valueOf(0) : grade.getCourseCredit());
                creditAndGPA += Float.parseFloat(
                        grade.getCourseCredit().equals(" ") ? String.valueOf(0) : grade.getCourseCredit()
                ) * Float.parseFloat(
                        grade.getCourseGPA().equals(" ") ? String.valueOf(0) : grade.getCourseGPA()
                );
            }
            creditAll += credit;
            creditAndGPAAll += creditAndGPA;
            average.add(String.format("%.2f", (creditAndGPA / credit)));
        }
        average.add(String.format("%.2f", (creditAndGPAAll / creditAll)));

        mView.showGPAInfo(average);
    }
}
