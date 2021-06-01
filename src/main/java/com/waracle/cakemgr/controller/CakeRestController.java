package com.waracle.cakemgr.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waracle.cakemgr.exceptions.DuplicateTitleException;
import com.waracle.cakemgr.model.CakeDTO;
import com.waracle.cakemgr.service.CakeService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(value = "/cakes")
public class CakeRestController {

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchAll() throws EntityNotFoundException {
		return new ResponseEntity<List>(cakeService.fetchAll(), HttpStatus.OK);
	}
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNewCake(@RequestBody CakeDTO cakeDTO) {
		if (cakeDTO.getImageUrl() == null) {
			throw new IllegalArgumentException();
		}
		return new ResponseEntity<CakeDTO>(cakeService.addCake(cakeDTO), HttpStatus.OK);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> handleException(Exception ex) {
		log.error(ex);
		if (ex instanceof DuplicateTitleException) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);			
		} else { // Default
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	@Autowired
	private CakeService cakeService;

}
