package com.javadev.subject;

import com.javadev.Class.ClassRepository;
import com.javadev.student.StudentDTO;
import com.javadev.student.StudentRepository;
import com.javadev.teacher.Teacher;
import com.javadev.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by kuba3 on 10.05.2016.
 */

@Controller
public class SubjectViewController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassRepository classRepository;

    @RequestMapping(value = "/view/subjects", method = RequestMethod.GET)
    public String show(@ModelAttribute(value = "formData") SubjectDTO subjectDTO, Model model)
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
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        model.addAttribute("message","Przedmiot nie znaleziony");
        model.addAttribute("link", "/view/subjects");
        return "message";
    }
    @RequestMapping(value = "/view/subject/addsubject", method = RequestMethod.POST)
    public String add(@RequestParam String name, Model model)
    {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(name);
        subjectRepository.save(subjectDTO.mapToEntity());
        model.addAttribute("message", "Przedmiot dodany");
        model.addAttribute("link", "/view/subjects");
        return "message";
    }
    @RequestMapping(value = "/view/subject/addteacher", method = RequestMethod.GET)
    public String addTeacher(@ModelAttribute(value = "formData") ListForm listForm , @RequestParam long id, Model model)
    {
        List<Teacher> list = teacherRepository.findAll();
        if(!subjectRepository.exists(id))
        {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.findOne(id);
        for(int i = 0;i<list.size();i++)
        {
            if(subject.getTeachers().contains(list.get(i)))
            {
                list.remove(i--);
            }
        }
        model.addAttribute("elements", list);
        return "list";
    }
    @RequestMapping(value = "/view/subject/addteacher", method = RequestMethod.POST)
    public String addTeacher(@RequestParam long id, ListForm listForm, Model model)
    {
        if(!subjectRepository.exists(id))
        {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        if(!teacherRepository.exists(listForm.getOption()))
        {
            model.addAttribute("message", "Nauczyciel nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.getOne(id);
        subject.getTeachers().add(teacherRepository.getOne(listForm.getOption()));
        subjectRepository.save(subject);
        model.addAttribute("message", "Nauczyciel dodany");
        model.addAttribute("link", "/view/subjects");
        return "message";
    }
    @RequestMapping(value = "/view/subject/removeteacher", method = RequestMethod.GET)
    public String removeTeacher(@ModelAttribute(value = "formData") ListForm listForm , @RequestParam long id, Model model)
    {
        if(!subjectRepository.exists(id))
        {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.findOne(id);
        List<Teacher> list = subject.getTeachers();
        model.addAttribute("elements", list);
        return "list";
    }
    @RequestMapping(value = "/view/subject/removeteacher", method = RequestMethod.POST)
    public String removeTeacher(@RequestParam long id, ListForm listForm, Model model)
    {
        if(!subjectRepository.exists(id))
        {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        if(!teacherRepository.exists(listForm.getOption()))
        {
            model.addAttribute("message", "Nauczyciel nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.getOne(id);
        subject.getTeachers().remove(teacherRepository.getOne(listForm.getOption()));
        subjectRepository.save(subject);
        model.addAttribute("message", "Nauczyciel usunięty");
        model.addAttribute("link", "/view/subjects");
        return "message";
    }
    @RequestMapping(value = "/view/subject", method = RequestMethod.GET)
    public String view(@RequestParam long id, Model model)
    {
        if(!subjectRepository.exists(id))
        {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.getOne(id);
        model.addAttribute("subject", subject);
        model.addAttribute("classes", classRepository.findBySubject(subject));
        return "subject/details";
    }
}
