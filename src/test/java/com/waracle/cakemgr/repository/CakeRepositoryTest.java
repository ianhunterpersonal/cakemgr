package com.waracle.cakemgr.repository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.service.CakeService;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({ObjectMapper.class, CakeService.class})
public class CakeRepositoryTest {

	@Autowired
	private TestEntityManager				testEntityManager;

	@Autowired
	private CakeRepository	cakeRepository;

	@Before
	public void beforeEachTest() {
		cakeRepository.deleteAll();		
	}
	
	@Test
	public void testFindAll() {

		Assert.assertEquals(0, cakeRepository.count());

		List<CakeEntity> testReadings = populateDatabase(
				CakeEntity.builder().id(1).title("CakeTitle1").imageUrl("url1").description("CakeDescription1").build(),
				CakeEntity.builder().id(2).title("CakeTitle2").imageUrl("url2").description("CakeDescription2").build(),
				CakeEntity.builder().id(3).title("CakeTitle3").imageUrl("url3").description("CakeDescription3").build()
		);

		Assert.assertEquals(3, cakeRepository.count());
		Iterator<CakeEntity> cakesItr = cakeRepository.findAll().iterator();
		Assert.assertEquals(testReadings.get(0), cakesItr.next());
		Assert.assertEquals(testReadings.get(1), cakesItr.next());
		Assert.assertEquals(testReadings.get(2), cakesItr.next());
		Assert.assertFalse(cakesItr.hasNext());
	}

	@Test
	public void testAddNewCake() {
	
		CakeEntity toAdd = CakeEntity.builder().title("CakeTitle1").imageUrl("url1").description("CakeDescription1").build();
		
		Assert.assertEquals(0, cakeRepository.count());
		
		CakeEntity added = cakeRepository.save(toAdd);
		
		Assert.assertEquals(1, cakeRepository.count());
		Assert.assertEquals(added, toAdd);

	}
	
	@Test(expected = Exception.class)
	public void testAddNewCakeDuplicateTitleFails() {
	
		Assert.assertEquals(0, cakeRepository.count());
		
		cakeRepository.save(CakeEntity.builder().title("EXISTING_TITLE").imageUrl("url1").description("CakeDescription1").build());
		
		cakeRepository.save(CakeEntity.builder().title("EXISTING_TITLE").imageUrl("url1").description("CakeDescription1").build());
		
		Assert.assertEquals(1, cakeRepository.count());

	}
	
	private List<CakeEntity> populateDatabase(CakeEntity ... entityArray) {
		
		cakeRepository.deleteAll();
		
		Arrays.asList(entityArray).stream().forEach(e -> e.setId(null));
		
		List<CakeEntity> testReadings =
			Arrays.asList(entityArray).stream()
				.sorted(Comparator.comparing(CakeEntity::getTitle))
				.collect(Collectors.toList());
		
		testReadings.forEach(testEntityManager::persistAndFlush);
		
		Assert.assertEquals(testReadings.size(), cakeRepository.count());
		return testReadings;
	}
	
}
