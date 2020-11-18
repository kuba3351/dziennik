package com.javadev.teacher;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
@Slf4j
@RestController
public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;

    @RequestMapping(value = "/api/teacher/add", method = RequestMethod.POST)
    public String add(@RequestBody @Valid TeacherDTO teacherDTO) {
        Teacher teacher = teacherDTO.mapToEntity();
        log.info("Request to add new teacher: " + teacher.getName() + " " + teacher.getLastName());
        log.info("Adding new teacher with given ID: " + teacherRepository.save(teacher));
        return "saved";
    }

    @RequestMapping(value = "/api/teacher/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            log.info("deleted teacher with ID:" + id);
            return "deleted";
        }
        else {
            log.warn("Request to delete teacher with ID: " + id + " which is not found!");
            return "teacher not found";
        }
    }

    @RequestMapping(value = "/api/teacher", method = RequestMethod.GET)
    public Iterable<Teacher> display() {
        log.info("request to show list of teachers");
        return teacherRepository.findAll();
    }

    @RequestMapping(value = "/api/teachersimple", method = RequestMethod.GET)
    public List<String> displaySimple() {
        ArrayList<String> teachers = new ArrayList<>();
        for (Teacher teacher : display()) {
            String builder = teacher.getName() +
                    " " +
                    teacher.getLastName();
            teachers.add(builder);
        }
        return teachers;
    }

    @RequestMapping(value = "/api/teacher/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable long id, @RequestBody TeacherDTO teacherDTO) {
        if (!teacherRepository.existsById(id)) { return "teacher not found"; }
        else {
            teacherRepository.save(teacherDTO.mapToEntity(id));
            return "updated";
        }
    }

    @RequestMapping(value = "/teacher/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Teacher show(@PathVariable long id) throws Exception {
        if (teacherRepository.existsById(id)) {
            return teacherRepository.findById(id).get();
        }
        else { throw new Exception("teacher not found"); }
    }
}
