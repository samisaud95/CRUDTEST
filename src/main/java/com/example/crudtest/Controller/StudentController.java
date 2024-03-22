package com.example.crudtest.Controller;

import com.example.crudtest.Entity.Student;
import com.example.crudtest.Repository.StudentRepository;
import com.example.crudtest.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
   @PostMapping("/addstudente")
    public ResponseEntity<Student> addstudente(@RequestBody Student student){
       studentService.createStudent(student);
       return ResponseEntity.ok().body(student);
   }
   @GetMapping("/liststudente")
    public ResponseEntity<List<Student>> listStudente(){
       List<Student> allStudente = studentService.getAllStudent();
       return ResponseEntity.ok().body(allStudente);
   }
   @GetMapping("/getstudent/{id}")
    public ResponseEntity<Optional<Student>> getStudent(@PathVariable Long id){
       Optional<Student> studentOptional = studentService.getStudent(id);
       return ResponseEntity.ok().body(studentOptional);
   }
    @PutMapping("/updatestudent/{id}")
    public ResponseEntity<Student> updateStudentById(@RequestBody Student student,@PathVariable Long id){
        Optional<Student> studentOptional = studentService.updateStudente(id,student);
        if(studentOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(student);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Student> deleteStudent(@RequestParam Long id){
        Student studentDaCancellare = studentService.deleteStudent(id);
        return ResponseEntity.ok().body(studentDaCancellare);
    }

    @PatchMapping("/isworking/{id}")
    public ResponseEntity<Optional<Student>> updateIsWorking(@PathVariable Long id, @RequestParam boolean working) {
        Optional<Student> studentDaModificare = studentService.updateIsWorking(id,working);
        return ResponseEntity.ok().body(studentDaModificare);
    }
}
