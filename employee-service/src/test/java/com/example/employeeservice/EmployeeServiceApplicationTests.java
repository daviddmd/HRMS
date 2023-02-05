package com.example.employeeservice;

import com.example.employeeservice.Models.Employee.EmployeeCreateDTO;
import com.example.employeeservice.Models.Employee.EmployeeDTO;
import com.example.employeeservice.Models.Employee.EmployeeSearchDTO;
import com.example.employeeservice.Models.Employee.EmployeeUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.example.employeeservice.Helper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = EmployeeServiceApplication.class)
@ContextConfiguration(classes = EmployeeServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-testing.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EmployeeServiceApplicationTests {
    ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    @BeforeAll
    void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void createEmployee() throws Exception {
        String name = "Employee 1";
        String email = "employee1@example.com";
        String phoneNumber = "910000000";
        String identifier = "E1";
        Long managerId = null;
        EmployeeCreateDTO dto = EmployeeCreateDTO.builder().
                name(name).
                email(email).
                phoneNumber(phoneNumber).
                identifier(identifier).
                managerId(managerId).
                build();
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andReturn();
        EmployeeDTO employeeDTO = fromJson(mapper, result, EmployeeDTO.class);
        assertEquals(name, employeeDTO.getName());
        assertEquals(email, employeeDTO.getEmail());
        assertEquals(phoneNumber, employeeDTO.getPhoneNumber());
        assertEquals(identifier, employeeDTO.getIdentifier());
        assertEquals(1, employeeDTO.getId());
        assertNull(employeeDTO.getManager());
        name = "Employee 2";
        email = "employee2@example.com";
        phoneNumber = "920000000";
        identifier = "E2";
        managerId = 1L;
        dto = EmployeeCreateDTO.builder().
                name(name).
                email(email).
                phoneNumber(phoneNumber).
                identifier(identifier).
                managerId(managerId).
                build();
        result = mvc.perform(MockMvcRequestBuilders.post("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andReturn();
        employeeDTO = fromJson(mapper, result, EmployeeDTO.class);
        assertEquals(name, employeeDTO.getName());
        assertEquals(email, employeeDTO.getEmail());
        assertEquals(phoneNumber, employeeDTO.getPhoneNumber());
        assertEquals(identifier, employeeDTO.getIdentifier());
        assertEquals(2, employeeDTO.getId());
        assertNotNull(employeeDTO.getManager());
        assertEquals(1, employeeDTO.getManager().getId());
        //Invalid Manager ID, asset that creation fails
        name = "Employee 3";
        email = "employee3@example.com";
        phoneNumber = "930000000";
        identifier = "E3";
        managerId = 80L;
        dto = EmployeeCreateDTO.builder().
                name(name).
                email(email).
                phoneNumber(phoneNumber).
                identifier(identifier).
                managerId(managerId).
                build();
        mvc.perform(MockMvcRequestBuilders.post("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
        //Assert that it fails with existing identifier
        identifier = "E2";
        dto = EmployeeCreateDTO.builder().
                name(name).
                email(email).
                phoneNumber(phoneNumber).
                identifier(identifier).
                build();
        mvc.perform(MockMvcRequestBuilders.post("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest());
        //Assert that it fails with existing email
        identifier = "E3";
        email = "employee2@example.com";
        dto = EmployeeCreateDTO.builder().
                name(name).
                email(email).
                phoneNumber(phoneNumber).
                identifier(identifier).
                build();
        mvc.perform(MockMvcRequestBuilders.post("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest());
        //Create employee
        email = "employee3@example.com";
        managerId = 2L;
        dto = EmployeeCreateDTO.builder().
                name(name).
                email(email).
                phoneNumber(phoneNumber).
                identifier(identifier).
                managerId(managerId).
                build();
        result = mvc.perform(MockMvcRequestBuilders.post("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        employeeDTO = fromJson(mapper, result, EmployeeDTO.class);
        assertEquals(name, employeeDTO.getName());
        assertEquals(email, employeeDTO.getEmail());
        assertEquals(phoneNumber, employeeDTO.getPhoneNumber());
        assertEquals(identifier, employeeDTO.getIdentifier());
        assertEquals(3, employeeDTO.getId());
        assertNotNull(employeeDTO.getManager());
        assertEquals(2, employeeDTO.getManager().getId());
    }

    @Test
    @Order(2)
    void listEmployees() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/employees").
                        content(toJson(mapper, EmployeeSearchDTO.builder().build())).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        List<EmployeeDTO> employeeDTOList = listFromJson(mapper, result, EmployeeDTO[].class);
        assertEquals(3, employeeDTOList.size());
    }

    @Test
    @Order(3)
    void searchEmployees() throws Exception {
        EmployeeSearchDTO dto = EmployeeSearchDTO.builder().managerId(1L).build();
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        List<EmployeeDTO> employeeDTOList = listFromJson(mapper, result, EmployeeDTO[].class);
        assertEquals(1, employeeDTOList.size());
        assertEquals("E2", employeeDTOList.get(0).getIdentifier());
        dto = EmployeeSearchDTO.builder().managerId(2L).build();
        result = mvc.perform(MockMvcRequestBuilders.get("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        employeeDTOList = listFromJson(mapper, result, EmployeeDTO[].class);
        assertEquals(1, employeeDTOList.size());
        assertEquals("E3", employeeDTOList.get(0).getIdentifier());
        dto = EmployeeSearchDTO.builder().managerId(3L).build();
        result = mvc.perform(MockMvcRequestBuilders.get("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        employeeDTOList = listFromJson(mapper, result, EmployeeDTO[].class);
        assertEquals(0, employeeDTOList.size());
        dto = EmployeeSearchDTO.builder().email("employee1@example.com").build();
        result = mvc.perform(MockMvcRequestBuilders.get("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        employeeDTOList = listFromJson(mapper, result, EmployeeDTO[].class);
        assertEquals(1, employeeDTOList.size());
        assertEquals("E1", employeeDTOList.get(0).getIdentifier());
        dto = EmployeeSearchDTO.builder().name("Employee").build();
        result = mvc.perform(MockMvcRequestBuilders.get("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        employeeDTOList = listFromJson(mapper, result, EmployeeDTO[].class);
        assertEquals(3, employeeDTOList.size());
        dto = EmployeeSearchDTO.builder().phoneNumber("910 000 000").build();
        result = mvc.perform(MockMvcRequestBuilders.get("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        employeeDTOList = listFromJson(mapper, result, EmployeeDTO[].class);
        assertEquals(1, employeeDTOList.size());
        assertEquals("E1", employeeDTOList.get(0).getIdentifier());

        dto = EmployeeSearchDTO.builder().identifier("E1").build();
        result = mvc.perform(MockMvcRequestBuilders.get("/employees").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        employeeDTOList = listFromJson(mapper, result, EmployeeDTO[].class);
        assertEquals(1, employeeDTOList.size());
        assertEquals("E1", employeeDTOList.get(0).getIdentifier());
    }

    @Test
    @Order(4)
    void updateEmployee() throws Exception {
        String newName = "New Employee 1";
        String newIdentifier = "NE1";
        String newEmail = "new.employee.1@example.com";
        String newPhoneNumber = "911000000";
        Long newManagerId = null;
        EmployeeUpdateDTO dto = EmployeeUpdateDTO.builder().
                name(newName).
                email(newEmail).
                phoneNumber(newPhoneNumber).
                identifier(newIdentifier).
                managerId(newManagerId).
                build();
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/employees/1").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andReturn();
        EmployeeDTO employeeDTO = fromJson(mapper, result, EmployeeDTO.class);
        assertEquals(newName, employeeDTO.getName());
        assertEquals(newEmail, employeeDTO.getEmail());
        assertEquals(newPhoneNumber, employeeDTO.getPhoneNumber());
        assertEquals(newIdentifier, employeeDTO.getIdentifier());
        assertEquals(1, employeeDTO.getId());
        result = mvc.perform(MockMvcRequestBuilders.get("/employees/1").
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andReturn();
        employeeDTO = fromJson(mapper, result, EmployeeDTO.class);
        assertEquals(newName, employeeDTO.getName());
        assertEquals(newEmail, employeeDTO.getEmail());
        assertEquals(newPhoneNumber, employeeDTO.getPhoneNumber());
        assertEquals(newIdentifier, employeeDTO.getIdentifier());
        assertEquals(1, employeeDTO.getId());
        //Assert that request fails if using invalid manager ID
        newManagerId = 9L;
        dto = EmployeeUpdateDTO.builder().
                name(newName).
                email(newEmail).
                phoneNumber(newPhoneNumber).
                identifier(newIdentifier).
                managerId(newManagerId).
                build();
        mvc.perform(MockMvcRequestBuilders.put("/employees/1").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
        //Asset that request fails if using the same ID as the user to update for the manager ID
        newManagerId = 1L;
        dto = EmployeeUpdateDTO.builder().
                name(newName).
                email(newEmail).
                phoneNumber(newPhoneNumber).
                identifier(newIdentifier).
                managerId(newManagerId).
                build();
        mvc.perform(MockMvcRequestBuilders.put("/employees/1").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest());
        //Asset that the request fails if using an existing email different from the current user
        newManagerId = null;
        newEmail = "employee2@example.com";
        dto = EmployeeUpdateDTO.builder().
                name(newName).
                email(newEmail).
                phoneNumber(newPhoneNumber).
                identifier(newIdentifier).
                managerId(newManagerId).
                build();
        mvc.perform(MockMvcRequestBuilders.put("/employees/1").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest());
        //Assert that the request fails if using an existing identifier different from the current user
        newEmail = "employee1@example.com";
        newIdentifier = "E2";
        dto = EmployeeUpdateDTO.builder().
                name(newName).
                email(newEmail).
                phoneNumber(newPhoneNumber).
                identifier(newIdentifier).
                managerId(newManagerId).
                build();
        mvc.perform(MockMvcRequestBuilders.put("/employees/1").
                        content(toJson(mapper, dto)).
                        contentType(MediaType.APPLICATION_JSON).
                        characterEncoding(StandardCharsets.UTF_8).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void deleteEmployee() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/employees/1").
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/employees/2").
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        EmployeeDTO dto = fromJson(mapper, result, EmployeeDTO.class);
        assertNotNull(dto.getManager());
        assertEquals(1, dto.getManager().getId());
        mvc.perform(MockMvcRequestBuilders.delete("/employees/1")).andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/employees/1")).andExpect(status().isNotFound());
        result = mvc.perform(MockMvcRequestBuilders.get("/employees/2").
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        dto = fromJson(mapper, result, EmployeeDTO.class);
        assertNull(dto.getManager());

    }

}
