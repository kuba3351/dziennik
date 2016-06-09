package com.javadev.subject;

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
@RestController
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;
    static Logger logger = LogManager.getLogger(SubjectController.class.getName());

    @RequestMapping(value = "/api/subject/add", method = RequestMethod.POST)
    public String add(@RequestBody @Valid SubjectDTO subjectDTO) {
        Subject subject = subjectDTO.mapToEntity();
        logger.info("Request to add new subject: " + subject.getName());
        logger.info("adding with given ID: " + subjectRepository.save(subject).getId());
        return "saved";
    }

    @RequestMapping(value = "/api/subject/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable long id) {
        if (subjectRepository.exists(id)) {
            logger.info("Deleted subject with ID: " + id);
            subjectRepository.delete(id);
            return "deleted";
        }
        else {
            logger.warn("Request to deleted subject with ID: " + id + " whitch is not found!");
            return "subject not found";
        }
    }

    @RequestMapping(value = "/api/subject", method = RequestMethod.GET)
    public List<Subject> display() {
        logger.info("Request to show list of subjects");
        return subjectRepository.findAll();
    }

    @RequestMapping(value = "/api/subjectsimple", method = RequestMethod.GET)
    public List<String> displaySimple() {
        ArrayList<String> subjects = new ArrayList<>();
        List<Subject> list = display();
        for (int i = 0; i < list.size(); i++) {
            subjects.add(list.get(i).getName());
        }
        return subjects;
    }

    @RequestMapping(value = "/api/subject/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable long id, @RequestBody SubjectDTO subjectDTO) {
        if (!subjectRepository.exists(id)) { return "subject not found"; }
        else {
            subjectRepository.save(subjectDTO.mapToEntity(id));
            return "updated";
        }
    }

    @RequestMapping(value = "/api/subject/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Subject show(@PathVariable long id) throws Exception {
        if (subjectRepository.exists(id)) {
            return subjectRepository.findOne(id);
        }
        else { throw new Exception("subject not found"); }
    }
}
