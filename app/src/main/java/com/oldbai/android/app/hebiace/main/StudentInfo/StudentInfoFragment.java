package com.oldbai.android.app.hebiace.main.StudentInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oldbai.android.app.hebiace.R;
import com.oldbai.android.app.hebiace.data.StudentInfo;

import java.util.List;

/**
 * Created by BaiGuoyong on 9/15/2017.
 */

public class StudentInfoFragment extends Fragment implements StudentInfoContract.View{
    private static final String TAG = "StudentInfoFragment";

    private StudentInfoContract.Presenter mPresenter;

    private TextView mName;
    private TextView mCollege;
    private TextView mMajor;
    private TextView mGPAYear;
    private TextView mGPAValue;
    private TextView mGPATotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_student_info_fragment, container, false);
        //持有Presenter
        mPresenter = new StudentInfoPresenter(this, getActivity().getApplicationContext());

        mName = rootView.findViewById(R.id.main_student_info_name);
        mCollege = rootView.findViewById(R.id.main_student_info_college);
        mMajor = rootView.findViewById(R.id.main_student_info_major);
        mGPAYear = rootView.findViewById(R.id.main_student_gpa_year);
        mGPAValue = rootView.findViewById(R.id.main_student_gpa_value);
        mGPATotal = rootView.findViewById(R.id.main_student_gpa_total);

        return rootView;
    }

    public static StudentInfoFragment newInstance(){
        return new StudentInfoFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(StudentInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showBaseInfo(StudentInfo studentInfo) {
        mName.setText(studentInfo.getName());
        mCollege.setText(studentInfo.getCollege());
        mMajor.setText(studentInfo.getMajor());
    }

    @Override
    public void showGPAInfo(List<String> GPA) {
        String year = "";
        String value = "";
        List<String> years = mPresenter.getYear();
        mGPATotal.setText(GPA.get(GPA.size() - 1));
        GPA.remove(GPA.size() - 1);
        for (int i = 0; i < GPA.size(); i++) {
            year += years.get(i) + "学年\n";
            value += GPA.get(i) + "\n";
        }
        mGPAYear.setText(year.substring(0, year.length() - 1));
        mGPAValue.setText(value.substring(0, value.length() - 1));
    }

    @Override
    public void sendToastMessage(String msg) {

    }
}
