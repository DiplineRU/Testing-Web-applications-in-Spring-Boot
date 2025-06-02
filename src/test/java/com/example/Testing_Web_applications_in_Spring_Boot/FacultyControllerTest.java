package com.example.Testing_Web_applications_in_Spring_Boot;

import com.example.Testing_Web_applications_in_Spring_Boot.controller.FacultyController;
import com.example.Testing_Web_applications_in_Spring_Boot.model.Faculty;
import com.example.Testing_Web_applications_in_Spring_Boot.repository.FacultyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FacultyRepository facultyRepository;

    // Вспомогательный метод для формирования базового URL
    private String getBaseUrl() {
        return "http://localhost:" + port + "/Faculty";
    }

    @Test
    public void testGetFacultyByIdSuccess() {
        // Сначала создаем факультет
        Faculty faculty = new Faculty();
        faculty.setName("Test Faculty");
        faculty.setColor("Blue");
        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity(getBaseUrl(), faculty, Faculty.class);
        Faculty createdFaculty = createResponse.getBody();

        // Теперь получаем его по ID
        ResponseEntity<Faculty> response = restTemplate.getForEntity(getBaseUrl() + "/" + createdFaculty.getId(), Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdFaculty.getName(), response.getBody().getName());
    }

    @Test
    public void testEditFacultySuccess() {
        // Создаем факультет
        Faculty faculty = new Faculty();
        faculty.setName("Old Name");
        faculty.setColor("Red");
        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity(getBaseUrl(), faculty, Faculty.class);
        Faculty createdFaculty = createResponse.getBody();

        // Обновляем факультет
        createdFaculty.setName("Updated Name");
        HttpEntity<Faculty> request = new HttpEntity<>(createdFaculty);
        ResponseEntity<Faculty> response = restTemplate.exchange(getBaseUrl(), HttpMethod.PUT, request, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Name", response.getBody().getName());
    }

    @Test
    public void testEditFacultyBadRequest() {
        Faculty faculty = new Faculty();
        faculty.setId(9999L); // Несуществующий ID
        HttpEntity<Faculty> request = new HttpEntity<>(faculty);
        ResponseEntity<Faculty> response = restTemplate.exchange(
                getBaseUrl(),
                HttpMethod.PUT,
                request,
                Faculty.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Ожидаем 404
    }

    @Test
    public void testDeleteFaculty() {
        // 1. Создаем факультет
        Faculty faculty = new Faculty();
        faculty.setName("To Delete");
        faculty.setColor("Yellow");
        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity(
                getBaseUrl(),
                faculty,
                Faculty.class
        );
        Faculty createdFaculty = createResponse.getBody();

        // 2. Удаляем
        restTemplate.delete(getBaseUrl() + "/" + createdFaculty.getId());

        // 3. Проверяем, что факультета больше нет
        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                getBaseUrl() + "/" + createdFaculty.getId(),
                Faculty.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteFacultyNotFound() {
        ResponseEntity<Void> response = restTemplate.exchange(
                getBaseUrl() + "/9999",
                HttpMethod.DELETE,
                null,
                Void.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchFacultiesByName() {
        Faculty faculty = new Faculty();
        faculty.setName("Search Test");
        faculty.setColor("Purple");
        restTemplate.postForEntity(getBaseUrl(), faculty, Faculty.class);

        ResponseEntity<Collection> response = restTemplate.getForEntity(getBaseUrl() + "/searchNameOrColor?name=Search Test", Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetFacultyStudentsSuccess() {
        Faculty faculty = new Faculty();
        faculty.setName("Faculty with Students");
        faculty.setColor("Orange");
        ResponseEntity<Faculty> facultyResponse = restTemplate.postForEntity(getBaseUrl(), faculty, Faculty.class);
        Faculty createdFaculty = facultyResponse.getBody();

        ResponseEntity<Collection> response = restTemplate.getForEntity(getBaseUrl() + "/" + createdFaculty.getId() + "/students", Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

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