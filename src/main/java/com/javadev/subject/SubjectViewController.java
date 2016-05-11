package com.javadev.subject;

import com.javadev.student.StudentDTO;
import com.javadev.student.StudentRepository;
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
public class SubjectViewController {

    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping(value = "/view/subjects", method = RequestMethod.GET)
    public String show(Model model)
    {
        model.addAttribute("list", subjectRepository.findAll());
        return "/subject/list";
    }
    @RequestMapping(value = "/view/subject/delete", method = RequestMethod.GET)
    public String delete(@RequestParam long id, Model model)
    {
        if(subjectRepository.exists(id))
        {
            subjectRepository.delete(id);
            model.addAttribute("message", "Przedmiot usunięty");
            return "subject/delete";
        }
        model.addAttribute("message","Przedmiot nie znaleziony");
        return "subject/delete";
    }
}
