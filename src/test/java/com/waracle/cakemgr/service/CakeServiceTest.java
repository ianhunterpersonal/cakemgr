package com.waracle.cakemgr.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.waracle.cakemgr.model.CakeConverter;
import com.waracle.cakemgr.model.CakeDTO;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.repository.CakeRepository;

@RunWith(MockitoJUnitRunner.class)
public class CakeServiceTest {

	@Mock
	CakeRepository				cakeRepository;

	@InjectMocks
	private CakeService		cakeService		= new CakeService();

	private List<CakeEntity>	testCakes;		// A batch of test results to return from Mock repository

	private CakeConverter		cakeConverter	= new CakeConverter();

	@Before
	public void setUp() throws Exception {
		testCakes = Arrays
				.asList(
						CakeEntity.builder().id(1).title("CakeTitle1").imageUrl("url1").description("CakeDescripion1").build(),
						CakeEntity.builder().id(2).title("CakeTitle2").imageUrl("url2").description("CakeDescription2").build(),
						CakeEntity.builder().id(3).title("CakeTitle3").imageUrl("url3").description("CakeDescription3").build())
				.stream().sorted(Comparator.comparing(CakeEntity::getId)).collect(Collectors.toList());
	}

	@Test
	public void testFetchAll() {
	
		Mockito.when(cakeRepository.findAll()).thenReturn(testCakes);
		
		List<CakeDTO> fromService = cakeService.fetchAll();
		
		Assert.assertEquals(testCakes, fromService.stream().map(cakeConverter::from).collect(Collectors.toList()));
		
	}
	
	@Test
	public void testAddNewCake() throws Throwable {

		CakeEntity expectedEntity = CakeEntity.builder().id(1).title("CakeTitle1").imageUrl("url1").description("CakeDescripion1").build();

		Mockito.when(cakeRepository.save(Mockito.any(CakeEntity.class))).thenReturn(expectedEntity);

		CakeDTO toAdd = cakeConverter.from(expectedEntity);
		CakeDTO addedCake = cakeService.addCake(toAdd);

		Assert.assertEquals(toAdd, addedCake);
		
	}

	
}
