package com.javadev.mark;

import com.javadev.student.Student;
import com.javadev.teacher.Teacher;

import com.javadev.subject.Subject;

/**
 * Created by kuba3 on 25.04.2016.
 */
public class MarkDTO {
    private int mark;
    private long subjectId;
    private long teacherId;
    private long studentId;

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

    public long getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public Mark mapToEntity(Student student, Teacher teacher, Subject subject)
    {
        Mark mark = new Mark();
        mark.setMark(this.mark);
        mark.setStudent(student);
        mark.setTeacher(teacher);
        mark.setSubject(subject);
        return mark;
    }
}
