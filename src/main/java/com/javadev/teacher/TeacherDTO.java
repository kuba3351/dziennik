package com.javadev.teacher;

/**
 * Created by kuba3 on 25.04.2016.
 */
public class TeacherDTO {
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
    public Teacher mapToEntity()
    {
        Teacher teacher = new Teacher();
        teacher.setName(this.getName());
        teacher.setLastName(this.getLastName());
        return teacher;
    }
    public Teacher mapToEntity(long id)
    {
        Teacher teacher = this.mapToEntity();
        teacher.setId(id);
        return teacher;
    }
}
