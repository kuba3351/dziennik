package com.javadev.subject;

import com.javadev.Class.Class;
import com.javadev.Class.ClassRepository;
import com.javadev.teacher.Teacher;
import com.javadev.teacher.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

/**
 * Created by kuba3 on 10.05.2016.
 */

@Controller
@Slf4j
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
        return "subject/list";
    }

    @RequestMapping(value = "/view/subject/delete", method = RequestMethod.GET)
    public String delete(@RequestParam long id, Model model) {
        if (subjectRepository.existsById(id)) {
            try {
                subjectRepository.deleteById(id);
            } catch (Exception e) {
                model.addAttribute("message","bąd bazy danych");
                model.addAttribute("link", "/view/classes");
                return "message";
            }
            model.addAttribute("message", "Przedmiot usunięty");
            model.addAttribute("link", "/view/subjects");
            log.info("Subject deleted!");
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
        log.info("subject added! {}", keyValue("subject", subjectDTO.getName()));
        return "message";
    }

    @RequestMapping(value = "/view/subject/addteacher", method = RequestMethod.GET)
    public String addTeacher(@ModelAttribute(value = "formData") ListForm listForm, @RequestParam long id, Model
            model) {
        List<Teacher> list = StreamSupport.stream(teacherRepository.findAll().spliterator(), false).collect(Collectors.toList());
        if (!subjectRepository.existsById(id)) {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.findById(id).get();
        list.removeAll(subject.getTeachers());
        model.addAttribute("elements", list);
        return "list";
    }

    @RequestMapping(value = "/view/subject/addteacher", method = RequestMethod.POST)
    public String addTeacher(@RequestParam long id, ListForm listForm, Model model) {
        if (!subjectRepository.existsById(id)) {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        if (!teacherRepository.existsById(listForm.getOption())) {
            model.addAttribute("message", "Nauczyciel nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.findById(id).get();
        subject.getTeachers().add(teacherRepository.findById(listForm.getOption()).get());
        subjectRepository.save(subject);
        model.addAttribute("message", "Nauczyciel dodany");
        model.addAttribute("link", "/view/subjects");
        log.info("teacher added to subject {}", keyValue("subject", subject.getName()));
        return "message";
    }

    @RequestMapping(value = "/view/subject/removeteacher", method = RequestMethod.GET)
    public String removeTeacher(@ModelAttribute(value = "formData") ListForm listForm, @RequestParam long id, Model
            model) {
        if (!subjectRepository.existsById(id)) {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.findById(id).get();
        List<Teacher> list = subject.getTeachers();
        model.addAttribute("elements", list);
        log.info("teacher removed from subject {}", keyValue("subject", subject.getName()));
        return "list";
    }

    @RequestMapping(value = "/view/subject/removeteacher", method = RequestMethod.POST)
    public String removeTeacher(@RequestParam long id, ListForm listForm, Model model) {
        if (!subjectRepository.existsById(id)) {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        if (!teacherRepository.existsById(listForm.getOption())) {
            model.addAttribute("message", "Nauczyciel nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.findById(id).get();
        subject.getTeachers().remove(teacherRepository.findById(listForm.getOption()).get());
        subjectRepository.save(subject);
        model.addAttribute("message", "Nauczyciel usunięty");
        model.addAttribute("link", "/view/subjects");
        return "message";
    }

    @RequestMapping(value = "/view/subject", method = RequestMethod.GET)
    public String view(@RequestParam long id, Model model) {
        if (!subjectRepository.existsById(id)) {
            model.addAttribute("message", "Przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Subject subject = subjectRepository.findById(id).get();
        model.addAttribute("subject", subject);
        model.addAttribute("classes", classRepository.findBySubject(subject));
        return "subject/details";
    }

    @RequestMapping(value = "/view/subject/addclass", method = RequestMethod.GET)
    public String addClassForm(@RequestParam long id, @ModelAttribute(value = "formData") ListForm listForm, Model
            model) {
        if (!subjectRepository.existsById(id)) {
            model.addAttribute("error", "przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        List<Class> list = StreamSupport.stream(classRepository.findAll().spliterator(), true).collect(Collectors.toList());
        list.removeAll(classRepository.findBySubject(subjectRepository.findById(id).get()));
        model.addAttribute("elements", list);
        model.addAttribute("classes", 1);
        return "list";
    }

    @RequestMapping(value = "/view/subject/addclass", method = RequestMethod.POST)
    public String addClass(@RequestParam long id, ListForm listForm, Model model) {
        if (!subjectRepository.existsById(id)) {
            model.addAttribute("error", "przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        if (!classRepository.existsById(listForm.getOption())) {
            model.addAttribute("error", "klasa nie znaleziona");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Class clazz = classRepository.findById(listForm.getOption()).get();
        clazz.getSubject().add(subjectRepository.findById(id).get());
        classRepository.save(clazz);
        model.addAttribute("message", "Dodano klasę");
        model.addAttribute("link", "/view/subjects");
        log.info("class added from subject {}", keyValue("subject", clazz.getSubject()));
        return "message";
    }

    @RequestMapping(value = "/view/subject/removeclass", method = RequestMethod.GET)
    public String removeClassForm(@RequestParam long id, @ModelAttribute(value = "formData") ListForm listForm, Model
            model) {
        if (!subjectRepository.existsById(id)) {
            model.addAttribute("error", "przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        List<Class> list = classRepository.findBySubject(subjectRepository.findById(id).get());
        model.addAttribute("elements", list);
        model.addAttribute("classes", 1);
        return "list";
    }

    @RequestMapping(value = "/view/subject/removeclass", method = RequestMethod.POST)
    public String removeClass(@RequestParam long id, ListForm listForm, Model model) {
        if (!subjectRepository.existsById(id)) {
            model.addAttribute("error", "przedmiot nie znaleziony");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        if (!classRepository.existsById(listForm.getOption())) {
            model.addAttribute("error", "klasa nie znaleziona");
            model.addAttribute("link", "/view/subjects");
            return "message";
        }
        Class clazz = classRepository.findById(listForm.getOption()).get();
        clazz.getSubject().remove(subjectRepository.findById(id).get());
        classRepository.save(clazz);
        model.addAttribute("message", "Usunięto klasę");
        model.addAttribute("link", "/view/subjects");
        log.info("class removed from subject {}", keyValue("subject", clazz.getSubject()));
        return "message";
    }
}
