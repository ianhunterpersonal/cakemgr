package com.waracle.cakemgr.model;

import org.junit.Assert;
import org.junit.Test;

public class PojoTests {

	@Test
	public void testCakeDTOEqualsIgnoresId() {
		
		CakeDTO toTest1 = CakeDTO.builder().id(1).title("Title").imageUrl("URL1").description("Description").build();
		CakeDTO toTest2 = CakeDTO.builder().id(1).title("Title").imageUrl("URL1").description("Description").build();
		
		Assert.assertEquals(toTest1, toTest2);
		
	}
	
	@Test
	public void testCakeEntityEqualsIgnoresId() {
		
		CakeEntity toTest1 = CakeEntity.builder().id(1).title("Title").imageUrl("URL1").description("Description").build();
		CakeEntity toTest2 = CakeEntity.builder().id(2).title("Title").imageUrl("URL1").description("Description").build();
		
		Assert.assertEquals(toTest1, toTest2);
		
	}
	
	
}
