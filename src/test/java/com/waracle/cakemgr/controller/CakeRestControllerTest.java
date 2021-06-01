package com.waracle.cakemgr.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
public class CakeRestControllerTest {

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

		String expectedJson = convertToJson(testReadings);

		Mockito.when(cakeService.fetchAll()).thenReturn(testReadings);

		RequestBuilder rb = MockMvcRequestBuilders.get("/cakes").accept(MediaType.APPLICATION_JSON);
		MvcResult r = mockMvc.perform(rb).andExpect(status().isOk()).andReturn();

		String responseJson = r.getResponse().getContentAsString();

		JSONAssert.assertEquals(expectedJson, responseJson, true);

	}

	@Test
	public void testAddNewCake() throws Throwable {

		CakeDTO cakeToAdd = CakeDTO.builder().id(null).title("NewCake").imageUrl("NewCakeURL").description("NewCakeDescr").build();
		
		////////////////////////////////////////////////////
		// We expect this as the result of the call to REST
		CakeDTO cakeAdded = CakeDTO.builder().id(999).title("NewCake").imageUrl("NewCakeURL").description("NewCakeDescr").build();
		String cakeAddedJson = objectMapper.writeValueAsString(cakeAdded);
		////////////////////////////////////////////////////
		
		Mockito.when(cakeService.addCake(Mockito.any(CakeDTO.class))).thenReturn(cakeAdded);

		String requestBodyJson = objectMapper.writeValueAsString(cakeToAdd);
		RequestBuilder rb = MockMvcRequestBuilders.post("/cakes").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult r = mockMvc.perform(rb).andExpect(status().isOk()).andReturn();

		String responseJson = r.getResponse().getContentAsString();

		JSONAssert.assertEquals(cakeAddedJson, responseJson, true);

	}

	private String convertToJson(List<CakeDTO> dtoList) {
		StringBuilder sb = new StringBuilder("[");
		sb.append(dtoList.stream().map(r -> {
			try {
				return objectMapper.writeValueAsString(r);
			} catch (JsonProcessingException e1) {
				log.error(e1);
				return "{}";
			}
		}).collect(Collectors.joining(",")));
		sb.append("]");
		return sb.toString();
	}

}
