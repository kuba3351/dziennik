package com.javadev.subject;

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
    @RequestMapping(value = "/subjectsimple",method = RequestMethod.GET)
    public List<String> displaySimple()
    {
        ArrayList<String> subjects = new ArrayList<>();
        List<Subject> list = display();
        for(int i = 0;i<list.size();i++)
        {
            subjects.add(list.get(i).getName());
        }
        return subjects;
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
    @RequestMapping(value="/subject/{id}",method= RequestMethod.GET)
    @ResponseBody
    public Subject show(@PathVariable long id) throws Exception
    {
        if(subjectRepository.exists(id)){
            return subjectRepository.findOne(id);
        }
        else
            throw new Exception("subject not found");
    }
}
