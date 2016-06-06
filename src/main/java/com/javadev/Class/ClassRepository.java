package com.javadev.Class;

import com.javadev.subject.Subject;
import com.javadev.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kuba3 on 03.06.2016.
 */
public interface ClassRepository extends JpaRepository<Class, Long> {
    public List<Class> findBySubject(Subject subject);
}
