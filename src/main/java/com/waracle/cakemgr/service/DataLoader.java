package com.waracle.cakemgr.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.model.CakeDTO;
import com.waracle.cakemgr.repository.CakeRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class DataLoader {

    @Autowired
    private CakeRepository cakeRepository;

 	@Autowired
 	private CakeService					cakeService;

	@Autowired
	private ObjectMapper					objectMapper;

    //method invoked during the startup
    @PostConstruct
    public void loadData() throws JsonParseException, JsonMappingException, IOException {
   	 
   	log.info("Loading Data");
  		URL initDataURL = new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json");

  		CakeDTO[] entitiesFromSrc = objectMapper.readValue(initDataURL, CakeDTO[].class);

  		Arrays.asList(entitiesFromSrc).stream().distinct().forEach(cakeService::addCake);

  		cakeService.fetchAll().stream().forEach(System.out::println);
  		
    }

    //method invoked during the shutdown
    @PreDestroy
    public void removeData() {
   	 cakeRepository.deleteAll();
    }
    
}