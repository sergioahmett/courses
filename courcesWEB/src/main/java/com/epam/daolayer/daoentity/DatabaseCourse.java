package com.epam.daolayer.daoentity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.epam.interfaces.CourseInterface;

/**
 * The class corresponding to the course in the database
 * 
 * @author Sergey Ahmetshin
 *
 */
public class DatabaseCourse extends BaseEntity implements CourseInterface {
    private String title;
    private int theme;
    private String description;
    private int duration;
    private int maxStudentCount;
    private int teacher;
    private Timestamp startDate;

    private DatabaseCourse() {
    }

    public String getTitle() {
        return title;
    }
    public Timestamp getStartDate() {
        return startDate;
    }

    public int getTheme() {
        return theme;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public int getMaxStudentCount() {
        return maxStudentCount;
    }

    public int getTeacher() {
        return teacher;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        LocalDate date = Instant.ofEpochMilli(startDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public static Builder newBuilder() {
        return new DatabaseCourse().new Builder();
    }

    public Builder getBuilder() {
        return new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(int id) {
            DatabaseCourse.this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            DatabaseCourse.this.title = title;
            return this;
        }
        public Builder setStartDate(Timestamp startDate) {
            DatabaseCourse.this.startDate = startDate;
            return this;
        }

        public Builder setTheme(int theme) {
            DatabaseCourse.this.theme = theme;
            return this;
        }

        public Builder setDescription(String description) {
            DatabaseCourse.this.description = description;
            return this;
        }

        public Builder setDuration(int duration) {
            DatabaseCourse.this.duration = duration;
            return this;
        }

        public Builder setStudentCount(int maxStudentCount) {
            DatabaseCourse.this.maxStudentCount = maxStudentCount;
            return this;
        }

        public Builder setTeacher(int teacher) {
            DatabaseCourse.this.teacher = teacher;
            return this;
        }
        
        

        public DatabaseCourse build() {
            return DatabaseCourse.this;
        }
    }

    @Override
    public String toString() {
        return "Cource [title=" + title + ", theme=" + theme + ", description=" + description + ", duration=" + duration
                + ", maxStudentCount=" + maxStudentCount + ", teacher=" + teacher + ", id=" + id + ", startDate="+startDate+"]";
    }
}
