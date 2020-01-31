package com.elaniin.nitro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "categories")
@ApiModel(description = "All properties about Categories ")
public class Categories {

//	@Id
//	@Column(unique = true, nullable = false)
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "Ccategory_SEQ")
//	@SequenceGenerator(sequenceName = "category_seq", allocationSize = 1, name = "Category_SEQ")
	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Database generated ID")
	private Long id;
	@Column(nullable = false, length = 25, unique = true)
	@ApiModelProperty(notes = "Category Name")
	private String categoryName;

	public Categories() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Categories(String categoryName) {
		super();
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Categories [id=" + id + ", categoryName=" + categoryName + "]";
	}
}