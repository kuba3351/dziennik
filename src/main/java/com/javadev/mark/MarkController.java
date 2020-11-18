package com.javadev.mark;

import com.javadev.student.Student;
import com.javadev.student.StudentRepository;
import com.javadev.subject.Subject;
import com.javadev.subject.SubjectRepository;
import com.javadev.teacher.TeacherRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
@RestController
public class MarkController {

    @Autowired
    MarkRepository markRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SubjectRepository subjectRepository;

    Logger logger = LogManager.getLogger(MarkController.class.getName());

    @RequestMapping(value = "/api/mark", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody @Valid MarkDTO markDTO) {
        if (studentRepository.existsById(markDTO.getStudentId()) && subjectRepository.existsById(markDTO.getSubjectId()) &&
                markDTO.getMark() >= 1 && markDTO.getMark() <= 6) {
            Mark mark = markDTO.mapToEntity(studentRepository.findById(markDTO.getStudentId()).get(), subjectRepository
                    .findById(markDTO.getSubjectId()).get());
            logger.info("Request to add templates.mark: " + markDTO.getMark() + " with student ID: " + markDTO
                    .getStudentId() + " subject ID:" + markDTO.getSubjectId());
            Mark save = markRepository.save(mark);
            logger.info(String.format("Addning with ID:%s", save.getId()));
            return new ResponseEntity("Accepted", HttpStatus.ACCEPTED);
        }
        logger.warn("Bad Request to add templates.mark: " + markDTO.getMark() + " with student ID: " + markDTO
                .getStudentId() + " subject ID:" + markDTO.getSubjectId());
        return new ResponseEntity("Bad request", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/api/mark", method = RequestMethod.GET)
    public List<Mark> show() {
        return markRepository.findAll();
    }

    @RequestMapping(value = "/api/mark/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable long id) {
        if (markRepository.existsById(id)) {
            logger.info("Request to delete templates.mark with ID: " + id);
            markRepository.deleteById(id);
            return "deleted";
        }
        else {
            logger.warn("Request to delete templates.mark with ID: " + id + " whitch is not found!");
            return "templates.mark not found";
        }
    }

    @RequestMapping(value = "/api/mark/find", method = RequestMethod.POST)
    public List<Mark> find(@RequestBody MarkDTO markDTO) {
        List<Mark> list = markRepository.findAll();
        if (markDTO.getStudentId() != 0) {
            Student student = studentRepository.findById(markDTO.getStudentId()).get();
            list.retainAll(markRepository.findByStudent(student));
        }
        if (markDTO.getSubjectId() != 0) {
            Subject subject = subjectRepository.findById(markDTO.getSubjectId()).get();
            list.retainAll(markRepository.findBySubject(subject));
        }
        return list;
    }

    @RequestMapping(value = "/api/mark/findsimple", method = RequestMethod.POST)
    public List<Integer> findSimple(@RequestBody MarkDTO markDTO) {
        List<Mark> list = find(markDTO);
        ArrayList<Integer> marks = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            marks.add(list.get(i).getMark());
        }
        return marks;
    }
}
