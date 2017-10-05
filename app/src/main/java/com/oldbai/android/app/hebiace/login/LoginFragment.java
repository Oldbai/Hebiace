package com.oldbai.android.app.hebiace.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.oldbai.android.app.hebiace.R;
import com.oldbai.android.app.hebiace.data.School;
import com.oldbai.android.app.hebiace.data.StudentInfo;
import com.oldbai.android.app.hebiace.main.MainActivity;

import static android.support.v4.util.Preconditions.checkNotNull;
import static com.oldbai.android.app.hebiace.data.School.HEBEINU;
import static com.oldbai.android.app.hebiace.data.School.HEBIACE;

/**
 * Created by BaiGuoyong on 9/5/2017.
 */

public class LoginFragment extends Fragment implements LoginContract.View {
    private static final String TAG = "LoginFragment";

    private LoginContract.Presenter mPresenter;

    private AutoCompleteTextView mStudentId;
    private EditText mStudentPwd;
    private Spinner mUniversity;
    private Button mSignInButton;
    private School mSchool;
    private String mCurrentUniversityName;
    private ProgressBar mLoginProgressBar;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        mPresenter = new LoginPresenter(this, getActivity().getApplicationContext());
        mStudentId = rootView.findViewById(R.id.studentId);
        mStudentPwd = rootView.findViewById(R.id.studentPwd);
        //选择学校
        mUniversity = rootView.findViewById(R.id.school_spinner);
        mUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mCurrentUniversityName = getActivity()
                        .getResources()
                        .getStringArray(R.array.university)[i];
                switch (i) {
                    case 0:
                        mSchool = HEBIACE;
                        break;
                    case 1:
                        mSchool = HEBEINU;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //设置登陆监听
        mSignInButton = rootView.findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = mStudentId.getText().toString();
                String pwd = mStudentPwd.getText().toString();
                mPresenter.attemptLogin(id, pwd, mSchool, mCurrentUniversityName);
            }
        });
        //进度条
        mLoginProgressBar = rootView.findViewById(R.id.login_progress_bar);

        return rootView;
    }

    /**
     * 开启MainActivity
     */
    @Override
    public void showMainUI() {
        destoryActivity();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    /**
     * 显示ProgressBar
     * @param active
     */
    @Override
    public void setLoadingIndicator(boolean active) {
        mLoginProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setStudentIdError(String message) {
        mStudentId.setError(message);
    }

    @Override
    public void setStudentPwdError(String message) {
        mStudentPwd.setError(message);
    }

    @Override
    public void initStudentInfo(StudentInfo studentInfo) {
        mStudentId.setText(studentInfo.getStudentId());
        mStudentPwd.setText(studentInfo.getStudentPwd());
    }


    @Override
    public void setViewFocus(int viewId) {
        switch (viewId) {
            case R.id.studentId:
                mStudentId.requestFocus();
                break;
            case R.id.studentPwd:
                mStudentPwd.requestFocus();
                break;
            default:
                break;
        }
    }

    @Override
    public void sendToastMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void destoryActivity() {
        getActivity().finish();
    }
}