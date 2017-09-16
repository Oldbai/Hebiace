package com.oldbai.android.app.hebiace.main.Grade;

import com.oldbai.android.app.hebiace.BasePresenter;
import com.oldbai.android.app.hebiace.BaseView;

import java.util.List;

/**
 * Created by BaiGuoyong on 9/12/2017.
 */

public interface GradeContract {
    interface View extends BaseView<Presenter>{

        void setProgressIndicator(boolean active);

        void updataUI();

        void sendToastMessage(String msg);

    }
    interface Presenter extends BasePresenter {

        List<String> getGradesPageNum();

    }
}
