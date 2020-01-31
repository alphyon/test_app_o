package com.elaniin.nitro.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InvoiceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String invoiceCode;
	private long clienteId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd", timezone = "UTC")
	private Date PayDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd", timezone = "UTC")
	private Date expirationDate;

	public InvoiceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public InvoiceDTO(Long id, String invoiceCode, long clienteId, Date payDate, Date expirationDate) {
		super();
		this.id = id;
		this.invoiceCode = invoiceCode;
		this.clienteId = clienteId;
		PayDate = payDate;
		this.expirationDate = expirationDate;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public long getClienteId() {
		return clienteId;
	}

	public void setClienteId(long clienteId) {
		this.clienteId = clienteId;
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

	@Override
	public String toString() {
		return "InvoiceDTO [id=" + id + ", invoiceCode=" + invoiceCode + ", clienteId=" + clienteId + ", PayDate="
				+ PayDate + ", expirationDate=" + expirationDate + "]";
	}

}
