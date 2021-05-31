package com.waracle.cakemgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waracle.cakemgr.model.CakeConverter;
import com.waracle.cakemgr.model.CakeDTO;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.repository.CakeRepository;

@Service
@Transactional
public class CakeService {

	public void addCake(CakeDTO dto) {

		CakeEntity entity = new CakeConverter().from(dto);
		
		cakeRepository.save(entity);
		
	}
	
	public List<CakeDTO> fetchAll() {
		List<CakeEntity> allEntities = new ArrayList<>();
		cakeRepository.findAll().forEach(allEntities::add);
		return allEntities.stream().map(new CakeConverter()::from).collect(Collectors.toList());
	}
	
	@Autowired
	private CakeRepository cakeRepository;

	public void updateCake(CakeDTO dto) {
		cakeRepository.save(cakeConverter.from(dto));
	}
	
	private CakeConverter cakeConverter = new CakeConverter();

	public List<CakeDTO> findByTitle(String title) {
		return cakeRepository.findByTitle(title).stream().map(cakeConverter::from).collect(Collectors.toList());
	}
	
}
