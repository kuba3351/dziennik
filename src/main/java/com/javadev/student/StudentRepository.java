package com.javadev.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    public List<Student> findByClazz(com.javadev.Class.Class clazz);
    public Student findByLogin(String login);
}
