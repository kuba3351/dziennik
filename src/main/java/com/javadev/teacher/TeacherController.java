package com.javadev.teacher;

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
public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;
    static Logger logger = LogManager.getLogger(TeacherController.class.getName());

    @RequestMapping(value="/teacher/add",method=RequestMethod.POST)
    public String add(@RequestBody @Valid TeacherDTO teacherDTO)
    {
        Teacher teacher = teacherDTO.mapToEntity();
        logger.info("Request to add new teacher: "+teacher.getName()+" "+teacher.getLastName());
        logger.info("Adding new teacher with given ID: "+teacherRepository.save(teacher));
        return "saved";
    }
    @RequestMapping(value="/teacher/delete/{id}",method=RequestMethod.DELETE)
    public String delete(@PathVariable long id)
    {
        if(teacherRepository.exists(id)){
            teacherRepository.delete(id);
            logger.info("deleted teacher with ID:"+id);
            return "deleted";
        }
        else {
            logger.warn("Request to delete teacher with ID: "+id+" which is not found!");
            return "teacher not found";
        }
    }
    @RequestMapping(value = "/teacher",method = RequestMethod.GET)
    public List<Teacher> display()
    {
        logger.info("request to show list of teachers");
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
            throw new Exception("teacher not found");
    }
}
