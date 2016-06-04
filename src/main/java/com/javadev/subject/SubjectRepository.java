package com.javadev.subject;

import com.javadev.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByTeachers(Teacher teacher);
}
