package com.epam.resultentity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.epam.daolayer.daoentity.DatabaseCourse;
import com.epam.daolayer.daoentity.DatabaseTheme;
import com.epam.daolayer.daoentity.DatabaseUser;
import com.epam.interfaces.CourseInterface;
import com.epam.utils.CourseHelper;

/**
 * An entity that contains all the information about the course. Contains
 * courseId, courseTitle, themeId, themeTitle, regStudent, maxStudent,
 * startDate, duration, teacheId, teacherFullName and coursedescription.
 * 
 * @author Sergey Ahmetshin
 *
 */

public class ResultCourse implements CourseInterface {
    private int courseId;
    private String courseTitle;
    private int themeId;
    private String themeTitle;
    private int regStudent;
    private int maxStudent;
    private Timestamp startDate;
    private int duration;
    private int teacheId;
    private String teacherFullName;
    private String description;

    public ResultCourse() {
    }

    public ResultCourse(DatabaseCourse dbCourse, DatabaseTheme dbTheme, DatabaseUser dbTeachere, int regStudent) {
        if (dbCourse != null) {
            courseId = dbCourse.getId();
            courseTitle = dbCourse.getTitle();
            maxStudent = dbCourse.getMaxStudentCount();
            startDate = dbCourse.getStartDate();
            duration = dbCourse.getDuration();
            setDescription(dbCourse.getDescription());
        }
        if (dbTheme != null) {
            themeId = dbTheme.getId();
            themeTitle = dbTheme.getTitle();
        }
        this.regStudent = regStudent;
        if (dbTeachere != null) {
            teacheId = dbTeachere.getId();
            teacherFullName = dbTeachere.getName() + " " + dbTeachere.getSurname();
        }
    }

    public String getDate() {
        LocalDate date = Instant.ofEpochMilli(startDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getThemeTitle() {
        return themeTitle;
    }

    public void setThemeTitle(String themeTitle) {
        this.themeTitle = themeTitle;
    }

    public int getRegStudent() {
        return regStudent;
    }

    public void setRegStudent(int regStudent) {
        this.regStudent = regStudent;
    }

    public int getMaxStudent() {
        return maxStudent;
    }

    public void setMaxStudent(int maxStudent) {
        this.maxStudent = maxStudent;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTeacheId() {
        return teacheId;
    }

    public void setTeacheId(int teacheId) {
        this.teacheId = teacheId;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActual() {
        return CourseHelper.checkActual(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + courseId;
        result = prime * result + ((courseTitle == null) ? 0 : courseTitle.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + duration;
        result = prime * result + maxStudent;
        result = prime * result + regStudent;
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + teacheId;
        result = prime * result + ((teacherFullName == null) ? 0 : teacherFullName.hashCode());
        result = prime * result + themeId;
        result = prime * result + ((themeTitle == null) ? 0 : themeTitle.hashCode());
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
        ResultCourse other = (ResultCourse) obj;
        if (courseId != other.courseId)
            return false;
        if (courseTitle == null) {
            if (other.courseTitle != null)
                return false;
        } else if (!courseTitle.equals(other.courseTitle))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (duration != other.duration)
            return false;
        if (maxStudent != other.maxStudent)
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (teacheId != other.teacheId)
            return false;
        if (teacherFullName == null) {
            if (other.teacherFullName != null)
                return false;
        } else if (!teacherFullName.equals(other.teacherFullName))
            return false;
        if (themeId != other.themeId)
            return false;
        if (themeTitle == null) {
            if (other.themeTitle != null)
                return false;
        } else if (!themeTitle.equals(other.themeTitle))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ResultCourse [courseId=" + courseId + ", courseTitle=" + courseTitle + ", themeId=" + themeId
                + ", themeTitle=" + themeTitle + ", regStudent=" + regStudent + ", maxStudent=" + maxStudent
                + ", startDate=" + startDate + ", duration=" + duration + ", teacheId=" + teacheId
                + ", teacherFullName=" + teacherFullName + ", description=" + description + "]";
    }

}
