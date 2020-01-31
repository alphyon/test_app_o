package com.elaniin.nitro.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "proxies")
public class Proxy implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String code;
	@NotNull
	private String name;
	@NotNull
	private String url;
	@Column(length = 255)
	private String inputParameters;
	@Column(length = 900)
	private String outParameters;
	private int idCollector;
	@ManyToOne(cascade = CascadeType.ALL)
	private Interpreters interpreters;
	private long step;
	@Column(length = 25)
	private String method;

	public Proxy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Proxy(String name, String code, String url, String inputParameters, String outParameters, int idCollector,
			Interpreters interpreters, long step, String method) {
		super();
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

	public Interpreters getInterpreters() {
		return interpreters;
	}

	public void setInterpreters(Interpreters interpreters) {
		this.interpreters = interpreters;
	}

	@Override
	public String toString() {
		return "Proxy [id=" + id + ", code=" + code + ", name=" + name + ", url=" + url + ", inputParameters="
				+ inputParameters + ", outParameters=" + outParameters + ", idCollector=" + idCollector
				+ ", interpreters=" + interpreters + ", step=" + step + ", method=" + method + "]";
	}

}
