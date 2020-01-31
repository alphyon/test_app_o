package com.elaniin.nitro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elaniin.nitro.dto.InvoiceDTO;
import com.elaniin.nitro.dto.InvoiceDetailsDTO;
import com.elaniin.nitro.entity.InvoiceDetails;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {

	static String INVOICE_DETAILS_QUERY = "select new com.elaniin.nitro.dto.InvoiceDetailsDTO( c.clientCode, i.invoiceCode, c.name, c.lastname, i.PayDate, i.expirationDate, p.code, id.quota, id.balanceInArrears, id.state) "
			+ "from InvoiceDetails id inner join id.invoice i inner join id.products p inner join i.cliente c where c.id = :idClient";
	
	static String INVOICE = "select new com.elaniin.nitro.dto.InvoiceDTO(i.id, i.invoiceCode, i.cliente.id, i.PayDate, i.expirationDate) from Invoice i where i.invoiceCode = :code";
			

	@Query(value = INVOICE_DETAILS_QUERY)
	List<InvoiceDetailsDTO> invoiceDetailByClient(@Param("idClient") Long idClient);

	@Query(nativeQuery = true)
	InvoiceDetailsDTO getInvoiceDetailMonth(@Param("idClient") Long idClient, @Param("paramDate") String paramDate);
	
	@Query(value = INVOICE)
	InvoiceDTO getInvoice(@Param("code") String code);
	

}
