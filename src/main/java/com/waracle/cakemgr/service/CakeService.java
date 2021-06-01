package com.waracle.cakemgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waracle.cakemgr.exceptions.DuplicateTitleException;
import com.waracle.cakemgr.exceptions.EmptyUrlException;
import com.waracle.cakemgr.model.CakeConverter;
import com.waracle.cakemgr.model.CakeDTO;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.repository.CakeRepository;

@Service
@Transactional
public class CakeService {

	@Autowired
	private CakeRepository	cakeRepository;

	private CakeConverter	cakeConverter	= new CakeConverter();

	public List<CakeDTO> fetchAll() {
		List<CakeEntity> allEntities = new ArrayList<>();
		cakeRepository.findAll().forEach(allEntities::add);
		return allEntities.stream().map(new CakeConverter()::from).collect(Collectors.toList());
	}

	public CakeDTO addCake(CakeDTO dto) throws RuntimeException {

		validateDto(dto); // Throws if invalid
		
		CakeEntity toSave = new CakeConverter().from(dto);

		CakeEntity saved = cakeRepository.save(toSave);
		return cakeConverter.from(saved);
	}

	private void validateDto(CakeDTO dto) throws RuntimeException {

		if ((dto.getImageUrl() == null) || dto.getImageUrl().isBlank()) {
			throw new EmptyUrlException();
		}

		if (cakeRepository.existsByTitle(dto.getTitle())) {
			throw new DuplicateTitleException(dto.getTitle());
		}
	}
}
