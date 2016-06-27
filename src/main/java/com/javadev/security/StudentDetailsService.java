package com.javadev.security;

import com.javadev.student.Student;
import com.javadev.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jakub on 27.06.16.
 */
@Service("studentDetailsService")
public class StudentDetailsService implements UserDetailsService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Student student = studentRepository.findByLogin(s);
        if(student == null)
            throw new UsernameNotFoundException("Student not found");
        else
            return new StudentUserDetails(studentRepository.findAll(), student);
    }
}
