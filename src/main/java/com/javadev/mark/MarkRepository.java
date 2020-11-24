package com.javadev.mark;

import com.javadev.student.Student;
import com.javadev.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kuba3 on 26.04.2016.
 */
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findBySubject(Subject subject);

    List<Mark> findByStudent(Student student);
}
