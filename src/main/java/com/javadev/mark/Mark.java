package com.javadev.mark;

import com.javadev.student.Student;
import com.javadev.teacher.Teacher;

import javax.persistence.*;
import com.javadev.subject.Subject;

/**
 * Created by kuba3 on 25.04.2016.
 */
@Entity
public class Mark {
    @Id
    @GeneratedValue
    private
    long id;
    private int mark;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Student student;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
