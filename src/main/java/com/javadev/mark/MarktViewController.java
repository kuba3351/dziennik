package com.javadev.mark;

import com.javadev.student.Student;
import com.javadev.student.StudentRepository;
import com.javadev.subject.Subject;
import com.javadev.subject.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
    public String form(@ModelAttribute(value = "formData") FormDTO formDTO, Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "/mark/form";
    }

    @RequestMapping(value = "/view/mark/show", method = RequestMethod.POST)
    public String show(@ModelAttribute(value = "formData") FormDTO formDTO, @RequestParam(value = "student") long
            student_id, @RequestParam(value = "subject") long subject_id, Model model) {
        if (!studentRepository.existsById(student_id) || !subjectRepository.existsById(subject_id)) {
            model.addAttribute("error", "wrong data");
        }
        else {
            Student student = studentRepository.findById(student_id).get();
            Subject subject = subjectRepository.findById(subject_id).get();
            model.addAttribute("student", student);
            model.addAttribute("subject", subject);
            List<Mark> list = markRepository.findByStudent(student);
            list.retainAll(markRepository.findBySubject(subject));
            List<Mark> kartkowka = new ArrayList<>();
            List<Mark> sprawdzian = new ArrayList<>();
            List<Mark> koncowa = new ArrayList<>();
            for (Mark mark : list) {
                if (mark.getTyp().equals("kartkowka")) { kartkowka.add(mark); }
                if (mark.getTyp().equals("sprawdzian")) { sprawdzian.add(mark); }
                if (mark.getTyp().equals("koncowa")) { koncowa.add(mark); }
            }
            model.addAttribute("kartkowka", kartkowka);
            model.addAttribute("sprawdzian", sprawdzian);
            model.addAttribute("koncowa", koncowa);
        }
        return "/mark/show";
    }

    @RequestMapping(value = "/view/mark/add", method = RequestMethod.POST)
    public String add(FormDTO formDTO, Model model) {
        if (!subjectRepository.existsById(formDTO.getSubject())) {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/mark/form");
            return "message";
        }
        if (!studentRepository.existsById(formDTO.getStudent())) {
            model.addAttribute("message", "Student nie znaleziony");
            model.addAttribute("link", "/view/mark/form");
            return "message";
        }
        Mark mark = new Mark();
        mark.setMark(formDTO.getMark());
        mark.setTyp(formDTO.getTyp());
        Student student = studentRepository.findById(formDTO.getStudent()).get();
        mark.setStudent(student);
        Subject subject = subjectRepository.findById(formDTO.getSubject()).get();
        mark.setSubject(subject);
        List<Mark> lista = markRepository.findByStudent(student);
        lista.retainAll(markRepository.findBySubject(subject));
        if (formDTO.getTyp().equals("koncowa")) {
            if (lista.isEmpty()) {
                model.addAttribute("message", "Zdaje się, że najpierw dajemy oceny cząstkowe");
                model.addAttribute("link", "/view/mark/form");
                return "message";
            }
        }
        for (Mark mark1 : lista) {
            if (mark1.getTyp().equals("koncowa")) {
                model.addAttribute("message", "Zdaje się, że nie wystawiamy już ocen po wystawieniu końcowej");
                model.addAttribute("link", "/view/mark/form");
                return "message";
            }
        }
        if(mark.getMark() <= 5 && mark.getMark() >= 1 && (mark.getTyp().equals("kartkowka") || mark.getTyp().equals("sprawdzian") || mark.getTyp().equals("koncowa"))) {
            markRepository.save(mark);
        }
        else
        {
            model.addAttribute("message", "Błąd walidacji danych");
            model.addAttribute("link", "/view/mark/form");
            return "message";
        }
        model.addAttribute("message", "Dodano ocenę");
        model.addAttribute("link", "/view/mark/form");
        return "message";
    }
}

