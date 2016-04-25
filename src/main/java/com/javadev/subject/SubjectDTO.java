package com.javadev.subject;

/**
 * Created by kuba3 on 25.04.2016.
 */
public class SubjectDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject mapToEntity()
    {
        Subject subject = new Subject();
        subject.setName(this.getName());
        return subject;
    }
    public Subject mapToEntity(long id)
    {
        Subject subject = this.mapToEntity();
        subject.setId(id);
        return subject;
    }
}
