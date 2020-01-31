package com.elaniin.nitro.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InvoiceDetailsDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientCode;
	private String name;
	private String lastname;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY/MM/dd", timezone = "UTC")
	private Date PayDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY/MM/dd", timezone = "UTC")
	private Date expirationDate;
	private String code;
	private BigDecimal quota;
	private BigDecimal balanceInArrears;
	private String state;
	private String invoiceCode;

	public InvoiceDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvoiceDetailsDTO(String clientCode, String invoiceCode, String name, String lastname, Date payDate,
			Date expirationDate, String code, BigDecimal quota, BigDecimal balanceInArrears, String state) {
		super();
		this.clientCode = clientCode;
		this.name = name;
		this.lastname = lastname;
		PayDate = payDate;
		this.expirationDate = expirationDate;
		this.code = code;
		this.quota = quota;
		this.balanceInArrears = balanceInArrears;
		this.state = state;
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
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

	public Date getPayDate() {
		return PayDate;
	}

	public void setPayDate(Date payDate) {
		PayDate = payDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getQuota() {
		return quota;
	}

	public void setQuota(BigDecimal quota) {
		this.quota = quota;
	}

	public BigDecimal getBalanceInArrears() {
		return balanceInArrears;
	}

	public void setBalanceInArrears(BigDecimal balanceInArrears) {
		this.balanceInArrears = balanceInArrears;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "InvoiceDetailsDTO [clientCode=" + clientCode + ", name=" + name + ", lastname=" + lastname
				+ ", PayDate=" + PayDate + ", expirationDate=" + expirationDate + ", code=" + code + ", quota=" + quota
				+ ", balanceInArrears=" + balanceInArrears + ", state=" + state + "]";
	}

}
