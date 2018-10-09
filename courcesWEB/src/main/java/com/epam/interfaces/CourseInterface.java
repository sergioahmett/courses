package com.epam.interfaces;

import java.sql.Timestamp;

/**
 * Interface to all course entities
 * 
 * @author Sergey Ahmetshin
 *
 */
public interface CourseInterface {
    public Timestamp getStartDate();

    public int getDuration();
}
