package com.javadev.subject;

import com.javadev.teacher.Teacher;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    @ManyToMany
    List<Teacher> teachers;

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
