package com.oldbai.android.app.hebiace.main.Grade;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.oldbai.android.app.hebiace.data.StudentInfo;
import com.oldbai.android.app.hebiace.data.source.UserRepository;
import com.oldbai.android.app.hebiace.util.JsoupHtml;
import com.oldbai.android.app.hebiace.util.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by BaiGuoyong on 9/12/2017.
 */

public class GradePresenter implements GradeContract.Presenter {

    private GradeContract.View mView;

    private UserRepository mUserRepository;

    public GradePresenter(GradeContract.View view, Context context) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
        mUserRepository = UserRepository.getInstance(context);
    }


    @Override
    public void start() {

    }

    @Override
    public List<String> getGradesPageNum() {
        //数据库意外删除
        List<String> list = mUserRepository.getAllYearList();
        return list;
    }
}
