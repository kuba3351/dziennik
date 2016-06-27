package com.javadev.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    public List<Student> findByClazz(com.javadev.Class.Class clazz);
    public Student findByLogin(String login);
}
