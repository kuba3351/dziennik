package com.javadev.mark;

/**
 * Created by kuba3 on 12.05.2016.
 */
public class FormDTO {
    private long student;
    private long subject;
    private int mark;

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    public long getSubject() {
        return subject;
    }

    public void setSubject(long subject) {
        this.subject = subject;
    }
}
