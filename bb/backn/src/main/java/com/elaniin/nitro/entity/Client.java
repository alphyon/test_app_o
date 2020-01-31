package com.elaniin.nitro.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.elaniin.nitro.dto.ClientDTO;


@SqlResultSetMapping(name = "invoiceByClient", classes = {
		@ConstructorResult(targetClass = ClientDTO.class, columns = {
				@ColumnResult(name = "clientCode", type = String.class),
				@ColumnResult(name = "name", type = String.class),
				@ColumnResult(name = "lastname", type = String.class),
				@ColumnResult(name = "phoneNumber", type = String.class),
				@ColumnResult(name = "address", type = String.class),
				@ColumnResult(name = "invoiceCod", type = String.class),
				@ColumnResult(name = "payDate", type = Date.class)}) })


@Entity
@Table(name = "client")
@NamedNativeQuery(name = "Client.getInvoiceByClient", query = "select c.client_code as clientCode, c.name as name, c.lastname as lastname, c.phone_number as  phoneNumber, c.address as address, i.invoice_code as invoiceCod, "
		+ " i.pay_date as payDate from client c inner join invoice i on i.cliente_id = c.id where c.client_code = :codeClient and month(i.pay_date) = month(:paramDate) " , resultSetMapping = "invoiceByClient")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 25, unique = true)
	private String clientCode;
	@Column(nullable = false, length = 75)
	private String name;
	@Column(nullable = false, length = 75)
	private String lastname;
	@Column(nullable = false, length = 75)
	private String phoneNumber;
	@Column(nullable = false, length = 200)
	private String address;

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(String clientCode, String name, String lastname, String phoneNumber, String address) {
		super();
		this.clientCode = clientCode;
		this.name = name;
		this.lastname = lastname;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
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
		return "Cliente [id=" + id + ", clientCode=" + clientCode + ", name=" + name + ", lastname=" + lastname
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
	}

}

