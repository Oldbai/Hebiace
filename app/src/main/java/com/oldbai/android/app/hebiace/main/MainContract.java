package com.oldbai.android.app.hebiace.main;

import android.content.Context;
import android.view.View;

import com.oldbai.android.app.hebiace.BasePresenter;
import com.oldbai.android.app.hebiace.BaseView;
import com.oldbai.android.app.hebiace.data.StudentInfo;


/**
 * Created by BaiGuoyong on 9/6/2017.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void showUserInfo(StudentInfo studentInfo);

        void showLoginUI();

        void showWeb(String url);

    }
    interface Presenter extends BasePresenter {

        void logout();

        void about();

        void schoolIndex();

        void techingServer();

    }

}
