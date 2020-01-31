package com.elaniin.nitro.dto;

import java.io.Serializable;

public class InterpretersDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private Long idCollector;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public Long getIdCollector() {
		return idCollector;
	}

	public void setIdCollector(Long idCollector) {
		this.idCollector = idCollector;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
