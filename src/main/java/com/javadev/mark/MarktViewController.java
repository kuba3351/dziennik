package com.javadev.mark;

import com.javadev.student.StudentRepository;
import com.javadev.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MarktViewController {
    @Autowired
    MarkRepository markRepository;
    StudentRepository studentRepository;
    TeacherRepository teacherRepository;
    @RequestMapping(value = "/view/mark/form", method = RequestMethod.GET)
    public String form(@ModelAttribute(value = "formData") FormDTO formDTO)
    {
        return "/mark/form";
    }
    @RequestMapping(value = "/view/mark/show", method = RequestMethod.POST)
    public String show(@ModelAttribute(value = "formData") MarkDTO markDTO, @RequestParam String name, @RequestParam String lastName, @RequestParam String subject, Model model)
    {
        List<Mark> list = markRepository.findAll();
        for(Mark mark : list)
        {
            if(!mark.getStudent().getName().equals(name) && mark.getStudent().getLastName().equals(lastName) && !mark.getSubject().getName().equals(subject))
                list.remove(mark);
        }
        model.addAttribute("name", name);
        model.addAttribute("lastName", lastName);
        model.addAttribute("subject", subject);
        model.addAttribute("list", list);
        return "/mark/show";
    }
}

