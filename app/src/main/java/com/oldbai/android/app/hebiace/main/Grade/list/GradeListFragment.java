package com.oldbai.android.app.hebiace.main.Grade.list;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.oldbai.android.app.hebiace.R;
import com.oldbai.android.app.hebiace.data.Grade;
import java.util.List;

/**
 * Created by BaiGuoyong on 9/13/2017.
 */

public class GradeListFragment extends Fragment implements GradeListContract.View{
    private static final String TAG = "IndexListFragment";

    private static final String CURRENT_GRADE_PAGE ="CurrentGradePage";

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private GradeListAdapter mListAdapter;

    private GradeListContract.Presenter mPresenter;

    private List<Grade> mGradeList;

    private String mPage;


    public static GradeListFragment newInstance(String page) {
        GradeListFragment fragment = new GradeListFragment();
        Bundle args = new Bundle();
        args.putString(CURRENT_GRADE_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_grade_list_fragment, container, false);

        //获取参数
        if (getArguments() != null){
            mPage = getArguments().getString(CURRENT_GRADE_PAGE);
        }
        mPresenter = new GradeListPresenter(this, getActivity().getApplicationContext(), mPage);
        mRecyclerView = rootView.findViewById(R.id.grade_list_content);
        mSwipeRefreshLayout = rootView.findViewById(R.id.grade_list_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData();
            }
        });


        updataUI();

        return rootView;
    }

    @Override
    public void setPresenter(GradeListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updataUI() {
        mGradeList = mPresenter.getYearGrade();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListAdapter = new GradeListAdapter(mGradeList);
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    public void sendToastMessage(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProgressIndicator(boolean active) {
        mSwipeRefreshLayout.setRefreshing(active);

    }

    public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.GradeListViewHolder> {
        private List<Grade> mGradeList;

        public GradeListAdapter(List<Grade> grades) {
            mGradeList = grades;
        }

        @Override
        public GradeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_grade_list, parent, false);

            return new GradeListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GradeListViewHolder holder, final int position) {
            Grade grade = mGradeList.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GradeDetailDialogFragment detailDialogFragment = new GradeDetailDialogFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(GradeDetailDialogFragment.EXTRA_GRADE, mGradeList.get(position));
                    detailDialogFragment.setArguments(bundle);

                    detailDialogFragment.show(getFragmentManager(), mGradeList.get(position).getCourseName());
                }
            });
            TextView courseNameTextView = holder.itemView.findViewById(R.id.grade_name);
            TextView courseIdTextView = holder.itemView.findViewById(R.id.grade_course_id);
            TextView courseTotalGradeTextView = holder.itemView.findViewById(R.id.grade_course_total_grade);
            TextView courseArrGradeTextView = holder.itemView.findViewById(R.id.grade_course_arr);
            TextView courseCreditTextView = holder.itemView.findViewById(R.id.grade_course_credit);
            TextView courseYearTermGradeTextView = holder.itemView.findViewById(R.id.grade_course_year_term);

            courseNameTextView.setText(grade.getCourseName());
            courseYearTermGradeTextView.setText(grade.getYear() + "("+grade.getTerm() + ")");
            courseIdTextView.setText(grade.getCourseId());
            courseCreditTextView.setText(grade.getCourseCredit());
            courseArrGradeTextView.setText(grade.getCourseArr());
            courseTotalGradeTextView.setText(grade.getCourseTotalGrade());
        }

        @Override
        public int getItemCount() {
            return mGradeList.size();
        }

        public class GradeListViewHolder extends RecyclerView.ViewHolder{

            public GradeListViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
