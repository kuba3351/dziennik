package com.javadev.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javadev.student.StudentFormDTO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kuba3 on 03.06.2016.
 */
public class TeacherFormDTO {
    private String name;
    private String lastName;
    private String pesel;
    private String address;
    private String birthday;

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
    public TeacherDTO mapToDTO()
    {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName(name);
        teacherDTO.setLastName(lastName);
        teacherDTO.setAddress(address);
        teacherDTO.setPesel(pesel);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            teacherDTO.setBirthday(simpleDateFormat.parse(birthday));
        }
        catch(Exception e)
        {
            LogManager.getLogger(TeacherFormDTO.class.getName()).error(String.format("Date parsing error %s",birthday));
        }
        return teacherDTO;
    }
}
