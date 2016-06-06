package com.javadev.student;

import com.javadev.Class.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
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

    @Autowired
    private ClassRepository classRepository;

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
        model.addAttribute("message", "Student nie znaleziony");
        model.addAttribute("link", "/view/students");
        return "message";
    }
    @RequestMapping(value="/view/student/delete", method = RequestMethod.GET)
    public String delete(@RequestParam long id, Model model)
    {
        if(studentRepository.exists(id))
        {
            studentRepository.delete(id);
            model.addAttribute("message", "Student usunięty");
            model.addAttribute("link", "/view/students");
            return "message";
        }
        model.addAttribute("message", "Student nie znaleziony");
        model.addAttribute("link", "/view/students");
        return "message";
    }
    @RequestMapping(value = "/view/student/add", method = RequestMethod.GET)
    public String addForm(@ModelAttribute(value = "formData") StudentDTO studentDTO, Model model)
    {
        model.addAttribute("actionaddress","/view/student/addstudent");
        model.addAttribute("clazz", classRepository.findAll());
        return "student/addForm";
    }
    @RequestMapping(value="/view/student/addstudent", method = RequestMethod.POST)
    public String addStudent(StudentFormDTO studentFormDTO, Model model)
    {
        if(!classRepository.exists(studentFormDTO.getClass_id()))
        {
            model.addAttribute("message", "klasa nie znaleziona");
            model.addAttribute("link", "/view/students");
            return "message";
        }
        com.javadev.Class.Class clazz = classRepository.getOne(studentFormDTO.getClass_id());
        try {
            studentRepository.save(studentFormDTO.mapToDTO().mapToEntity(clazz));
        }
        catch(ParseException e)
        {
            model.addAttribute("message", "źle wpisana data");
            model.addAttribute("link", "/view/students");
            return "message";
        }
        model.addAttribute("message", "Student dodany");
        model.addAttribute("link", "/view/students");
        return "message";
    }
    @RequestMapping(value="/view/student/update", method = RequestMethod.GET)
    public String update(@RequestParam long id, Model model)
    {
        if(!studentRepository.exists(id))
        {
            model.addAttribute("message", "student nie znaleziony");
            model.addAttribute("link", "/view/students");
            return "message";
        }
        model.addAttribute("formData" ,StudentDTO.getDTO(studentRepository.getOne(id)).mapToFormDTO());
        model.addAttribute("actionaddress", "/view/student/updatestudent?id=" + id);
        model.addAttribute("clazz", classRepository.findAll());
        return "student/addForm";
    }
    @RequestMapping(value="/view/student/updatestudent", method = RequestMethod.POST)
    public String update(StudentFormDTO studentFormDTO, @RequestParam long id, Model model)
    {
        if(!studentRepository.exists(id))
        {
            model.addAttribute("message", "student nie znaleziony");
            model.addAttribute("link", "/view/students");
            return "message";
        }
        Student student = null;
        if(!classRepository.exists(studentFormDTO.getClass_id()))
        {
            model.addAttribute("message", "klasa nie znaleziona");
            model.addAttribute("link", "/view/students");
            return "message";
        }
        com.javadev.Class.Class clazz = classRepository.getOne(studentFormDTO.getClass_id());
        try {
            student = studentFormDTO.mapToDTO().mapToEntity(clazz);
        } catch (ParseException e) {
            model.addAttribute("message", "źle wpisana data");
            model.addAttribute("link", "/view/students");
            return "message";
        }
        student.setId(id);
        studentRepository.save(student);
        model.addAttribute("message", "Student zmieniony");
        model.addAttribute("link", "/view/students");
        return "message";
    }
}
