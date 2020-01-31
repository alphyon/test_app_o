package com.elaniin.nitro.dto;

import java.io.Serializable;

import com.elaniin.nitro.entity.Interpreters;

public class OrchestratorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	private String json;
	private String endpoint;
	private int idCollector;
	private long idInterpreter;

	private Interpreters interpreters;

	public OrchestratorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrchestratorDTO(String json, String endpoint, int idCollector, long idInterpreter) {
		super();
		this.json = json;
		this.endpoint = endpoint;
		this.idCollector = idCollector;
		this.idInterpreter = idInterpreter;
	}

	public Interpreters getInterpreters() {
		return interpreters;
	}

	public void setInterpreters(Interpreters interpreters) {
		this.interpreters = interpreters;
	}

	public long getIdInterpreter() {
		return idInterpreter;
	}

	public void setIdInterpreter(long idInterpreter) {
		this.idInterpreter = idInterpreter;
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

	@Override
	public String toString() {
		return "OrchestratorDTO [id=" + id + ", json=" + json + ", endpoint=" + endpoint + ", idCollector="
				+ idCollector + "]";
	}

}
