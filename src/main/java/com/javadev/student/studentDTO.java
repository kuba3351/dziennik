package com.javadev.student;

/**
 * Created by kuba3 on 25.04.2016.
 */
public class StudentDTO {
    private String name;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Student mapToEntity()
    {
        Student student = new Student();
        student.setName(this.getName());
        student.setLastName(this.getLastName());
        return student;
    }
    public Student mapToEntity(long id)
    {
        Student student = this.mapToEntity();
        student.setId(id);
        return student;
    }
}
