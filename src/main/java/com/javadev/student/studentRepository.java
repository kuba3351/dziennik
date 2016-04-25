package com.javadev.student;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kuba3 on 25.04.2016.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
