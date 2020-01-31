package com.elaniin.nitro.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "interpreters")
public class Interpreters implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
//	@Column(unique = true, nullable = false)
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "Interpreters_SEQ")
//	@SequenceGenerator(sequenceName = "interpreters_seq", allocationSize = 1, name = "Interpreters_SEQ")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 100, unique = true)
	private String name;
	@Column(length = 300)
	private String description;
	@ManyToOne(cascade = CascadeType.ALL)
	private Collector collector;

	public Interpreters() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Interpreters(String name, String description, Collector collector) {
		super();
		this.name = name;
		this.description = description;
		this.collector = collector;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collector getCollector() {
		return collector;
	}

	public void setCollector(Collector collector) {
		this.collector = collector;
	}

	@Override
	public String toString() {
		return "Interpreters [id=" + id + ", name=" + name + ", description=" + description + ", collector=" + collector
				+ "]";
	}

}