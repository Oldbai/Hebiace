package com.oldbai.android.app.hebiace.main;

import android.content.Context;

import com.oldbai.android.app.hebiace.data.StudentInfo;
import com.oldbai.android.app.hebiace.data.source.UserRepository;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by BaiGuoyong on 9/10/2017.
 */

public class MainPresenter implements MainContract.Presenter{
    private static final String TAG = "MainPresenter";

    private MainContract.View mView;

    private UserRepository mUserRepository;

    private StudentInfo mStudentInfo;

    public MainPresenter(MainContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);
        mUserRepository = UserRepository.getInstance(context);
    }
    @Override
    public void start() {
        //获得个人信息
        mStudentInfo = mUserRepository.getStudentInfo();
        //显示个人信息
        mView.showUserInfo(mStudentInfo);
    }

    @Override
    public void logout() {
        mUserRepository.saveSettingIsFirstLogin(true);
        mUserRepository.deleteAllGrades();

        mView.showLoginUI();
    }



    @Override
    public void about() {
        mView.showWeb("http://www.oldbai.com/");
    }

    @Override
    public void schoolIndex() {
        switch (mStudentInfo.getSchoolEmnu()){
            case HEBEINU:
                mView.showWeb("http://211.81.208.4/");
                break;
            case HEBIACE:
                mView.showWeb("http://www.hebiace.edu.cn/");
                break;
            default:
                break;
        }
    }

    @Override
    public void techingServer() {
        switch (mStudentInfo.getSchoolEmnu()){
            case HEBEINU:
                mView.showWeb("http://jwc.hebeinu.edu.cn/webPage/index.html");
                break;
            case HEBIACE:
                mView.showWeb("http://wwt.hebiace.edu.cn/col8/col24/col82/index.htm1?colid=82");
                break;
            default:
                break;
        }
    }
}
