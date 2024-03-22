package com.example.crudtest;
import com.example.crudtest.Controller.StudentController;
import com.example.crudtest.Entity.Student;
import com.example.crudtest.Service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
  private StudentController studentController;
    @Autowired
    private StudentService studentService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
    }
    @Test
   void createStudent() throws Exception{
      Student student = new Student();
      student.setId(1L);
      student.setName("Mario");
      student.setSurname("Lopez");
      student.setWorking(true);

      String studentJSON = objectMapper.writeValueAsString(student);

    MvcResult resultActions = this.mockMvc.perform(post("/student/addstudente")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(studentJSON)).andDo(print())
            .andExpect(status().isOk()).andReturn();
    }
    @Test
    void getAllStudents() throws Exception {
        createStudent();
        MvcResult result = this.mockMvc.perform(get("/student/liststudente"))
                .andDo(print()).andReturn();

        List<Student> userFromResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(userFromResponseList.size()).isNotZero();

    }
    @Test
    void getStudent() throws Exception {
        Long studentId = 1L;
        createStudent();

        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/getstudent/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(studentId)).andReturn();
    }
    @Test
    void updateStudentById() throws Exception {
        Long studentId = 1L;
        createStudent();
        Student updatedStudent = new Student(studentId, "Mario", "Lopez", false);
        String studentJSON = objectMapper.writeValueAsString(updatedStudent);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/student/updatestudent/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON).content(studentJSON))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }
    @Test
    void updateWorking() throws Exception {
        Long studentId = 1L;
        boolean isWorking = false;

        mockMvc.perform(MockMvcRequestBuilders.patch("/student/isworking/{id}", studentId)
                        .param("working", String.valueOf(isWorking)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    void deleteStudente() throws Exception {
        createStudent();

        MvcResult result = mockMvc.perform(delete("/student/delete")
                        .param("id", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


    }







}
