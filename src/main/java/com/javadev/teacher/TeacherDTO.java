package com.javadev.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kuba3 on 25.04.2016.
 */
public class TeacherDTO {
    private String name;
    private String lastName;
    private String pesel;
    private String address;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;

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
        teacher.setAddress(this.address);
        teacher.setPesel(this.pesel);
        teacher.setBirthday(this.birthday);
        return teacher;
    }
    public Teacher mapToEntity(long id)
    {
        Teacher teacher = this.mapToEntity();
        teacher.setId(id);
        return teacher;
    }
    public TeacherFormDTO mapToFormDTO()
    {
        TeacherFormDTO teacherFormDTO = new TeacherFormDTO();
        teacherFormDTO.setName(name);
        teacherFormDTO.setLastName(lastName);
        teacherFormDTO.setAddress(address);
        teacherFormDTO.setPesel(pesel);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        teacherFormDTO.setBirthday(simpleDateFormat.format(birthday));
        return teacherFormDTO;
    }
    public static TeacherDTO getDTO(Teacher teacher)
    {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName(teacher.getName());
        teacherDTO.setLastName(teacher.getLastName());
        teacherDTO.setAddress(teacher.getAddress());
        teacherDTO.setPesel(teacher.getPesel());
        teacherDTO.setBirthday(teacher.getBirthday());
        return teacherDTO;
    }
}
