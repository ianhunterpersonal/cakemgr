package com.waracle.cakemgr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "Cake", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID"), @UniqueConstraint(columnNames = "TITLE")
})
public class CakeEntity implements Serializable {

	private static final long	serialVersionUID	= -1798070786993154676L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = true)
	@EqualsAndHashCode.Exclude
	private Integer				id;

	@Column(name = "TITLE", unique = true, nullable = false, length = 100)
	private String					title;

	@JsonProperty("desc")
	@Column(name = "DESCRIPTION", unique = false, nullable = false, length = 100)
	private String					description;

	@Column(name = "IMAGE_URL", unique = false, nullable = false, length = 300)
	private String					imageUrl;

}
