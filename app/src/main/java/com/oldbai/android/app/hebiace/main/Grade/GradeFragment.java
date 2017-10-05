package com.oldbai.android.app.hebiace.main.Grade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.oldbai.android.app.hebiace.R;
import com.oldbai.android.app.hebiace.main.Grade.list.GradeListFragment;

import java.util.List;

/**
 * Created by BaiGuoyong on 9/12/2017.
 */

public class GradeFragment extends Fragment implements GradeContract.View{
    private static final String TAG = "IndexFragment";

    private ViewPager mViewPager;

    private GradesPagerAdapter mGradesPagerAdapter;

    private GradeContract.Presenter mPresenter;

    private List<String> mYearList;

    private TabLayout mTabLayout;

    public static GradeFragment newInstance() {
        return new GradeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_grade_fragment, container, false);
        //初始化Presenter
        mPresenter = new GradePresenter(this, getActivity().getApplicationContext());
        mViewPager = rootView.findViewById(R.id.grade_container);
        mTabLayout = rootView.findViewById(R.id.grade_tabs);

        updataUI();

        return rootView;

    }

    @Override
    public void setPresenter(GradeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void updataUI() {
        //获得年份列表
        mYearList = mPresenter.getGradesPageNum();
        //初始化Apapter
        mGradesPagerAdapter = new GradesPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mGradesPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    //设置标签栏
    public class GradesPagerAdapter extends FragmentPagerAdapter {

        public GradesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return GradeListFragment.newInstance(mYearList.get(position).toString());
        }

        @Override
        public int getCount() {
            return mYearList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mYearList.get(position).substring(0, 4);
        }
    }
}
