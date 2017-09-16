package com.oldbai.android.app.hebiace.main.Grade.list;

import com.oldbai.android.app.hebiace.BasePresenter;
import com.oldbai.android.app.hebiace.BaseView;
import com.oldbai.android.app.hebiace.data.Grade;

import java.util.List;

/**
 * Created by BaiGuoyong on 9/13/2017.
 */

public interface GradeListContract {

    interface View extends BaseView<Presenter> {

        void updataUI();

        void sendToastMessage(String msg);

        void setProgressIndicator(boolean active);
    }
    interface Presenter extends BasePresenter {

        List<Grade> getYearGrade();

        void refreshData();
    }
}
