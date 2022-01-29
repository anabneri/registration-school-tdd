package com.anabneri.registrationschool;

import com.anabneri.registrationschool.model.StudentDTO;
import com.anabneri.registrationschool.model.entity.Student;
import com.anabneri.registrationschool.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    static String STUDENT_API = "/api/student";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @Test
    @DisplayName("Should create a student registration with success.")
    public void createStudentTest() throws Exception {

        StudentDTO studentDTOBuilder = StudentDTO.builder().studentName("Ana Neri").dateOfRegistration("10/10/2021").build();
        Student savedStudent = Student.builder().studentId(101).studentName("Ana Neri").dateOfRegistration("10/10/2021").build();

        BDDMockito.given(studentService.save(Mockito.any(Student.class))).willReturn(savedStudent);


        String json  = new ObjectMapper().writeValueAsString(studentDTOBuilder);

        MockHttpServletRequestBuilder request  = MockMvcRequestBuilders
                .post(STUDENT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);


        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("studentId").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("studentName").value(studentDTOBuilder.getStudentName()))
                .andExpect(MockMvcResultMatchers.jsonPath("dateOfRegistration").value(studentDTOBuilder.getDateOfRegistration()));

    }


    @Test
    @DisplayName("Should throw an exception when not have date enough for the test.")
    public void createInvalidStudentTest() {

    }
}