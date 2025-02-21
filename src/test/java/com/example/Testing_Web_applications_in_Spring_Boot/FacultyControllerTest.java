package com.example.Testing_Web_applications_in_Spring_Boot;

import com.example.Testing_Web_applications_in_Spring_Boot.controller.FacultyController;
import com.example.Testing_Web_applications_in_Spring_Boot.controller.StudentController;
import com.example.Testing_Web_applications_in_Spring_Boot.model.Faculty;
import com.example.Testing_Web_applications_in_Spring_Boot.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetFaculty() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/Faculty", String.class))
                .isNotEmpty();
    }

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testPostBooks() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Bloody");
        faculty.setColor("red");
        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/Faculty", faculty, String.class))
                .isNotEmpty();
    }

}
