package com.waracle.cakemgr.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.model.CakeDTO;
import com.waracle.cakemgr.service.CakeService;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {
		CakeRestController.class,
		CakeHtmlController.class
})
@Log4j2
public class CakeHtmlControllerTest {

	@Autowired
	private MockMvc						mockMvc;

	@Autowired
	ObjectMapper							objectMapper;

	@MockBean
	private CakeService	cakeService;

	private List<CakeDTO>	testReadings;

	@Before
	public void setupTestData() {

		// Note the sort of this collection at the bottom is just to ensure its in ID
		// order
		testReadings = Arrays.asList(
				CakeDTO.builder().id(1).title("CakeTitle1").imageUrl("url1").description("CakeDescription1").build(),
				CakeDTO.builder().id(2).title("CakeTitle2").imageUrl("url2").description("CakeDescription2").build(),
				CakeDTO.builder().id(3).title("CakeTitle3").imageUrl("url3").description("CakeDescription3").build())
			.stream()
			.sorted(Comparator.comparing(CakeDTO::getId))
			.collect(Collectors.toList());

	}

	@Test
	public void testGetAllCakes() throws Exception {

		Mockito.when(cakeService.fetchAll()).thenReturn(testReadings);

		RequestBuilder rb = MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML);
		MvcResult r = mockMvc.perform(rb).andExpect(status().isOk()).andReturn();

		String responseHtml = r.getResponse().getContentAsString();

		Assert.assertNotNull(responseHtml); // How to test HTML resp. ? :-/
		
	}

}
