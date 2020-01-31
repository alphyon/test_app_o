package com.elaniin.nitro.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ClientDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String clientCode;
	private String name;
	private String lastname;
	private String phoneNumber;
	private String address;
	private String invoiceCod;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY/MM/dd", timezone = "UTC")
	private Date payDate;

	public ClientDTO(String clientCode, String name, String lastname, String phoneNumber, String address,
			String invoiceCod, Date payDate) {
		super();
		this.clientCode = clientCode;
		this.name = name;
		this.lastname = lastname;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.invoiceCod = invoiceCod;
		this.payDate = payDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getInvoiceCod() {
		return invoiceCod;
	}

	public void setInvoiceCod(String invoiceCod) {
		this.invoiceCod = invoiceCod;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ClientDTO [id=" + id + ", clientCode=" + clientCode + ", name=" + name + ", lastname=" + lastname
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
	}

}
