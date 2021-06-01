package com.waracle.cakemgr.integration;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.controller.CakeController;
import com.waracle.cakemgr.model.CakeConverter;
import com.waracle.cakemgr.model.CakeDTO;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.repository.CakeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CakeRestTest {

	@Before
	public void setUp() throws Exception {
		cakeRepository.deleteAll();
		Assert.assertEquals(0, cakeRepository.count());
	}

	@After
	public void tearDown() throws Exception {
		cakeRepository.deleteAll();
		Assert.assertEquals(0, cakeRepository.count());
	}

	@Test
	public void testGetAllCakes() throws Exception {

		Assert.assertEquals(0, cakeRepository.count()); // Cross check

		/////////////////////////////////
		// Test data for Cust_01
		CakeEntity[] testEntitiesCust = {
				CakeEntity.builder().id(1111).title("title1").imageUrl("url1").description("description1").build(),
				CakeEntity.builder().id(2222).title("title2").imageUrl("url2").description("description2").build(),
				CakeEntity.builder().id(3333).title("title3").imageUrl("url3").description("description3").build(),
				CakeEntity.builder().id(4444).title("title4").imageUrl("url4").description("description4").build(),
		};
		cakeRepository.saveAll(convertArrayToIterable(testEntitiesCust));
		Assert.assertEquals(testEntitiesCust.length, cakeRepository.count()); // Sanity check
		/////////////////////////////////

		CakeDTO[] fromServer = this.restTemplate.getForObject(endpointUrl(), CakeDTO[].class);
		
		assertRestResultsAgainstDatabase(fromServer, testEntitiesCust);
		
	}

	@Test
	public void testAddNewCake() throws Exception {

		Assert.assertEquals(0, cakeRepository.count()); // Cross check

		CakeDTO cakeToAdd = CakeDTO.builder().title("newTitle").imageUrl("newUrl").description("newDescription").build();
		
		HttpEntity<CakeDTO> entity = new HttpEntity<>(cakeToAdd, new HttpHeaders());

		ResponseEntity<CakeDTO> response = restTemplate.exchange(endpointUrl(), HttpMethod.POST, entity, CakeDTO.class);

		Assert.assertEquals(response.getBody(), cakeToAdd);

		Assert.assertEquals(1, cakeRepository.count()); // Extra record
		Assert.assertEquals(cakeRepository.findAll().iterator().next(), cakeConverter.from(cakeToAdd)); // Check DB record

	}

	@Test
	public void testAddNewCakeWithExistingTitleFails() throws Exception {

		Assert.assertEquals(0, cakeRepository.count()); // Cross check

		final String DUPLICATED_TITLE = "EXISTING_TITLE";
		
		// Initialise DB with cake of known title.
		CakeEntity cakeInDb = CakeEntity.builder().id(1111).title(DUPLICATED_TITLE).imageUrl("url1").description("description1").build();
		cakeRepository.save(cakeInDb);
		
		// Try to add cake with same title
		CakeDTO cakeToAdd = CakeDTO.builder().title(DUPLICATED_TITLE).imageUrl("newUrl").description("newDescription").build();

		HttpEntity<CakeDTO> entity = new HttpEntity<>(cakeToAdd, new HttpHeaders());

		ResponseEntity<String> response = restTemplate.exchange(endpointUrl(), HttpMethod.POST, entity, String.class);

		Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		
		Assert.assertEquals(String.format("Title for '%s' cake already exists in database.", DUPLICATED_TITLE), response.getBody());

	}

	private void assertRestResultsAgainstDatabase(CakeDTO[] fromRest, CakeEntity[] testData) {
		assertRestResultsAgainstDatabase(Arrays.asList(fromRest), Arrays.asList(testData));
	}

	private void assertRestResultsAgainstDatabase(List<CakeDTO> fromRest, List<CakeEntity> testData) {
		Assert.assertEquals(testData.size(), fromRest.size());
		ModelMapper mm = new ModelMapper();
		fromRest.stream().sorted(Comparator.comparing(CakeDTO::getId)).forEach((dto) -> {
			Assert.assertTrue(cakeRepository.existsById(dto.getId()));
			CakeEntity re = cakeRepository.findById(dto.getId()).get();
			CakeDTO fromDb = mm.map(re, CakeDTO.class);
			Assert.assertEquals(dto, fromDb);
		});
	}

	private Iterable<CakeEntity> convertArrayToIterable(CakeEntity[] array) {
		return () -> Arrays.stream(array).iterator();
	}

	private String endpointUrl() {
		return "http://localhost:" + port + "/cakes";
	}
	
	@LocalServerPort
	protected int			port;

	TestRestTemplate restTemplate = new TestRestTemplate();
	
	@Autowired
	CakeController			sc;

	@Autowired
	ObjectMapper			objectMapper;

	@Autowired
	CakeRepository			cakeRepository;

	private CakeConverter cakeConverter = new CakeConverter();
	
}
