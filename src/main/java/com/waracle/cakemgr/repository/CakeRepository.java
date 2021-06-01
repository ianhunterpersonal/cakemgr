package com.waracle.cakemgr.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waracle.cakemgr.model.CakeEntity;

@Repository
public interface CakeRepository extends CrudRepository<CakeEntity, Integer> {

	public List<CakeEntity> findByTitle(String title);

	public boolean existsById(Integer id);

	public boolean existsByTitle(String title);

}
