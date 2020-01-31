package com.elaniin.nitro.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.elaniin.nitro.dto.InvoiceDetailsDTO;

@SqlResultSetMapping(name = "invoiceDetailByMonth", classes = {
		@ConstructorResult(targetClass = InvoiceDetailsDTO.class, columns = {
				@ColumnResult(name = "clientCode", type = String.class),
				@ColumnResult(name = "invoiceCode", type = String.class),
				@ColumnResult(name = "name", type = String.class),
				@ColumnResult(name = "lastname", type = String.class),
				@ColumnResult(name = "PayDate", type = Date.class),
				@ColumnResult(name = "expirationDate", type = Date.class),
				@ColumnResult(name = "code", type = String.class),
				@ColumnResult(name = "quota", type = BigDecimal.class),
				@ColumnResult(name = "balanceInArrears", type = BigDecimal.class),
				@ColumnResult(name = "state", type = String.class) }) })

@Entity
@Table(name = "invoicedetails")
@NamedNativeQuery(name = "InvoiceDetails.getInvoiceDetailMonth", query = "select c.client_code as clientCode, i.invoice_code as invoiceCode, c.name, c.lastname ,i.pay_date as PayDate,i.expiration_date as expirationDate, "
		+ "p.code,id.quota,id.balance_in_arrears as balanceInArrears,id.state from invoice i inner join client c on i.cliente_id = c.id inner join invoicedetails id on id.invoice_id = i.id inner join products p on id.products_id = p.id "
		+ "where c.id = :idClient and month(i.pay_date) = month(:paramDate)  ", resultSetMapping = "invoiceDetailByMonth")
public class InvoiceDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	private Invoice invoice;
	@ManyToOne(cascade = CascadeType.ALL)
	private Products products;
	private BigDecimal quota;
	private BigDecimal balanceInArrears;
	private String state;

	public InvoiceDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvoiceDetails(Invoice invoice, Products products, BigDecimal quota, BigDecimal balanceInArrears,
			String state) {
		super();
		this.invoice = invoice;
		this.products = products;
		this.quota = quota;
		this.balanceInArrears = balanceInArrears;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "DetalleFactura [id=" + id + ", invoice=" + invoice + ", products=" + products + ", quota=" + quota
				+ ", balanceInArrears=" + balanceInArrears + ", state=" + state + "]";
	}

}
