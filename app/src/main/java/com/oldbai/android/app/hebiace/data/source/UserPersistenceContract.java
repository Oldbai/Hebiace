package com.oldbai.android.app.hebiace.data.source;

import android.provider.BaseColumns;

/**
 * Created by BaiGuoyong on 9/5/2017.
 */

public final class UserPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private UserPersistenceContract(){}

    /* Inner class that defins the table content */
    public static abstract class DbGradeEntry implements BaseColumns {
        public static final String TABLE_NAME = "Grade";
        public static final String COLUMN_NAME_GRADE_ID = "id";
        public static final String COLUMN_NAME_GRADE_STUDENTID = "student_id";
        public static final String COLUMN_NAME_GRADE_YEAR = "year";
        public static final String COLUMN_NAME_GRADE_TERM = "term";
        public static final String COLUMN_NAME_GRADE_COURSE_ID = "course_id";
        public static final String COLUMN_NAME_GRADE_COURSE_NAME = "course_name";
        public static final String COLUMN_NAME_GRADE_COURSE_ARR = "course_arr";
        public static final String COLUMN_NAME_GRADE_COURSE_BELONGTO = "course_belongto";
        public static final String COLUMN_NAME_GRADE_COURSE_CREDIT = "course_credit";
        public static final String COLUMN_NAME_GRADE_COURSE_GPA = "course_gpa";
        public static final String COLUMN_NAME_GRADE_COURSE_REGULAR_GRADE = "course_regular_grade";
        public static final String COLUMN_NAME_GRADE_COURSE_MIDTERM_GRADE = "course_midterm_grade";
        public static final String COLUMN_NAME_GRADE_COURSE_FINAL_GRADE = "course_final_grade";
        public static final String COLUMN_NAME_GRADE_COURSE_EXPERIMENTAL_GRADE = "course_experimental_grade";
        public static final String COLUMN_NAME_GRADE_COURSE_TOTAL_GRADE = "course_total_grade";
        public static final String COLUMN_NAME_GRADE_COURSE_MINOR_MARK = "course_minor_mark";
        public static final String COLUMN_NAME_GRADE_COURSE_MAKEUP_GRADE = "course_makeup_grade";
        public static final String COLUMN_NAME_GRADE_COURSE_REBUILD_GRADE = "course_rebuild_grade";
        public static final String COLUMN_NAME_GRADE_COURSE_BELONG_TO_COLLEGE = "course_to_college";
        public static final String COLUMN_NAME_GRADE_COURSE_REBUILD_MARK = "course_rebuild_mark";
    }

    public static final class PrefUserInfo {
        //学号
        public static final String STUDENT_INFO_ID = "pref_student_info_id";
        //密码
        public static final String STUDENT_INFO_PWD = "pref_student_info_pwd";
        //名字
        public static final String STUDENT_INFO_NAME = "pref_student_info_name";
        //学院
        public static final String STUDENT_INFO_COLLEGE = "pref_student_info_college";
        //学校
        public static final String STUDENT_INFO_SCHOOL = "pref_student_info_school";
        //学校识别码，枚举类型
        public static final String STUDENT_INFO_SCHOOL_EMNU = "pref_student_info_school_id";
        //专业
        public static final String STUDENT_INFO_MAJOR = "pref_student_info_major";
        //班级
        public static final String STUDENT_INFO_CLASS = "pref_student_info_class";

    }

    public static final class PrefSetting {
        //配置文件名
        public static final String SETTING_FILE_NAME = "setting";

        //登陆状态
        public static final String IS_FIRST_LOGIN = "is_first_login";
    }

    public final class API {
        public final class HebiaceApi {
            public static final String EDU_SERVER_URL = "http://jws.hebiace.edu.cn/default2.aspx";
            public static final String EDU_SERVER_GRADE = "http://jws.hebiace.edu.cn/xscj_gc.aspx?xh=";
        }

        public final class HebeinuApi {
            public static final String EDU_SERVER_URL = "http://60.8.194.168:10003/default_vsso.aspx";
            public static final String EDU_SERVER_GRADE = "http://60.8.194.168:10003/xscjcx.aspx?xh=";
        }
    }
}
