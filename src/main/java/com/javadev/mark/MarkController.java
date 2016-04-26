package com.javadev.mark;

import com.javadev.student.Student;
import com.javadev.student.StudentRepository;
import com.javadev.subject.Subject;
import com.javadev.subject.SubjectRepository;
import com.javadev.teacher.Teacher;
import com.javadev.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuba3 on 25.04.2016.
 */
@RestController
public class MarkController {

    @Autowired
    MarkRepository markRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @RequestMapping("/mark/add")
    public String add(@RequestBody @Valid MarkDTO markDTO)
    {
        System.out.println(markDTO.getStudentId()+" "+markDTO.getSubjectId()+" "+markDTO.getTeacherId());
        markRepository.save(markDTO.mapToEntity(studentRepository.getOne(markDTO.getStudentId()),teacherRepository.getOne(markDTO.getTeacherId()),subjectRepository.getOne(markDTO.getSubjectId())));
        return "added";
    }
    @RequestMapping(value = "/mark", method = RequestMethod.GET)
    public List<Mark> show()
    {
        return markRepository.findAll();
    }
    @RequestMapping(value="/mark/delete/{id}",method= RequestMethod.DELETE)
    public String delete(@PathVariable long id)
    {
        if(markRepository.exists(id)){
            markRepository.delete(id);
            return "deleted";
        }
        else
            return "mark not found";
    }
    @RequestMapping(value="/mark/find",method = RequestMethod.POST)
    public List<Mark> find(@RequestBody MarkDTO markDTO)
    {
        List<Mark> list = markRepository.findAll();
        if(markDTO.getTeacherId() != 0)
        {
            Teacher teacher = teacherRepository.getOne(markDTO.getTeacherId());
            for(int i = 0;i<list.size();i++)
            {
                if(!teacher.equals(list.get(i).getTeacher()))
                    list.remove(i--);
            }
        }
        if(markDTO.getStudentId() != 0)
        {
            Student student = studentRepository.getOne(markDTO.getStudentId());
            for(int i = 0;i<list.size();i++)
            {
                if(!student.equals(list.get(i).getStudent()))
                    list.remove(i--);
            }
        }
        if(markDTO.getSubjectId() != 0)
        {
            Subject subject = subjectRepository.getOne(markDTO.getSubjectId());
            for(int i = 0;i<list.size();i++)
            {
                if(!subject.equals(list.get(i).getSubject()))
                    list.remove(i--);
            }
        }
        return list;
    }
    @RequestMapping(value="/mark/findsimple",method = RequestMethod.POST)
    public List<Integer> findSimple(@RequestBody MarkDTO markDTO)
    {
        List<Mark> list = find(markDTO);
        ArrayList<Integer> marks = new ArrayList<>();
        for(int i = 0;i<list.size();i++)
        {
            marks.add(list.get(i).getMark());
        }
        return marks;
    }
}