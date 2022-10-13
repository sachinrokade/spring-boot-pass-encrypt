package com.encrypt.demo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.encrypt.demo.controller.TestController;
import com.encrypt.demo.exceptions.EmpNotFoundException;
import com.encrypt.demo.pojo.Employee;
import com.encrypt.demo.services.TestServices;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * @WebMvcTest - for testing the controller layer exclusively
 * Includes @ExtendWith(SpringExtension.class) for Spring TestContext Framework into JUnit 5's Jupiter programming model.
 * @AutoConfigureWebMvc and the @AutoConfigureMockMvc are also included among other functionality.
 */
@WebMvcTest(TestController.class)
class SpringBootPassEncryptApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TestServices testServices;
	@Autowired
	private ObjectMapper mapper;


//	@Test
//	public void get_vehicleByVin_ThrowsVehicleNotFoundException() throws Exception {
//
//		// Return an empty Optional object since we didn't find the vin
//		Mockito.when(testServices.getById(101)).thenReturn(new Employee());
//
//		ResultActions resultActions = mockMvc.perform(
//						MockMvcRequestBuilders.get("/test/getById/101")
//								.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNotFound());
//
//		assertEquals(EmpNotFoundException.class, resultActions.andReturn().getResolvedException().getClass());
//		assertTrue(resultActions.andReturn().getResolvedException().getMessage()
//				.contains("Employee Not found"));
//	}

//	@Test
//	public void put_updatesAndReturnsUpdatedObjWith202() throws Exception {
//
//		Employee employeeBuilder = new Employee();
//		employeeBuilder.setId(101);
//		employeeBuilder.setName("test");
//		employeeBuilder.setDept("IT test");
//		employeeBuilder.setSalery(1010);
//
//		Mockito.when(testServices.updateEmp(employeeBuilder.getId(),employeeBuilder)).thenReturn(employeeBuilder);
//		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//				.put("/test/update/101", employeeBuilder).contentType(MediaType.APPLICATION_JSON_VALUE)
//				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
//				.content(this.mapper.writeValueAsBytes(employeeBuilder));
//
//		mockMvc.perform(builder).andExpect(status().isAccepted()).andExpect(jsonPath("$.name", is("test")))
//				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(employeeBuilder)));
//	}
	@Test
	public void post_createsNewVehicle_andReturnsObjWith201() throws Exception {
		Employee employeeBuilder = new Employee();
		employeeBuilder.setId(101);
		employeeBuilder.setName("test");
		employeeBuilder.setDept("IT test");
		employeeBuilder.setSalery(1010);

		Mockito.when(testServices.saveEmp(Mockito.any(Employee.class))).thenReturn(employeeBuilder);
		// Build post request with vehicle object payload
		MockHttpServletRequestBuilder builder =
				 MockMvcRequestBuilders.post("/test/addEmp")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
						 .accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(employeeBuilder));
		mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("test")))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(employeeBuilder)));
	}
	@Test
	public void post_submitsInvalidEmp_WithEmptyMake_Returns400() throws Exception {
		// Create new vehicle with empty 'make' field
		Employee employeeBuilder = new Employee();
		employeeBuilder.setId(101);
		employeeBuilder.setName(" ");      //bad request
		employeeBuilder.setDept("IT test");
		employeeBuilder.setSalery(1010);
		String vehicleJsonString = this.mapper.writeValueAsString(employeeBuilder);
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post("/test/addEmp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(vehicleJsonString))
				.andExpect(status().isBadRequest());

		// @Valid annotation in controller will cause exception to be thrown
		assertEquals(MethodArgumentNotValidException.class,
				resultActions.andReturn().getResolvedException().getClass());
		assertTrue(resultActions.andReturn().getResolvedException().getMessage().contains("'make' field was empty"));
	}


	@Test
	public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
		// given - precondition or setup
		List<Employee> listOfEmployees = new ArrayList<>();
		listOfEmployees.add(Employee.builder().name("ABC").dept("IT").salery(1000).id(121).build());
		listOfEmployees.add(Employee.builder().name("PQR").dept("HR").salery(1000).id(123).build());
		listOfEmployees.add(Employee.builder().name("XYZ").dept("IT").salery(1000).id(122).build());
		given(testServices.getAllEmp()).willReturn(listOfEmployees);

		// when -  action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/test/getAll"));

		// then - verify the output
		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()",
						is(listOfEmployees.size())));

	}
}
