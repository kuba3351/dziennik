package com.javadev.Class;

/**
 * Created by kuba3 on 03.06.2016.
 */
public class ClassDTO {
    private String name;
    private int year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Class mapToEntity() {
        Class clazz = new Class();
        clazz.setName(name);
        clazz.setYear(year);
        return clazz;
    }
}
