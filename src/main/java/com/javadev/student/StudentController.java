package com.javadev.student;

import com.javadev.student.StudentRepository;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by kuba3 on 25.04.2016.
 */
@RestController
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    static Logger logger = LogManager.getLogger(StudentController.class.getName());
    @RequestMapping(value="/api/student",method=RequestMethod.POST)
    public String add(@RequestBody @Valid StudentDTO studentDTO)
    {
        Student student = studentDTO.mapToEntity();
        logger.info("Request to add student: "+student.getName()+" "+student.getLastName());
        logger.info("Adding student. Given ID is: "+studentRepository.save(student).getId());
        return "saved";
    }
    @RequestMapping(value="/api/student/{id}",method= RequestMethod.DELETE)
    public String delete(@PathVariable long id)
    {
        if(studentRepository.exists(id)){
            logger.info("Deleting student with ID:"+id);
            studentRepository.delete(id);
            return "deleted";
        }
        else {
            logger.warn("Request to delete student With ID: "+id+" whitch is not found!");
            return "student not found";
        }
    }
    @RequestMapping(value = "/api/student",method = RequestMethod.GET)
    public List<Student> display()
    {
        logger.info("Request to show list of students");
        return studentRepository.findAll();
    }
    @RequestMapping(value = "/api/studentsimple",method = RequestMethod.GET)
    public List<String> displaySimple()
    {
        ArrayList<String> students = new ArrayList<>();
        List<Student> list = display();
        for(int i = 0;i<list.size();i++)
        {
            StringBuilder builder = new StringBuilder();
            Student student = list.get(i);
            builder.append(student.getName());
            builder.append(" ");
            builder.append(student.getLastName());
            students.add(builder.toString());
        }
        return students;
    }
    @RequestMapping(value="/api/student/{id}",method=RequestMethod.PUT)
    public String update(@PathVariable long id, @RequestBody StudentDTO studentDTO)
    {
        if(!studentRepository.exists(id))
            return "student not found";
        else {
            studentRepository.save(studentDTO.mapToEntity(id));
            return "updated";
        }
    }
    @RequestMapping(value="/api/student/{id}",method= RequestMethod.GET)
    @ResponseBody
    public Student show(@PathVariable long id) throws Exception
    {
        if(studentRepository.exists(id)){
            return studentRepository.findOne(id);
        }
        else
            throw new Exception("student not found");
    }
}
