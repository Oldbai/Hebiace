package com.oldbai.android.app.hebiace.util;

import android.text.TextUtils;

import com.oldbai.android.app.hebiace.data.Grade;
import com.oldbai.android.app.hebiace.data.School;
import com.oldbai.android.app.hebiace.data.StudentInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.oldbai.android.app.hebiace.data.School.*;

/**
 * Created by BaiGuoyong on 9/10/2017.
 */

public class JsoupHtml {
    public static String get__VIEW(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        Document document = Jsoup.parse(data);
        return document.select("input[name=__VIEWSTATE]").val();
    }


    public static String getTitle(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        Document document = Jsoup.parse(data);
        return document.head().select("title").text();
    }

    public static StudentInfo getStudentInfoFromHtmlData(String data, StudentInfo studentInfo) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        Elements elements;
        Document document = Jsoup.parse(data);

        switch (studentInfo.getSchoolEmnu()){
            case HEBIACE:
                elements = document.select("span[id~=Label[5678]]");
                //设置信息，0是名字，1是学院，2是专业， 3是班级
                studentInfo.setName(elements.get(0).text().substring(3));
                studentInfo.setCollege(elements.get(1).text().substring(3));
                studentInfo.setMajor(elements.get(2).text());
                studentInfo.setMyClass(elements.get(3).text().substring(4));
                break;
            case HEBEINU:
                elements = document.select("span[id~=lbl_(.*?)]");
                //设置信息，2是名字，3是学院， 5是专业， 6班级
                studentInfo.setName(elements.get(2).text().substring(3));
                studentInfo.setCollege(elements.get(3).text().substring(3));
                studentInfo.setMajor(elements.get(5).text());
                studentInfo.setMyClass(elements.get(7).text().substring(4));
                break;
        }


        return studentInfo;
    }

    public static Map<String, Grade> getAllGradeFromHtmlData(String data, StudentInfo studentInfo) {
        if (TextUtils.isEmpty(data)){
            return null;
        }
        Document document = Jsoup.parse(data);
        Element element = document.getElementById("Datagrid1");
        element.getElementsByClass("datelisthead").remove();
        Elements trs = element.select("tr");

        Map<String, Grade> gradeMap = new LinkedHashMap<String, Grade>();
        switch (studentInfo.getSchoolEmnu()) {
            case HEBIACE:
                for (int i = 0; i < trs.size(); i++) {
                    Grade grade = new Grade(studentInfo.getStudentId());
                    Elements tds = trs.get(i).select("td");
                    grade.setYear(tds.get(0).text());
                    grade.setTerm(tds.get(1).text());
                    grade.setCourseId(tds.get(2).text());
                    grade.setCourseName(tds.get(3).text());
                    grade.setCourseArr(tds.get(4).text());
                    grade.setCourseBelongTo(tds.get(5).text());
                    grade.setCourseCredit(tds.get(6).text());
                    grade.setCourseGPA(tds.get(7).text());
                    grade.setCourseRegularGrade(tds.get(8).text());
                    grade.setCourseMidtermGrade(tds.get(9).text());
                    grade.setCourseFinalGrade(tds.get(10).text());
                    grade.setCourseExperimentalGrade(tds.get(11).text());
                    grade.setCourseTotalGrade(tds.get(12).text());
                    grade.setCourseMinorMark(tds.get(13).text());
                    grade.setCourseMakeupGrade(tds.get(14).text());
                    grade.setCourseRebuildGrade(tds.get(15).text());
                    grade.setCourseBelongToCollege(tds.get(16).text());
                    grade.setCourseRebuildMark(tds.get(17).text());

                    gradeMap.put(grade.getId(), grade);
                }
                break;
            case HEBEINU:
                for (int i = 0; i < trs.size(); i++) {
                    Grade grade = new Grade(studentInfo.getStudentId());
                    Elements tds = trs.get(i).select("td");
                    grade.setYear(tds.get(0).text());
                    grade.setTerm(tds.get(1).text());
                    grade.setCourseId(tds.get(2).text());
                    grade.setCourseName(tds.get(3).text());
                    grade.setCourseArr(tds.get(4).text());
                    grade.setCourseBelongTo(tds.get(5).text());
                    grade.setCourseCredit(tds.get(6).text());
                    grade.setCourseGPA(tds.get(7).text());
                    grade.setCourseTotalGrade(tds.get(8).text());
                    grade.setCourseMinorMark(tds.get(9).text());
                    grade.setCourseMakeupGrade(tds.get(10).text());
                    grade.setCourseRebuildGrade(tds.get(11).text());
                    grade.setCourseBelongToCollege(tds.get(12).text());
                    grade.setCourseRebuildMark(tds.get(13).text());

                    gradeMap.put(grade.getId(), grade);
                }
        }


        return gradeMap;
    }
}
