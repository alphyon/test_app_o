package com.elaniin.nitro.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "collector")
@ApiModel(description = "All properties about Collectors ")
public class Collector {

//	@Id
//	@Column(unique = true, nullable = false)
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "MANIFOLD_SEQ")
//	@SequenceGenerator(sequenceName = "manifold_seq", allocationSize = 1, name = "MANIFOLD_SEQ")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Database generated ID")
	private Long id;
	@Column(nullable = false, length = 25, unique = true)
	@ApiModelProperty(notes = "Collectors Name")
	private String name;
	@Lob
	@ApiModelProperty(notes = "Collectos Photo")
	private String photo;
	@ApiModelProperty(notes = "Collectos Options")
	private String options;

	@ManyToOne(cascade = CascadeType.ALL)
	@ApiModelProperty(notes = "Categories obj from Collectors info")
	private Categories categories;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "channels_colector", joinColumns = @JoinColumn(name = "colector_id"), inverseJoinColumns = @JoinColumn(name = "channels_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "colector_id", "channels_id" }) })
	@ApiModelProperty(notes = "Channel List from Collectors info")
	private List<Channel> channels = new ArrayList<>();

	public Collector() {
		super();

	}

	public Collector(String name, String photo, String options, Categories categories, List<Channel> channels) {
		super();
		this.name = name;
		this.photo = photo;
		this.options = options;
		this.categories = categories;
		this.channels = channels;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "Collector [id=" + id + ", name=" + name + ", photo=" + photo + ", options=" + options + ", categories="
				+ categories + ", channels=" + channels + "]";
	}

}
