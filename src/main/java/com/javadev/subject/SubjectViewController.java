package com.javadev.subject;

import com.javadev.Class.Class;
import com.javadev.Class.ClassRepository;
import com.javadev.teacher.Teacher;
import com.javadev.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String show(@ModelAttribute(value = "formData") SubjectDTO subjectDTO, Model model) {
        model.addAttribute("list", subjectRepository.findAll());
        return "/subject/list";
    }

    @RequestMapping(value = "/view/subject/delete", method = RequestMethod.GET)
    public String delete(@RequestParam long id, Model model) {
        if (subjectRepository.exists(id)) {
            try {
                subjectRepository.delete(id);
            } catch (Exception e) {
                model.addAttribute("message","bąd bazy danych");
                model.addAttribute("link", "/view/classes");
                return "message";
            }
            model.addAttribute("message", "Przedmiot usunięty");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        model.addAttribute("message", "Przedmiot nie znaleziony");
        model.addAttribute("link", "/view/subjects");
        return "message";
    }

    @RequestMapping(value = "/view/subject/addsubject", method = RequestMethod.POST)
    public String add(@RequestParam String name, Model model) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(name);
        subjectRepository.save(subjectDTO.mapToEntity());
        model.addAttribute("message", "Przedmiot dodany");
        model.addAttribute("link", "/view/subjects");
        return "message";
    }

    @RequestMapping(value = "/view/subject/addteacher", method = RequestMethod.GET)
    public String addTeacher(@ModelAttribute(value = "formData") ListForm listForm, @RequestParam long id, Model
            model) {
        List<Teacher> list = teacherRepository.findAll();
        if (!subjectRepository.exists(id)) {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.findOne(id);
        list.removeAll(subject.getTeachers());
        model.addAttribute("elements", list);
        return "list";
    }

    @RequestMapping(value = "/view/subject/addteacher", method = RequestMethod.POST)
    public String addTeacher(@RequestParam long id, ListForm listForm, Model model) {
        if (!subjectRepository.exists(id)) {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        if (!teacherRepository.exists(listForm.getOption())) {
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
    public String removeTeacher(@ModelAttribute(value = "formData") ListForm listForm, @RequestParam long id, Model
            model) {
        if (!subjectRepository.exists(id)) {
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
    public String removeTeacher(@RequestParam long id, ListForm listForm, Model model) {
        if (!subjectRepository.exists(id)) {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        if (!teacherRepository.exists(listForm.getOption())) {
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
    public String view(@RequestParam long id, Model model) {
        if (!subjectRepository.exists(id)) {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.getOne(id);
        model.addAttribute("subject", subject);
        model.addAttribute("classes", classRepository.findBySubject(subject));
        return "subject/details";
    }

    @RequestMapping(value = "/view/subject/addclass", method = RequestMethod.GET)
    public String addClassForm(@RequestParam long id, @ModelAttribute(value = "formData") ListForm listForm, Model
            model) {
        if (!subjectRepository.exists(id)) {
            model.addAttribute("error", "przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        List<Class> list = classRepository.findAll();
        list.removeAll(classRepository.findBySubject(subjectRepository.getOne(id)));
        model.addAttribute("elements", list);
        model.addAttribute("classes", 1);
        return "list";
    }

    @RequestMapping(value = "/view/subject/addclass", method = RequestMethod.POST)
    public String addClass(@RequestParam long id, ListForm listForm, Model model) {
        if (!subjectRepository.exists(id)) {
            model.addAttribute("error", "przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        if (!classRepository.exists(listForm.getOption())) {
            model.addAttribute("error", "klasa nie znaleziona");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Class clazz = classRepository.findOne(listForm.getOption());
        clazz.getSubject().add(subjectRepository.findOne(id));
        classRepository.save(clazz);
        model.addAttribute("message", "Dodano klasę");
        model.addAttribute("link", "/view/subjects");
        return "message";
    }

    @RequestMapping(value = "/view/subject/removeclass", method = RequestMethod.GET)
    public String removeClassForm(@RequestParam long id, @ModelAttribute(value = "formData") ListForm listForm, Model
            model) {
        if (!subjectRepository.exists(id)) {
            model.addAttribute("error", "przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        List<Class> list = classRepository.findBySubject(subjectRepository.getOne(id));
        model.addAttribute("elements", list);
        model.addAttribute("classes", 1);
        return "list";
    }

    @RequestMapping(value = "/view/subject/removeclass", method = RequestMethod.POST)
    public String removeClass(@RequestParam long id, ListForm listForm, Model model) {
        if (!subjectRepository.exists(id)) {
            model.addAttribute("error", "przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        if (!classRepository.exists(listForm.getOption())) {
            model.addAttribute("error", "klasa nie znaleziona");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Class clazz = classRepository.findOne(listForm.getOption());
        clazz.getSubject().remove(subjectRepository.findOne(id));
        classRepository.save(clazz);
        model.addAttribute("message", "Usunięto klasę");
        model.addAttribute("link", "/view/subjects");
        return "message";
    }
}
