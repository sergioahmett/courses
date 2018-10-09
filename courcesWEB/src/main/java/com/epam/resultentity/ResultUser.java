package com.epam.resultentity;

import java.util.LinkedList;
import java.util.List;

import com.epam.utils.CourseHelper;

/**
 * An entity that contains all the information about the user. Contains user Id,
 * full name, login, mail and lists of courses
 * 
 * @author Sergey Ahmetshin
 *
 */

public class ResultUser {
    private int id;
    private String fullName;
    private String login;
    private String mail;
    private List<ResultCourse> actualCourseList;
    private List<ResultCourse> currentCourseList;
    private List<ResultCourse> archiveCourseList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<ResultCourse> getActualCourseList() {
        return actualCourseList;
    }

    public void setActualCourseList(List<ResultCourse> actualCourseList) {
        this.actualCourseList = actualCourseList;
    }

    public List<ResultCourse> getCurrentCourseList() {
        return currentCourseList;
    }

    public void setCurrentCourseList(List<ResultCourse> currentCourseList) {
        this.currentCourseList = currentCourseList;
    }

    public List<ResultCourse> getArchiveCourseList() {
        return archiveCourseList;
    }

    public void setArchiveCourseList(List<ResultCourse> archiveCourseList) {
        this.archiveCourseList = archiveCourseList;
    }

    public void setCourseList(List<ResultCourse> resList) {
        actualCourseList = new LinkedList<>();
        currentCourseList = new LinkedList<>();
        archiveCourseList = new LinkedList<>();
        resList.forEach(course -> {
            if (CourseHelper.checkActual(course)) {
                actualCourseList.add(course);
            }
            if (CourseHelper.checkCurrent(course)) {
                currentCourseList.add(course);
            }
            if (CourseHelper.checkArchive(course)) {
                archiveCourseList.add(course);
            }
        });

    }
}