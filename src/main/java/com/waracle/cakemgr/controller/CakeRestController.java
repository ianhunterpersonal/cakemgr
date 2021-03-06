package com.waracle.cakemgr.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waracle.cakemgr.model.CakeDTO;
import com.waracle.cakemgr.service.CakeService;

@RestController
@RequestMapping(value = "/cakes")
public class CakeRestController extends BaseController {

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchAll() throws EntityNotFoundException {
		return new ResponseEntity<List>(cakeService.fetchAll(), HttpStatus.OK);
	}

	@CrossOrigin(maxAge = 3600)
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNewCake(@RequestBody CakeDTO cakeDTO) {
		return new ResponseEntity<CakeDTO>(cakeService.addCake(cakeDTO), HttpStatus.OK);
	}

	@Autowired
	private CakeService cakeService;

}
