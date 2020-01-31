package com.elaniin.nitro.service;

import java.util.List;

import com.elaniin.nitro.dto.InvoiceDTO;
import com.elaniin.nitro.dto.InvoiceDetailsDTO;

public interface InvoiceDetailsService {
	
	List<InvoiceDetailsDTO> getInvoiceDetailsByClient(Long id);
	
	InvoiceDetailsDTO getInvoiceDetailByMonth(Long id, String date);
	
	InvoiceDTO getInvoice(String code);

}
