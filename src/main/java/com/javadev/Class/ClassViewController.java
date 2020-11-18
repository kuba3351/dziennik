package com.javadev.Class;

import com.javadev.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by kuba3 on 06.06.2016.
 */
@Controller
public class ClassViewController {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    StudentRepository studentRepository;

    @RequestMapping(value = "/view/classes", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("list", classRepository.findAll());
        return "class/list";
    }

    @RequestMapping(value = "/view/class/add", method = RequestMethod.GET)
    public String add(@ModelAttribute(value = "formData") ClassDTO classDTO, Model model) {
        return "/class/addForm";
    }

    @RequestMapping(value = "/view/class/add", method = RequestMethod.POST)
    public String addClass(ClassDTO classDTO, Model model) {
        Class clazz = new Class();
        clazz.setName(classDTO.getName());
        clazz.setYear(classDTO.getYear());
        classRepository.save(clazz);
        model.addAttribute("message", "Klasa dodana");
        model.addAttribute("link", "/view/classes");
        return "message";
    }

    @RequestMapping(value = "/view/class/delete", method = RequestMethod.GET)
    public String delete(@RequestParam Long id, Model model) {
        if (!classRepository.existsById(id)) {
            model.addAttribute("message", "Klasa nie znaleziona");
            model.addAttribute("link", "/view/classes");
            return "message";
        }
        try {
            classRepository.deleteById(id);
            model.addAttribute("message", "Klasa usunięta");
            model.addAttribute("link", "/view/classes");
        } catch (Exception e) {
            model.addAttribute("message","bąd bazy danych");
            model.addAttribute("link", "/view/classes");
            return "message";
        }
        return "message";
    }

    @RequestMapping(value = "/view/class", method = RequestMethod.GET)
    public String view(@RequestParam Long id, Model model) {
        if (!classRepository.existsById(id)) {
            model.addAttribute("message", "Klasa nie znaleziona");
            model.addAttribute("link", "/view/classes");
            return "message";
        }
        classRepository.findById(id).ifPresent(clazz -> {
            model.addAttribute("clazz", clazz);
            model.addAttribute("students", studentRepository.findByClazz(clazz));
        });
        return "class/details";
    }

    @RequestMapping(value = "/view/class/update", method = RequestMethod.GET)
    public String update(@RequestParam Long id, Model model) {
        if (!classRepository.existsById(id)) {
            model.addAttribute("message", "Klasa nie znaleziona");
            model.addAttribute("link", "/view/classes");
            return "message";
        }
        model.addAttribute("formData", classRepository.findById(id).get());
        return "/class/addForm";
    }

    @RequestMapping(value = "/view/class/update", method = RequestMethod.POST)
    public String updateClass(ClassDTO classDTO, @RequestParam Long id, Model model) {
        com.javadev.Class.Class clazz = new Class();
        clazz.setName(classDTO.getName());
        clazz.setId(id);
        clazz.setYear(classDTO.getYear());
        classRepository.save(clazz);
        model.addAttribute("message", "Zmieniono klasę");
        model.addAttribute("link", "/view/classes");
        return "message";
    }
}
