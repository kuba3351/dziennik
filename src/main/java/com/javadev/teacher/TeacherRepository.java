package com.javadev.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by kuba3 on 25.04.2016.
 */
public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long> {
}
