package com.javadev.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;
    @RequestMapping(value="/subject/add",method= RequestMethod.POST)
    public String add(@RequestBody @Valid SubjectDTO subjectDTO)
    {
        subjectRepository.save(subjectDTO.mapToEntity());
        return "saved";
    }
    @RequestMapping(value="/subject/delete/{id}",method=RequestMethod.DELETE)
    public String delete(@PathVariable long id)
    {
        if(subjectRepository.exists(id)){
            subjectRepository.delete(id);
            return "deleted";
        }
        else
            return "subject not found";
    }
    @RequestMapping(value = "/subject",method = RequestMethod.GET)
    public List<Subject> display()
    {
        return subjectRepository.findAll();
    }
    @RequestMapping(value="/subject/update/{id}",method=RequestMethod.PUT)
    public String update(@PathVariable long id, @RequestBody SubjectDTO subjectDTO)
    {
        if(!subjectRepository.exists(id))
            return "subject not found";
        else {
            subjectRepository.save(subjectDTO.mapToEntity(id));
            return "updated";
        }
    }
}
