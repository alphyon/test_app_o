package com.elaniin.nitro.dto;

import java.io.Serializable;
import java.util.List;

import com.elaniin.nitro.entity.Categories;
import com.elaniin.nitro.entity.Channel;

public class CollectorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String photo;
	private Long categories_id;
	private String options;

	// new added
	private List<Channel> channels;
	private Categories categories;

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

	public CollectorDTO() {
		super();
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

	public Long getCategories_id() {
		return categories_id;
	}

	public void setCategories_id(Long categories_id) {
		this.categories_id = categories_id;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "CollectorDTO [id=" + id + ", name=" + name + ", photo=" + photo + ", categories_id=" + categories_id
				+ ", options=" + options + ", channels=" + channels + ", categories=" + categories + "]";
	}

}
