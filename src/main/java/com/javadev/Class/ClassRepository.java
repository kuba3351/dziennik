package com.javadev.Class;

import com.javadev.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by kuba3 on 03.06.2016.
 */
public interface ClassRepository extends PagingAndSortingRepository<Class, Long> {
    List<Class> findBySubject(Subject subject);
    Class findByName(String name);
}
