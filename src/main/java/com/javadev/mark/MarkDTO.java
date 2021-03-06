package com.javadev.mark;

import com.javadev.student.Student;
import com.javadev.subject.Subject;

/**
 * Created by kuba3 on 25.04.2016.
 */
public class MarkDTO {
    private int mark;
    private long subjectId;
    private long studentId;
    private String typ;

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public int getMark() {
        return this.mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public long getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public Mark mapToEntity(Student student, Subject subject) {
        Mark mark = new Mark();
        mark.setMark(this.mark);
        mark.setStudent(student);
        mark.setSubject(subject);
        return mark;
    }
}
