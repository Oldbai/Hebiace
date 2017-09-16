package com.oldbai.android.app.hebiace.login;

import android.content.Context;

import com.oldbai.android.app.hebiace.BasePresenter;
import com.oldbai.android.app.hebiace.BaseView;
import com.oldbai.android.app.hebiace.data.School;
import com.oldbai.android.app.hebiace.data.StudentInfo;

import java.util.Map;

/**
 * Created by BaiGuoyong on 9/5/2017.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showMainUI();

        void setLoadingIndicator(boolean active);

        void setStudentIdError(String message);

        void setStudentPwdError(String message);

        void initStudentInfo(StudentInfo studentInfo);

        void setViewFocus(int viewId);

        void sendToastMessage(String message);

    }

    interface Presenter extends BasePresenter {

        void attemptLogin(String id, String pwd, School school, String schoolName);
    }
}
