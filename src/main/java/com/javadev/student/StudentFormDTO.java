package com.javadev.student;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by kuba3 on 03.06.2016.
 */
public class StudentFormDTO {
    private String name;
    private String lastName;
    private String pesel;
    private String address;
    private String birthday;
    private String sex;
    private long class_id;

    public long getClass_id() {
        return class_id;
    }

    public void setClass_id(long class_id) {
        this.class_id = class_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

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

    public StudentDTO mapToDTO() throws ParseException {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(name);
        studentDTO.setLastName(lastName);
        studentDTO.setAddress(address);
        studentDTO.setPesel(pesel);
        studentDTO.setSex(sex);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        studentDTO.setBirthday(simpleDateFormat.parse(birthday));
        return studentDTO;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
