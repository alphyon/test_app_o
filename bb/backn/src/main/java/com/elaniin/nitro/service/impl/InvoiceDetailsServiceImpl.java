package com.elaniin.nitro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elaniin.nitro.dto.InvoiceDTO;
import com.elaniin.nitro.dto.InvoiceDetailsDTO;
import com.elaniin.nitro.repository.InvoiceDetailsRepository;
import com.elaniin.nitro.service.InvoiceDetailsService;

@Service
public class InvoiceDetailsServiceImpl implements InvoiceDetailsService {

	@Autowired
	InvoiceDetailsRepository invoiceDetailsRepository;

	@Override
	public List<InvoiceDetailsDTO> getInvoiceDetailsByClient(Long id) {
		return invoiceDetailsRepository.invoiceDetailByClient(id);
	}

	@Override
	public InvoiceDetailsDTO getInvoiceDetailByMonth(Long id, String date) {
		return invoiceDetailsRepository.getInvoiceDetailMonth(id, date);
	}

	@Override
	public InvoiceDTO getInvoice(String code) {
		return invoiceDetailsRepository.getInvoice(code);
	}

}
