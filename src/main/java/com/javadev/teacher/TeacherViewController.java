package com.javadev.teacher;

import com.javadev.subject.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

/**
 * Created by kuba3 on 10.05.2016.
 */

@Controller
public class TeacherViewController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping(value = "/view/teachers", method = RequestMethod.GET)
    public String teachersListView(Model model) {
        model.addAttribute("list", teacherRepository.findAll());
        return "teacher/list";
    }

    @RequestMapping(value = "/view/teacher", method = RequestMethod.GET)
    public String teacherDetails(@RequestParam long id, Model model) {
        if (teacherRepository.existsById(id)) {
            Teacher teacher = teacherRepository.findById(id).get();
            model.addAttribute("teacher", teacher);
            model.addAttribute("subjectlist", subjectRepository.findByTeachers(teacher));
            return "teacher/details";
        }
        model.addAttribute("error", "Nauczyciel nie znaleziony");
        return "teacher/details";
    }

    @RequestMapping(value = "/view/teacher/delete", method = RequestMethod.GET)
    public String delete(@RequestParam long id, Model model) {
        if (teacherRepository.existsById(id)) {
            try {
                teacherRepository.deleteById(id);
            } catch (Exception e) {
                model.addAttribute("message","bąd bazy danych");
                model.addAttribute("link", "/view/classes");
                return "message";
            }
            model.addAttribute("message", "Nauczyciel usunięty");
            model.addAttribute("link", "/view/teachers");
            return "message";
        }
        model.addAttribute("message", "Nauczyciel nie znaleziony");
        model.addAttribute("link", "/view/teachers");
        return "message";
    }

    @RequestMapping(value = "/view/teacher/add", method = RequestMethod.GET)
    public String addForm(@ModelAttribute(value = "formData") TeacherDTO teacherDTO, Model model) {
        model.addAttribute("actionaddress", "/view/teacher/addteacher");
        return "teacher/addForm";
    }

    @RequestMapping(value = "/view/teacher/addteacher", method = RequestMethod.POST)
    public String addTeacher(TeacherFormDTO teacherFormDTO, Model model) {
        try {
            teacherRepository.save(teacherFormDTO.mapToDTO().mapToEntity());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "Nauczyciel dodany");
        model.addAttribute("link", "/view/teachers");
        return "message";
    }

    @RequestMapping(value = "/view/teacher/updateteacher", method = RequestMethod.GET)
    public String update(@RequestParam long id, Model model) {
        if (!teacherRepository.existsById(id)) {
            model.addAttribute("message", "nauczyciel nie znaleziony");
            model.addAttribute("link", "/view/teachers");
            return "message";
        }
        model.addAttribute("formData", TeacherDTO.getDTO(teacherRepository.findById(id).get()).mapToFormDTO());
        return "teacher/addForm";
    }

    @RequestMapping(value = "/view/teacher/updateteacher", method = RequestMethod.POST)
    public String update(TeacherFormDTO teacherFormDTO, @RequestParam long id, Model model) {
        if (!teacherRepository.existsById(id)) {
            model.addAttribute("message", "Nauczyciel nie znaleziony");
            model.addAttribute("link", "/view/teachers");
            return "message";
        }
        Teacher teacher = null;
        try {
            teacher = teacherFormDTO.mapToDTO().mapToEntity();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        teacher.setId(id);
        teacherRepository.save(teacher);
        model.addAttribute("message", "nauczyciel zmieniony");
        model.addAttribute("link", "/view/teachers");
        return "message";
    }
}
