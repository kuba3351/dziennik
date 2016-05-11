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
    public String addTeacher(@RequestParam(value = "name") String name, @RequestParam(value = "lastName") String lastName, @RequestParam(value = "address") String address, @RequestParam(value = "pesel") String pesel, @RequestParam(value = "birthday") String birthday, Model model)
    {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName(name);
        teacherDTO.setLastName(lastName);
        teacherDTO.setAddress(address);
        teacherDTO.setPesel(pesel);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            teacherDTO.setBirthday(simpleDateFormat.parse(birthday));
        }
        catch(Exception e)
        {
            model.addAttribute("message","Źle wpisana data");
            return "teacher/add";
        }
        teacherRepository.save(teacherDTO.mapToEntity());
        return "teacher/add";
    }
}
