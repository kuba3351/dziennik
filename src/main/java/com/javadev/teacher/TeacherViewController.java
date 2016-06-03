package com.javadev.teacher;

import com.javadev.teacher.TeacherDTO;
import com.javadev.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;

/**
 * Created by kuba3 on 10.05.2016.
 */

@Controller
public class TeacherViewController {

    @Autowired
    private TeacherRepository teacherRepository;

    @RequestMapping(value="/view/teachers", method = RequestMethod.GET)
    public String teachersListView(Model model)
    {
        model.addAttribute("list", teacherRepository.findAll());
        return "teacher/list";
    }
    @RequestMapping(value = "/view/teacher", method = RequestMethod.GET)
    public String teacherDetails(@RequestParam long id, Model model)
    {
        if(teacherRepository.exists(id))
        {
            model.addAttribute("teacher", teacherRepository.getOne(id));
            return "teacher/details";
        }
        model.addAttribute("error", "Nauczyciel nie znaleziony");
        return "teacher/details";
    }
    @RequestMapping(value="/view/teacher/delete", method = RequestMethod.GET)
    public String delete(@RequestParam long id, Model model)
    {
        if(teacherRepository.exists(id))
        {
            teacherRepository.delete(id);
            return "teacher/delete";
        }
        model.addAttribute("error", "Nauczyciel nie znaleziony");
        return "teacher/delete";
    }
    @RequestMapping(value = "/view/teacher/add", method = RequestMethod.GET)
    public String addForm(@ModelAttribute(value = "formData") TeacherDTO teacherDTO)
    {
        return "teacher/addForm";
    }
    @RequestMapping(value="/view/teacher/addteacher", method = RequestMethod.POST)
    public String addTeacher(TeacherFormDTO teacherFormDTO, Model model)
    {
        teacherRepository.save(teacherFormDTO.mapToDTO().mapToEntity());
        return "teacher/add";
    }
    @RequestMapping(value="/view/teacher/edit", method = RequestMethod.GET)
    public String update(@RequestParam long id, Model model)
    {
        if(!teacherRepository.exists(id))
        {
            model.addAttribute("error", "nauczyciel nie znaleziony");
        }
        else
        {
            model.addAttribute("formData" ,TeacherDTO.getDTO(teacherRepository.getOne(id)).mapToFormDTO());
            model.addAttribute("id", id);
        }
        return "teacher/updateForm";
    }
    @RequestMapping(value="/view/teacher/updateteacher", method = RequestMethod.POST)
    public String update(TeacherFormDTO teacherFormDTO, @RequestParam long id, Model model)
    {
        if(!teacherRepository.exists(id))
        {
            model.addAttribute("error", "teacher not found");
        }
        else
        {
            Teacher teacher = teacherFormDTO.mapToDTO().mapToEntity();
            teacher.setId(id);
            teacherRepository.save(teacher);
        }
        return "teacher/add";
    }
}
