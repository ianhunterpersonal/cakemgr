package com.waracle.cakemgr.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class CakeDTO implements Serializable {

	private static final long	serialVersionUID	= -1798070786993154676L;

	@EqualsAndHashCode.Exclude
	private Integer				id;

	@With
	private String					title;

	@JsonProperty("desc")
	private String					description;

	@JsonProperty("image")
	@NotNull
	private String					imageUrl;

}