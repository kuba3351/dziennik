package com.javadev.security;

import com.javadev.student.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * Created by jakub on 27.06.16.
 */
public class StudentUserDetails implements UserDetails {

    List<Student> students;
    Student student;

    public StudentUserDetails(List<Student> list, Student student)
    {
        this.students = list;
        this.student = student;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String users = StringUtils.collectionToCommaDelimitedString(students);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(users);
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        return student.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
