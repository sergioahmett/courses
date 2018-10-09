package com.epam.resultentity;

import java.util.List;

/**
 * An entity that contains all the information about the teacher. Contains
 * teacher ID, decription, teacher full name and list of his course
 * 
 * @author Sergey Ahmetshin
 *
 */

public class ResultTeacher {
    private int id;
    private String description;
    private String fullName;
    private List<ResultCourse> courseList;

    public ResultTeacher(int id, String fullName, String description, List<ResultCourse> courseList) {
        super();
        this.id = id;
        this.description = description;
        this.fullName = fullName;
        this.courseList = courseList;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<ResultCourse> getCourseList() {
        return courseList;
    }
    public void setCourseList(List<ResultCourse> courseList) {
        this.courseList = courseList;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
