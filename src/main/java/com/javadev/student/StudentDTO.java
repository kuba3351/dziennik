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
    private String sex;
    private long class_id;
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getClass_id() {
        return class_id;
    }

    public void setClass_id(long class_id) {
        this.class_id = class_id;
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

    public Student mapToEntity(com.javadev.Class.Class clazz) {
        Student student = new Student();
        student.setName(this.getName());
        student.setLastName(this.getLastName());
        student.setPesel(this.pesel);
        student.setAddress(this.getAddress());
        student.setBirthday(this.getBirthday());
        student.setSex(sex);
        student.setClazz(clazz);
        student.setLogin(this.login);
        student.setPassword(this.password);
        return student;
    }

    public Student mapToEntity(long id, com.javadev.Class.Class clazz) {
        Student student = this.mapToEntity(clazz);
        student.setId(id);
        return student;
    }

    public StudentFormDTO mapToFormDTO() {
        StudentFormDTO studentFormDTO = new StudentFormDTO();
        studentFormDTO.setName(name);
        studentFormDTO.setLastName(lastName);
        studentFormDTO.setAddress(address);
        studentFormDTO.setPesel(pesel);
        studentFormDTO.setSex(sex);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        studentFormDTO.setBirthday(simpleDateFormat.format(birthday));
        studentFormDTO.setClass_id(class_id);
        studentFormDTO.setLogin(login);
        studentFormDTO.setPassword(password);
        return studentFormDTO;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public static StudentDTO getDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setAddress(student.getAddress());
        studentDTO.setPesel(student.getPesel());
        studentDTO.setSex(student.getSex());
        studentDTO.setBirthday(student.getBirthday());
        studentDTO.setClass_id(student.getClazz().getId());
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
