package com.waracle.cakemgr.model;

public class CakeConverter {

	public CakeDTO from(CakeEntity entity) {
		return CakeDTO
				.builder()
					.id(entity.getId())
					.title(entity.getTitle())
					.description(entity.getDescription())
					.imageUrl(entity.getImageUrl())
					.build();
	}
	
	public CakeEntity from(CakeDTO dto) {
		return CakeEntity
			.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.description(dto.getDescription())
				.imageUrl(dto.getImageUrl())
				.build();
	}
	
	
}
