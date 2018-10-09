package com.epam.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.epam.interfaces.CourseInterface;

/**
 * A class that helps with checking courses
 * 
 * @author Sergey Ahmetshin
 * @param <T> extends CourseInterface
 */
public class CourseHelper<T extends CourseInterface> {
    public List<T> getActualCourses(List<T> list) {
        return list.stream().filter(CourseHelper::checkActual).collect(Collectors.toList());
    }

    public List<T> getCurrentCourses(List<T> list) {
        return list.stream().filter(CourseHelper::checkCurrent).collect(Collectors.toList());
    }

    public List<T> getArchiveCourses(List<T> list) {
        return list.stream().filter(CourseHelper::checkArchive).collect(Collectors.toList());
    }

    public static boolean checkActual(CourseInterface course) {
        return course.getStartDate().getTime() > System.currentTimeMillis();
    }

    public static boolean checkCurrent(CourseInterface course) {
        return (course.getStartDate().getTime() < System.currentTimeMillis()) && (course.getStartDate().getTime()
                + (long) course.getDuration() * 24 * 60 * 60 * 1000 > System.currentTimeMillis());
    }

    public static boolean checkArchive(CourseInterface course) {
        return course.getStartDate().getTime() + (long) course.getDuration() * 24 * 60 * 60 * 1000 < System
                .currentTimeMillis();
    }
}
