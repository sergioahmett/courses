package com.epam.resultentity;

import java.util.List;

import com.epam.daolayer.daoentity.DatabaseTheam;

/**
 * An entity that contains all the information about the theme. Contains
 * DatabaeTheam entity and list of course
 * 
 * @author Sergey Ahmetshin
 *
 */

public class ResultTheam {
    private DatabaseTheam theam;
    private List<ResultCourse> courseList;

    public ResultTheam(DatabaseTheam theam, List<ResultCourse> courseList) {
        this.setCourseList(courseList);
        this.theam = theam;
    }

    public DatabaseTheam getTheam() {
        return theam;
    }

    public void setTheam(DatabaseTheam theam) {
        this.theam = theam;
    }

    public List<ResultCourse> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<ResultCourse> courseList) {
        this.courseList = courseList;
    }
}
