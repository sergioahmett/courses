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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((courseList == null) ? 0 : courseList.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResultTeacher other = (ResultTeacher) obj;
        if (courseList == null) {
            if (other.courseList != null)
                return false;
        } else if (!courseList.equals(other.courseList))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (fullName == null) {
            if (other.fullName != null)
                return false;
        } else if (!fullName.equals(other.fullName))
            return false;
        if (id != other.id)
            return false;
        return true;
    }
public boolean compereTeacher(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    ResultTeacher other = (ResultTeacher) obj;
    if (description == null) {
        if (other.description != null)
            return false;
    } else if (!description.equals(other.description))
        return false;
    if (fullName == null) {
        if (other.fullName != null)
            return false;
    } else if (!fullName.equals(other.fullName))
        return false;
    if (id != other.id)
        return false;
    return true;
}
}
