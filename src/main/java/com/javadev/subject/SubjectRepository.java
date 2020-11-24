package com.javadev.subject;

import com.javadev.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long> {
    List<Subject> findByTeachers(Teacher teacher);
}
