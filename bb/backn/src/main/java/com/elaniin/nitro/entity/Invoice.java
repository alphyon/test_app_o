package com.elaniin.nitro.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "invoice")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String invoiceCode;
	@ManyToOne(cascade = CascadeType.ALL)
	private Client cliente;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pay_date", columnDefinition = "DATE")
	private Date PayDate;
	@Column(name = "expiration_date", columnDefinition = "DATE")
	private Date expirationDate;

	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Invoice(String invoiceCode, Client cliente, Date payDate, Date expirationDate) {
		super();
		this.invoiceCode = invoiceCode;
		this.cliente = cliente;
		PayDate = payDate;
		this.expirationDate = expirationDate;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getCliente() {
		return cliente;
	}

	public void setCliente(Client cliente) {
		this.cliente = cliente;
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
		return "Invoice [id=" + id + ", invoiceCode=" + invoiceCode + ", cliente=" + cliente + ", PayDate=" + PayDate
				+ ", expirationDate=" + expirationDate + "]";
	}

}
