package com.elaniin.nitro.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Table
public class Orchestrator implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Lob
	private String json;
	private String endpoint;
	private int idCollector;
	@ManyToOne(cascade = CascadeType.ALL)
	private Interpreters interpreters;

	public Orchestrator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Orchestrator(String json, String endpoint, int idCollector, Interpreters interpreters) {
		super();
		this.json = json;
		this.endpoint = endpoint;
		this.idCollector = idCollector;
		this.interpreters = interpreters;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public int getIdCollector() {
		return idCollector;
	}

	public void setIdCollector(int idCollector) {
		this.idCollector = idCollector;
	}

	public Interpreters getInterpreters() {
		return interpreters;
	}

	public void setInterpreters(Interpreters interpreters) {
		this.interpreters = interpreters;
	}

}
