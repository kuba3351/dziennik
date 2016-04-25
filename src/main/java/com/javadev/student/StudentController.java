package com.javadev.student;

import com.javadev.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
@RestController
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @RequestMapping(value="/student/add",method=RequestMethod.POST)
    public String add(@RequestBody @Valid StudentDTO studentDTO)
    {
        studentRepository.save(studentDTO.mapToEntity());
        return "saved";
    }
    @RequestMapping(value="/student/delete/{id}",method= RequestMethod.DELETE)
    public String delete(@PathVariable long id)
    {
        if(studentRepository.exists(id)){
            studentRepository.delete(id);
            return "deleted";
        }
        else
            return "student not found";
    }
    @RequestMapping(value = "/student",method = RequestMethod.GET)
    public List<Student> display()
    {
        return studentRepository.findAll();
    }
    @RequestMapping(value="/student/update/{id}",method=RequestMethod.PUT)
    public String update(@PathVariable long id, @RequestBody StudentDTO studentDTO)
    {
        if(!studentRepository.exists(id))
            return "student not found";
        else {
            studentRepository.save(studentDTO.mapToEntity(id));
            return "updated";
        }
    }
}
