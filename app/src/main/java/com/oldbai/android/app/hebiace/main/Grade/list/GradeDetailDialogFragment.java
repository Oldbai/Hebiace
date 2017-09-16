package com.oldbai.android.app.hebiace.main.Grade.list;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oldbai.android.app.hebiace.R;
import com.oldbai.android.app.hebiace.data.Grade;

import java.io.Serializable;

/**
 * Created by BaiGuoyong on 9/14/2017.
 */

public class GradeDetailDialogFragment extends DialogFragment {
    public static final String EXTRA_GRADE = "extra_grade";

    public Grade mGrade;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_grade_detail_list_fragment, container, false);

        mGrade = (Grade) getArguments().getSerializable(EXTRA_GRADE);
        TextView name = rootView.findViewById(R.id.grade_deteil_course_name);
        TextView id = rootView.findViewById(R.id.grade_deteil_course_id);
        TextView arr = rootView.findViewById(R.id.grade_deteil_course_arr);
        TextView credit = rootView.findViewById(R.id.grade_deteil_course_credit);
        TextView belongToCollege = rootView.findViewById(R.id.grade_deteil_course_belong_to_college);
        TextView regularGrade = rootView.findViewById(R.id.grade_deteil_course_regular_grade);
        TextView finalGrade = rootView.findViewById(R.id.grade_deteil_course_final_grade);
        TextView experimentalGrade = rootView.findViewById(R.id.grade_deteil_course_experimental_grade);
        TextView totalGrade = rootView.findViewById(R.id.grade_deteil_course_total_grade);
        TextView makeupGrade = rootView.findViewById(R.id.grade_deteil_course_makeup_grade);
        TextView rebulidGrade = rootView.findViewById(R.id.grade_deteil_course_rebuild_grade);

        name.setText(mGrade.getCourseName());
        id.setText(mGrade.getCourseId());
        arr.setText(mGrade.getCourseArr());
        credit.setText(mGrade.getCourseCredit());
        belongToCollege.setText(mGrade.getCourseBelongToCollege());
        regularGrade.setText(mGrade.getCourseRegularGrade());
        finalGrade.setText(mGrade.getCourseFinalGrade());
        experimentalGrade.setText(mGrade.getCourseExperimentalGrade());
        totalGrade.setText(mGrade.getCourseTotalGrade());
        makeupGrade.setText(mGrade.getCourseMakeupGrade());
        rebulidGrade.setText(mGrade.getCourseRebuildGrade());

        return rootView;
    }

}
