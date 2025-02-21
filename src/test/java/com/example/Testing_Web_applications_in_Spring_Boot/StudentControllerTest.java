package com.example.Testing_Web_applications_in_Spring_Boot;

import com.example.Testing_Web_applications_in_Spring_Boot.controller.StudentController;
import com.example.Testing_Web_applications_in_Spring_Boot.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetStudents() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/Student", String.class))
                .isNotEmpty();
    }

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setName("Bob");
        student.setAge(88);
        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/Student", student, String.class))
                .isNotEmpty();
    }
}
