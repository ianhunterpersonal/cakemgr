package com.waracle.cakemgr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.waracle.cakemgr.exceptions.DuplicateTitleException;
import com.waracle.cakemgr.exceptions.EmptyUrlException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BaseController {

	@ExceptionHandler
	public ResponseEntity<String> handleException(Exception ex) {
		log.error(ex);
		if (ex instanceof DuplicateTitleException) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
		} else if (ex instanceof EmptyUrlException) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} else { // Default
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
