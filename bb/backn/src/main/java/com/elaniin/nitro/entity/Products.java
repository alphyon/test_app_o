package com.elaniin.nitro.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private String name;
	private boolean state;
	private Date dischargeDate;
	private String payMode;

	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Products(String code, String name, boolean state, Date dischargeDate, String payMode) {
		super();
		this.code = code;
		this.name = name;
		this.state = state;
		this.dischargeDate = dischargeDate;
		this.payMode = payMode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Date getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	@Override
	public String toString() {
		return "Products [id=" + id + ", code=" + code + ", name=" + name + ", state=" + state + ", dischargeDate="
				+ dischargeDate + ", payMode=" + payMode + "]";
	}

}
