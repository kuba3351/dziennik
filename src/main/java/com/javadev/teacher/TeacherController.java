package com.javadev.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
@RestController
public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;
    @RequestMapping(value="/teacher/add",method=RequestMethod.POST)
    public String add(@RequestBody @Valid TeacherDTO teacherDTO)
    {
        teacherRepository.save(teacherDTO.mapToEntity());
        return "saved";
    }
    @RequestMapping(value="/teacher/delete/{id}",method=RequestMethod.DELETE)
    public String delete(@PathVariable long id)
    {
        if(teacherRepository.exists(id)){
            teacherRepository.delete(id);
            return "deleted";
        }
        else
            return "teacher not found";
    }
    @RequestMapping(value = "/teacher",method = RequestMethod.GET)
    public List<Teacher> display()
    {
        return teacherRepository.findAll();
    }
    @RequestMapping(value="/teacher/update/{id}",method=RequestMethod.PUT)
    public String update(@PathVariable long id, @RequestBody TeacherDTO teacherDTO)
    {
        if(!teacherRepository.exists(id))
            return "teacher not found";
        else {
            teacherRepository.save(teacherDTO.mapToEntity(id));
            return "updated";
        }
    }
}
