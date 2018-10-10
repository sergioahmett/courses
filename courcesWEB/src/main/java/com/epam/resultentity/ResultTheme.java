package com.epam.resultentity;

import java.util.List;

import com.epam.daolayer.daoentity.DatabaseTheme;

/**
 * An entity that contains all the information about the theme. Contains
 * DatabaeTheme entity and list of course
 * 
 * @author Sergey Ahmetshin
 *
 */

public class ResultTheme {
    private DatabaseTheme theme;
    private List<ResultCourse> courseList;

    public ResultTheme(DatabaseTheme theme, List<ResultCourse> courseList) {
        this.setCourseList(courseList);
        this.theme = theme;
    }

    public DatabaseTheme getTheme() {
        return theme;
    }

    public void setTheme(DatabaseTheme theme) {
        this.theme = theme;
    }

    public List<ResultCourse> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<ResultCourse> courseList) {
        this.courseList = courseList;
    }
}
