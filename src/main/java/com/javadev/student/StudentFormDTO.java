package com.javadev.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
