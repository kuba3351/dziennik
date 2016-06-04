package com.javadev.mark;

import com.javadev.student.Student;
import com.javadev.student.StudentRepository;
import com.javadev.subject.Subject;
import com.javadev.subject.SubjectRepository;
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

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @RequestMapping(value = "/view/mark/form", method = RequestMethod.GET)
    public String form(@ModelAttribute(value = "formData") FormDTO formDTO, Model model)
    {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "/mark/form";
    }
    @RequestMapping(value = "/view/mark/show", method = RequestMethod.POST)
    public String show(@ModelAttribute(value = "formData") FormDTO formDTO, @RequestParam(value = "student") long student_id,@RequestParam(value = "subject") long subject_id, Model model)
    {
        List<Mark> list = markRepository.findAll();
        if(!studentRepository.exists(student_id) || !subjectRepository.exists(subject_id))
        {
            model.addAttribute("error", "wrong data");
        }
        else
        {
            Student student = studentRepository.getOne(student_id);
            Subject subject = subjectRepository.getOne(subject_id);
            model.addAttribute("student", student);
            model.addAttribute("subject", subject);
            list.retainAll(markRepository.findByStudent(student));
            list.retainAll(markRepository.findBySubject(subject));
            model.addAttribute("list", list);
        }
        return "/mark/show";
    }
    @RequestMapping(value = "/view/mark/add", method = RequestMethod.POST)
    public String add(FormDTO formDTO, Model model)
    {
        if(!subjectRepository.exists(formDTO.getSubject()))
        {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/mark/form");
            return "message";
        }
        if(!studentRepository.exists(formDTO.getStudent()))
        {
            model.addAttribute("message", "Student nie znaleziony");
            model.addAttribute("link", "/view/mark/form");
            return "message";
        }
        Mark mark = new Mark();
        mark.setMark(formDTO.getMark());
        mark.setStudent(studentRepository.findOne(formDTO.getStudent()));
        mark.setSubject(subjectRepository.findOne(formDTO.getSubject()));
        markRepository.save(mark);
        model.addAttribute("message", "Dodano ocenę");
        model.addAttribute("link", "/view/mark/form");
        return "message";
    }
}

