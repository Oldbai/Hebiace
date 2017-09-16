package com.oldbai.android.app.hebiace.main.StudentInfo;

import com.oldbai.android.app.hebiace.BasePresenter;
import com.oldbai.android.app.hebiace.BaseView;
import com.oldbai.android.app.hebiace.data.StudentInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by BaiGuoyong on 9/12/2017.
 */

public interface StudentInfoContract {
    interface View extends BaseView<Presenter>{

        void showBaseInfo(StudentInfo studentInfo);

        void showGPAInfo(List<String> year);

        void sendToastMessage(String msg);

    }
    interface Presenter extends BasePresenter {

        List<String> getYear();

        void setInfo();

    }
}
