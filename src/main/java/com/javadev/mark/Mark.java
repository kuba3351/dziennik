package com.javadev.mark;

import com.javadev.student.Student;
import com.javadev.subject.Subject;

import javax.persistence.*;

/**
 * Created by kuba3 on 25.04.2016.
 */
@Entity
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int mark;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private Student student;
    private String typ;

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
