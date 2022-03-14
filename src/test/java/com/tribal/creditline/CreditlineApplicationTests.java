package com.tribal.creditline;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tribal.creditline.model.CreditLineRequest;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;


@SpringBootTest(properties = {"requests.allowed.any.withinmillis=0", "requests.allowed.rejected.withinmillis=0"})
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditlineApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	@Order(1)
	public void rejectedCreditLine_Try1() throws Exception {
		CreditLineRequest cl = new CreditLineRequest(null,"SME",435.30,4235.45,1000.00,new Date());

		this.mockMvc.perform(post("/creditline").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cl))
						.header("Authorization" , "Basic amF2aWVyOjEyMw=="))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accepted",is(false)));
	}

	@Test
	@Order(2)
	public void rejectedCreditLine_Try2() throws Exception {
		CreditLineRequest cl = new CreditLineRequest(null,"SME",435.30,4235.45,1000.00,new Date());

		this.mockMvc.perform(post("/creditline").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cl))
						.header("Authorization" , "Basic amF2aWVyOjEyMw=="))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accepted",is(false)));
	}

	@Test
	@Order(3)
	public void rejectedCreditLine_Try3() throws Exception {
		CreditLineRequest cl = new CreditLineRequest(null,"SME",435.30,4235.45,1000.00,new Date());

		this.mockMvc.perform(post("/creditline").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cl))
						.header("Authorization" , "Basic amF2aWVyOjEyMw=="))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("A sales agent will contact you")));
	}

	@Test
	@Order(4)
	public void acceptedSMECreditLine_Try1() throws Exception {
		CreditLineRequest cl = new CreditLineRequest(null,"SME",435.30,4235.45,847.00,new Date());

		this.mockMvc.perform(post("/creditline").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cl))
						.header("Authorization" , "Basic bG91cmRlczoxMjM="))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accepted",is(true)))
				.andExpect(jsonPath("$.acceptedCreditLine",is(847.00)));
	}

	@Test
	@Order(5)
	public void acceptedStartupCreditLine_Try1_CaseMonthlyRevenue() throws Exception {
		CreditLineRequest cl = new CreditLineRequest(null,"Startup",435.30,4235.45,847.00,new Date());

		this.mockMvc.perform(post("/creditline").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cl))
						.header("Authorization" , "Basic aXNpZHJvOjEyMw=="))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accepted",is(true)))
				.andExpect(jsonPath("$.acceptedCreditLine",is(847.00)));
	}

	@Test
	@Order(6)
	public void acceptedStartupCreditLine_Try1_CaseCashBalance() throws Exception {
		CreditLineRequest cl = new CreditLineRequest(null,"Startup",5000.00,4235.45,1600.00,new Date());

		this.mockMvc.perform(post("/creditline").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cl))
						.header("Authorization" , "Basic aHVtYmVydG86MTIz"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accepted",is(true)))
				.andExpect(jsonPath("$.acceptedCreditLine",is(1600.00)));
	}

	@Test
	@Order(7)
	public void acceptedStartupCreditLine_Try2_RequestedCreditChanged() throws Exception {
		CreditLineRequest cl = new CreditLineRequest(null,"Startup",5000.00,4235.45,2600.00,new Date());

		this.mockMvc.perform(post("/creditline").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cl))
						.header("Authorization" , "Basic aHVtYmVydG86MTIz"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accepted",is(true)))
				.andExpect(jsonPath("$.acceptedCreditLine",is(1600.00)));
	}

}