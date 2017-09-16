package com.oldbai.android.app.hebiace.util;

import android.text.TextUtils;

import com.oldbai.android.app.hebiace.data.StudentInfo;
import com.oldbai.android.app.hebiace.data.source.UserPersistenceContract.API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by BaiGuoyong on 9/7/2017.
 */

public class OkHttpUtils {
    //静态实例
    private static OkHttpUtils sOkHttpUtils = new OkHttpUtils();
    //cookies
    private static List<Cookie> sCookies;
    //okhttp构建头
    private OkHttpClient.Builder mOkHttpClientBuilder;
    //okhttpclient池
    private OkHttpClient mOkHttpClient;
    //构造器
    private OkHttpUtils() {
        mOkHttpClientBuilder = new OkHttpClient.Builder();

        mOkHttpClientBuilder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                sCookies = cookies;
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                if (null != sCookies) {
                    return sCookies;
                } else {
                    return new ArrayList<Cookie>();
                }
            }
        });

        mOkHttpClient = mOkHttpClientBuilder.build();
    }
//    /**
//     * 饿汉方式获取实例
//     * @return
//     */
//    private static OkHttpUtils getInstance() {
//        return sOkHttpUtils;
//    }


    public static Response postSync(String url, Map<String, String> postBody, Map<String, String>... requestHeaders) throws IOException {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        Map<String, String>  requestHeader = null;
        if (requestHeaders == null) {
            requestHeader = new LinkedHashMap<String,String>();
        } else {
            requestHeader = requestHeaders[0];
        }


        if (postBody == null) {
            postBody = new LinkedHashMap<String, String>();
        }

        Headers.Builder headersBuilder = new Headers.Builder();
        for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
            headersBuilder.add(entry.getKey(), entry.getValue());
        }
        Headers headers = headersBuilder.build();

        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, String> entry : postBody.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }

        RequestBody requestBody = formBody.build();

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();

        return sOkHttpUtils
                .mOkHttpClient
                .newCall(request)
                .execute();
    }

    public static Response getSync(String url) throws IOException {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Request request = new Request.Builder().url(url).build();
        return sOkHttpUtils.mOkHttpClient.newCall(request).execute();
    }

    public static Response getSync(String url, Map<String, String> requestHeader) throws IOException {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (requestHeader.isEmpty()) {
            return null;
        }

        Headers.Builder headersBuilder = new Headers.Builder();
        for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
            headersBuilder.add(entry.getKey(), entry.getValue());
        }
        Headers requestHeaders = headersBuilder.build();
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        Request request = new Request.Builder().url(url).headers(requestHeaders).build();
        return sOkHttpUtils.mOkHttpClient.newCall(request).execute();
    }


    public static String getHtmlView(String url, Map<String, String> requestHerader ) throws IOException {
        Response getResponse = OkHttpUtils.getSync(url, requestHerader);
        if (getResponse.code() != 200) {
            return null;
        }
        String view = getResponse.body().string();
        return JsoupHtml.get__VIEW(view);
    }

    public static String getHtmlView(String url) throws IOException {
        Response getResponse = OkHttpUtils.getSync(url);
        if (getResponse.code() != 200) {
            return null;
        }
        String view = getResponse.body().string();
        return JsoupHtml.get__VIEW(view);
    }


    //服务器状态验证
    public static boolean verityLogin(StudentInfo studentInfo) throws IOException {
        String loginUrl = null;
        Map<String, String> postBody = new LinkedHashMap<>();
        Map<String, String> requestHeader = new LinkedHashMap<>();
        //根据学校获取服务器网址。
        switch (studentInfo.getSchoolEmnu()) {
            case HEBIACE:
                loginUrl = API.HebiaceApi.EDU_SERVER_URL;
                break;
            case HEBEINU:
                loginUrl = API.HebeinuApi.EDU_SERVER_URL;
                break;
        }


        switch (studentInfo.getSchoolEmnu()) {
            case HEBIACE:
                String __VIEW = getHtmlView(loginUrl);
                postBody.put("RadioButtonList1", "%D1%A7%C9%FA");
                postBody.put("Button1", "");
                postBody.put("TextBox1", studentInfo.getStudentId());
                postBody.put("TextBox2", studentInfo.getStudentPwd());
                postBody.put("__VIEWSTATE", __VIEW==null ?  "" : __VIEW);

                requestHeader.put("Referer", API.HebiaceApi.EDU_SERVER_URL);
                break;
            case HEBEINU:
                postBody.put("RadioButtonList1_2", "%D1%A7%C9%FA");
                postBody.put("Button1", "");
                postBody.put("TextBox1", studentInfo.getStudentId());
                postBody.put("TextBox2", studentInfo.getStudentPwd());

                requestHeader.put("Referer", API.HebeinuApi.EDU_SERVER_URL);
                break;
        }
        Response postResponse = OkHttpUtils.postSync(loginUrl,
                postBody,
                requestHeader);
        String title = JsoupHtml.getTitle(postResponse.body().string());

        return title.equals("正方教务管理系统") ? true : false;
    }


    //获得数据
    public static String getGrade(StudentInfo studentInfo) {
        try {
            if (!verityLogin(studentInfo)){
                return null;
            }

            Map<String, String> postBody = new LinkedHashMap<>();
            Map<String, String> requestHeader = new LinkedHashMap<>();
            String gradeUrl = null;
            switch (studentInfo.getSchoolEmnu()) {
                case HEBIACE:
                    gradeUrl = API.HebiaceApi.EDU_SERVER_GRADE + studentInfo.getStudentId();
                    requestHeader.put("Referer", gradeUrl);
                    break;
                case HEBEINU:
                    gradeUrl = API.HebeinuApi.EDU_SERVER_GRADE + studentInfo.getStudentId();
                    requestHeader.put("Referer", gradeUrl);
                    break;
            }

            String __VIEW = getHtmlView(gradeUrl, requestHeader);

            switch (studentInfo.getSchoolEmnu()) {
                case HEBIACE:
                    postBody.put("ddlXN", "");
                    postBody.put("ddlXQ", "");
                    postBody.put("Button2", "%D4%DA%D0%A3%D1%A7%CF%B0%B3%C9%BC%A8%B2%E9%D1%AF");
                    postBody.put("__VIEWSTATE", __VIEW==null ?  "" : __VIEW);
                    break;
                case HEBEINU:
                    postBody.put("ddlXN", "");
                    postBody.put("ddlXQ", "");
                    postBody.put("btn_zcj", "%C0%FA%C4%EA%B3%C9%BC%A8");
                    postBody.put("ddl_kcxz", "");
                    postBody.put("__VIEWSTATE", __VIEW==null ?  "" : __VIEW);
                    break;
            }

            Response response = postSync(gradeUrl, postBody, requestHeader);
            String grade = response.body().string();

            return grade;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
