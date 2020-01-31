package com.elaniin.nitro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "channel")
@ApiModel(description = "All properties about Channels ")
public class Channel {

//	@Id
//	@Column(unique = true, nullable = false)
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CHANNEL_SEQ")
//	@SequenceGenerator(sequenceName = "channel_seq", allocationSize = 1, name = "CHANNEL_SEQ")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Database generated ID")
	private Long id;
	@ApiModelProperty(notes = "Channel name")
	@Column(nullable = false, length = 25, unique = true)
	private String name;

	public Channel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Channel(String name) {
		super();
		this.name = name;
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

	@Override
	public String toString() {
		return "Channel [id=" + id + ", name=" + name + "]";
	}
}