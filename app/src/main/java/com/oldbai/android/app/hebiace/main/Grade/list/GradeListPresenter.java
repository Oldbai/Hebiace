package com.oldbai.android.app.hebiace.main.Grade.list;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.oldbai.android.app.hebiace.data.Grade;
import com.oldbai.android.app.hebiace.data.StudentInfo;
import com.oldbai.android.app.hebiace.data.source.UserRepository;
import com.oldbai.android.app.hebiace.util.JsoupHtml;
import com.oldbai.android.app.hebiace.util.OkHttpUtils;

import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by BaiGuoyong on 9/13/2017.
 */

public class GradeListPresenter implements GradeListContract.Presenter {
    private GradeListContract.View mView;

    private UserRepository mUserRepository;

    private StudentInfo mStudentInfo;

    Handler mHandler;

    String mYear;

    public GradeListPresenter(GradeListContract.View view, Context context, String year) {

        mView = checkNotNull(view);
        mView.setPresenter(this);
        mYear = year;
        mUserRepository = UserRepository.getInstance(context);
    }

    @Override
    public void start() {
        mStudentInfo = mUserRepository.getStudentInfo();
    }

    @Override
    public List<Grade> getYearGrade() {
        return mUserRepository.getGradeWithYear(mYear);
    }

    @Override
    public void refreshData() {
        mStudentInfo = mUserRepository.getStudentInfo();
        mView.setProgressIndicator(true);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);;
                Bundle data = msg.getData();
                String grade = data.getString("refreshGrade");
                if (grade == null) {
                    mView.sendToastMessage("更新失败！");
                } else {
                    mUserRepository.deleteAllGrades();
                    mUserRepository.saveGrades(JsoupHtml.getAllGradeFromHtmlData(grade, mStudentInfo));
                }
                mView.setProgressIndicator(false);
                mView.updataUI();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("refreshGrade", OkHttpUtils.getGrade(mStudentInfo));
                msg.setData(data);
                mHandler.sendMessage(msg);
            }
        }).start();
    }
}
