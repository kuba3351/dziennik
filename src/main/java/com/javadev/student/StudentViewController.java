package com.javadev.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kuba3 on 10.05.2016.
 */

@Controller
public class StudentViewController {

    @Autowired
    private StudentRepository studentRepository;

    @RequestMapping(value="/view/students", method = RequestMethod.GET)
    public String studentsListView(Model model)
    {
        model.addAttribute("list", studentRepository.findAll());
        return "student/list";
    }
    @RequestMapping(value = "/view/student", method = RequestMethod.GET)
    public String studentDetails(@RequestParam long id, Model model)
    {
        if(studentRepository.exists(id))
        {
            model.addAttribute("student", studentRepository.getOne(id));
            return "student/details";
        }
        model.addAttribute("error", "Student nie znaleziony");
        return "student/details";
    }
    @RequestMapping(value="/view/student/delete", method = RequestMethod.GET)
    public String delete(@RequestParam long id, Model model)
    {
        if(studentRepository.exists(id))
        {
            studentRepository.delete(id);
            return "student/delete";
        }
        model.addAttribute("error", "Student nie znaleziony");
        return "student/delete";
    }
    @RequestMapping(value = "/view/student/add", method = RequestMethod.GET)
    public String addForm(@ModelAttribute(value = "formData") StudentDTO studentDTO)
    {
        return "student/addForm";
    }
    @RequestMapping(value="/view/student/addstudent", method = RequestMethod.POST)
    public String addStudent(StudentFormDTO studentFormDTO, Model model)
    {
        studentRepository.save(studentFormDTO.mapToDTO().mapToEntity());
        return "student/add";
    }
    @RequestMapping(value="/view/student/update", method = RequestMethod.GET)
    public String update(@RequestParam long id, Model model)
    {
        if(!studentRepository.exists(id))
        {
            model.addAttribute("error", "student nie znaleziony");
        }
        else
        {
            model.addAttribute("formData" ,StudentDTO.getDTO(studentRepository.getOne(id)).mapToFormDTO());
            model.addAttribute("id", id);
        }
        return "student/updateForm";
    }
    @RequestMapping(value="/view/student/updatestudent", method = RequestMethod.POST)
    public String update(StudentFormDTO studentFormDTO, @RequestParam long id, Model model)
    {
        if(!studentRepository.exists(id))
        {
            model.addAttribute("error", "student nie znaleziony");
        }
        else
        {
            Student student = studentFormDTO.mapToDTO().mapToEntity();
            student.setId(id);
            studentRepository.save(student);
        }
        return "student/add";
    }
}
