package com.elaniin.nitro.dto;

import java.io.Serializable;

import com.elaniin.nitro.entity.Interpreters;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class ProxyDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String code;
	private String url;
	private String inputParameters;
	private String outParameters;
	private int idCollector;
	private Interpreters interpreters;
	private long interpreterId;
	private long step;
	private String method;

	public ProxyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProxyDTO(String name, String code, String url, String inputParameters, String outParameters, int idCollector,
			Interpreters interpreters) {
		super();
		this.name = name;
		this.code = code;
		this.url = url;
		this.inputParameters = inputParameters;
		this.outParameters = outParameters;
		this.idCollector = idCollector;
		this.interpreters = interpreters;
	}

	public ProxyDTO(String name, String code, String url, String inputParameters, String outParameters, int idCollector,
			long interpreterId, long step, String method) {
		super();
		this.name = name;
		this.code = code;
		this.url = url;
		this.inputParameters = inputParameters;
		this.outParameters = outParameters;
		this.idCollector = idCollector;
		this.interpreterId = interpreterId;
		this.step = step;
		this.method = method;
	}

	public ProxyDTO(Long id, String name, String code, String url, String inputParameters, String outParameters,
			int idCollector, Interpreters interpreters, long step, String method) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.url = url;
		this.inputParameters = inputParameters;
		this.outParameters = outParameters;
		this.idCollector = idCollector;
		this.interpreters = interpreters;
		this.step = step;
		this.method = method;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public long getStep() {
		return step;
	}

	public void setStep(long step) {
		this.step = step;
	}

	public long getInterpreterId() {
		return interpreterId;
	}

	public void setInterpreterId(long interpreterId) {
		this.interpreterId = interpreterId;
	}

	public Interpreters getInterpreters() {
		return interpreters;
	}

	public void setInterpreters(Interpreters interpreters) {
		this.interpreters = interpreters;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInputParameters() {
		return inputParameters;
	}

	public void setInputParameters(String inputParameters) {
		this.inputParameters = inputParameters;
	}

	public String getOutParameters() {
		return outParameters;
	}

	public void setOutParameters(String outParameters) {
		this.outParameters = outParameters;
	}

	public int getIdCollector() {
		return idCollector;
	}

	public void setIdCollector(int idCollector) {
		this.idCollector = idCollector;
	}

}
