package com.oldbai.android.app.hebiace.login;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.oldbai.android.app.hebiace.R;
import com.oldbai.android.app.hebiace.data.Grade;
import com.oldbai.android.app.hebiace.data.School;
import com.oldbai.android.app.hebiace.data.StudentInfo;
import com.oldbai.android.app.hebiace.data.source.UserRepository;
import com.oldbai.android.app.hebiace.util.JsoupHtml;
import com.oldbai.android.app.hebiace.util.OkHttpUtils;

import java.util.Map;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by BaiGuoyong on 9/5/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";

    private final LoginContract.View mView;
    private UserRepository mUserRepository;
    //个人信息
    private StudentInfo mStudentInfo;

    public LoginPresenter(LoginContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);
        //传入context利用DataHlper
        mUserRepository = UserRepository.getInstance(context);
    }
    @Override
    public void start() {
        mStudentInfo = mUserRepository.getStudentInfo();
        mView.initStudentInfo(mStudentInfo);
    }

    @Override
    public void attemptLogin(String studentId, String studentPwd, School schoolEmnu, String schoolName) {
        boolean cancel = false;
        int focusViewId = 0;
        if (TextUtils.isEmpty(studentId) && 10 < studentId.length() && studentId.length()< 12) {
            mView.setStudentIdError("学号错误");
            focusViewId = R.id.studentId;
            cancel = true;
        }
        if (TextUtils.isEmpty(studentPwd)) {
            mView.setStudentPwdError("密码不能为空");
            focusViewId = R.id.studentPwd;
            cancel = true;
        }
        if (cancel) {
            mView.setViewFocus(focusViewId);
        } else {
            //设置个人信息
            mStudentInfo.setStudentId(studentId);
            mStudentInfo.setStudentPwd(studentPwd);
            mStudentInfo.setSchoolEmnu(schoolEmnu);
            mStudentInfo.setSchoolName(schoolName);
            mView.setLoadingIndicator(true);
            new VerifyData().execute();
        }
    }


    private class VerifyData extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            String grade = OkHttpUtils.getGrade(mStudentInfo);
            if (TextUtils.isEmpty(grade)){
                return false;
            } else {
                mStudentInfo = JsoupHtml.getStudentInfoFromHtmlData(grade, mStudentInfo);
                Map<String, Grade> gradeMap = JsoupHtml.getAllGradeFromHtmlData(grade, mStudentInfo);

                //删除所有数据，从新导入数据
                mUserRepository.deleteAllGrades();
                mUserRepository.saveGrades(gradeMap);
                return true;
            }
        }
        @Override
        protected void onPostExecute(Boolean b) {
            //取消进程框
            mView.setLoadingIndicator(false);
            if (b){
                mView.sendToastMessage("欢迎您：" + mStudentInfo.getName() + " 同学");
                //保存个人信息
                mUserRepository.saveStudentInfo(mStudentInfo);
                //跳转主活动
                mView.showMainUI();

                mUserRepository.saveSettingIsFirstLogin(false);
            }else{
                mView.sendToastMessage("连接出错，请检查.");
            }
        }
    }
}
