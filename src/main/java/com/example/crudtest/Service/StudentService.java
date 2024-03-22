package com.example.crudtest.Service;

import com.example.crudtest.Entity.Student;
import com.example.crudtest.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student){
        Student savedStudent = studentRepository.save(student);
        return savedStudent;
    }
    public List<Student> getAllStudent(){
        List<Student> students = studentRepository.findAll();
        return students;
    }
    public Optional<Student> getStudent(Long id){
        Optional<Student> studentOptional= studentRepository.findById(id);
        return studentOptional;
    }
    public Optional<Student> updateStudente(Long id,Student student){
        Optional<Student> studenteUpdate = studentRepository.findById(id);
        if (studenteUpdate.isPresent()){
            studenteUpdate.get().setName(student.getName());
            studenteUpdate.get().setSurname(student.getSurname());
            studentRepository.save(studenteUpdate.get());
        }else{
            return Optional.empty();
        }
        return studenteUpdate;
    }
    public Student deleteStudent(Long id){
        studentRepository.deleteById(id);
        return null;
    }
    public Optional<Student> updateIsWorking(Long id,Boolean isWorking){
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()){
            studentOptional.get().setWorking(isWorking);
            studentRepository.save(studentOptional.get());
        }
        return studentOptional;
    }
}
