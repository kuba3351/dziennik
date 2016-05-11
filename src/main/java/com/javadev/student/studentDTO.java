package com.javadev.student;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kuba3 on 25.04.2016.
 */
public class StudentDTO {
    private String name;
    private String lastName;
    private String pesel;
    private String address;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;

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
        student.setPesel(this.pesel);
        student.setAddress(this.getAddress());
        student.setBirthday(this.getBirthday());
        return student;
    }
    public Student mapToEntity(long id)
    {
        Student student = this.mapToEntity();
        student.setId(id);
        return student;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
