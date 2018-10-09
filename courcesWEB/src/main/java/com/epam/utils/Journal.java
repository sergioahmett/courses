package com.epam.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.epam.daolayer.daoentity.BaseEntity;
import com.epam.daolayer.daoentity.DatabaseUser;
import com.google.gson.Gson;

/**
 * The class which is used for convenient storage of ratings in the database and
 * work with them.
 * 
 * @author Sergey Ahmetshin
 * 
 */

public class Journal extends BaseEntity {
    private List<String> titleList = new LinkedList<>();
    private Map<Integer, List<String>> usersMap = new HashMap<>();;
    private Map<Integer, Integer> finalRaiting = new HashMap<>();

    public void addStudents(List<DatabaseUser> studentsList) {
        studentsList.forEach(student -> {
            List<String> userString = new LinkedList<>();
            userString.add(student.getName() + " " + student.getSurname());
            usersMap.put(student.getId(), userString);
        });
    }

    public void addDayToJournal(String day) {
        titleList.add(day);
        usersMap.forEach((id, list) -> {
            list.add("");
        });
    }

    public void addRaitingToUserByDay(int id, String day, String raiting) {
        int index = titleList.indexOf(day) + 1;
        usersMap.get(id).remove(index);
        usersMap.get(id).add(index, raiting);
    }

    public void addFinalRaitingToUser(int id, int raiting) {
        if (usersMap.containsKey(id)) {
            finalRaiting.put(id, raiting);
        }
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public Map<Integer, List<String>> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Integer, List<String>> usersMap) {
        this.usersMap = usersMap;
    }

    public void output() {
        System.out.print("ÔÈÎ\t");
        titleList.forEach(title -> System.out.print(title + "\t"));
        System.out.println();
        usersMap.forEach((id, list) -> {
            list.forEach(str -> {
                System.out.print(str + "\t");
            });
            System.out.println();
        });
    }

    public String getJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Journal getFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Journal.class);
    }

    public Map<Integer, Integer> getFinalRaiting() {
        return finalRaiting;
    }

    public void setFinalRaiting(Map<Integer, Integer> finalRaiting) {
        this.finalRaiting = finalRaiting;
    }
}
