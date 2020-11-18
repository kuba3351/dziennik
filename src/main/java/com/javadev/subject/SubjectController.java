package com.javadev.subject;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by kuba3 on 25.04.2016.
 */
@Slf4j
@RestController
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    @RequestMapping(value = "/api/subject/add", method = RequestMethod.POST)
    public String add(@RequestBody @Valid SubjectDTO subjectDTO) {
        Subject subject = subjectDTO.mapToEntity();
        log.info("Request to add new subject: " + subject.getName());
        log.info("adding with given ID: " + subjectRepository.save(subject).getId());
        return "saved";
    }

    @RequestMapping(value = "/api/subject/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable long id) {
        if (subjectRepository.existsById(id)) {
            log.info("Deleted subject with ID: " + id);
            subjectRepository.deleteById(id);
            return "deleted";
        }
        else {
            log.warn("Request to deleted subject with ID: " + id + " whitch is not found!");
            return "subject not found";
        }
    }

    @RequestMapping(value = "/api/subject", method = RequestMethod.GET)
    public Iterable<Subject> display() {
        log.info("Request to show list of subjects");
        return subjectRepository.findAll();
    }

    @RequestMapping(value = "/api/subjectsimple", method = RequestMethod.GET)
    public List<String> displaySimple() {
        ArrayList<String> subjects = new ArrayList<>();
        for (Subject s : display()) {
            subjects.add(s.getName());
        }
        return subjects;
    }

    @RequestMapping(value = "/api/subject/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable long id, @RequestBody SubjectDTO subjectDTO) {
        if (!subjectRepository.existsById(id)) { return "subject not found"; }
        else {
            subjectRepository.save(subjectDTO.mapToEntity(id));
            return "updated";
        }
    }

    @RequestMapping(value = "/api/subject/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Subject> show(@PathVariable long id) throws Exception {
        if (subjectRepository.existsById(id)) {
            return subjectRepository.findById(id);
        }
        else { throw new Exception("subject not found"); }
    }
}
