package com.javadev.student;

import com.javadev.Class.ClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

/**
 * Created by kuba3 on 25.04.2016.
 */
@Slf4j
@RestController
public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ClassRepository classRepository;

    @RequestMapping(value = "/api/student", method = RequestMethod.POST)
    public String add(@RequestBody @Valid StudentDTO studentDTO) {
        if (!classRepository.existsById(studentDTO.getClass_id())) {
            return "class not found";
        }
        Student student = studentDTO.mapToEntity(classRepository.findById(studentDTO.getClass_id()).get());
        log.info("Request to add student: " + student.getName() + " " + student.getLastName());
        log.info("Adding student. Given ID is: " + studentRepository.save(student).getId());
        return "saved";
    }

    @RequestMapping(value = "/api/student/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable long id) {
        if (studentRepository.existsById(id)) {
            log.info("Deleting student with ID:" + id);
            studentRepository.deleteById(id);
            return "deleted";
        }
        else {
            log.warn("Request to delete student With ID: " + id + " whitch is not found!");
            return "student not found";
        }
    }

    @RequestMapping(value = "/api/student", method = RequestMethod.GET)
    public Iterable<Student> display() {
        log.info("Request to show list of students");
        return studentRepository.findAll();
    }

    @RequestMapping(value = "/api/studentsimple", method = RequestMethod.GET)
    public List<String> displaySimple() {
        ArrayList<String> students = new ArrayList<>();
        for(Student s : display()) {
            students.add(s.getName() + " " + s.getLastName());
        }
        return students;
    }

    @RequestMapping(value = "/api/student/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable long id, @RequestBody StudentDTO studentDTO) {
        if (!studentRepository.existsById(id)) {
            return "student not found";
        }
        if (!classRepository.existsById(studentDTO.getClass_id())) {
            return "klasa nie znaleziona";
        }
        classRepository.findById(studentDTO.getClass_id()).ifPresent(clazz -> studentRepository.save(studentDTO.mapToEntity(id, clazz)));
        return "updated";
    }

    @RequestMapping(value = "/api/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Student show(@PathVariable long id) throws Exception {
        return studentRepository.findById(id).orElseThrow(() -> new Exception("student not found"));
    }
}
