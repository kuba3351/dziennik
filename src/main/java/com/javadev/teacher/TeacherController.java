package com.javadev.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    @RequestMapping(value = "/teachersimple",method = RequestMethod.GET)
    public List<String> displaySimple()
    {
        ArrayList<String> teachers = new ArrayList<>();
        List<Teacher> list = display();
        for(int i = 0;i<list.size();i++)
        {
            StringBuilder builder = new StringBuilder();
            Teacher teacher = list.get(i);
            builder.append(teacher.getName());
            builder.append(" ");
            builder.append(teacher.getLastName());
            teachers.add(builder.toString());
        }
        return teachers;
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
    @RequestMapping(value="/teacher/{id}",method= RequestMethod.GET)
    @ResponseBody
    public Teacher show(@PathVariable long id) throws Exception
    {
        if(teacherRepository.exists(id)){
            return teacherRepository.findOne(id);
        }
        else
            throw new Exception("student not found");
    }
}
