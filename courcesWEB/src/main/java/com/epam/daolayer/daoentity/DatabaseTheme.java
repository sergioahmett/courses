package com.epam.daolayer.daoentity;

/**
 * The class corresponding to the theme in the database
 * 
 * @author Sergey Ahmetshin
 *
 */
public class DatabaseTheme extends BaseEntity {
    private String title;
    private String description;

    public DatabaseTheme(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public DatabaseTheme(int id, String title, String description) {
        this(title, description);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DatabaseTheme [id=" + id + "title=" + title + ", description=" + description + "]";
    }

}
