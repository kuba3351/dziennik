package com.javadev.student;

import com.javadev.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    @RequestMapping(value = "/studentsimple",method = RequestMethod.GET)
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
    @RequestMapping(value="/student/{id}",method= RequestMethod.GET)
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
