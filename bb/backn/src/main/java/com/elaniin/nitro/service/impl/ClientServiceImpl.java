package com.elaniin.nitro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elaniin.nitro.dto.ClientDTO;
import com.elaniin.nitro.entity.Client;
import com.elaniin.nitro.repository.ClientRepository;
import com.elaniin.nitro.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	@Override
	public Client getClientByCode(String code) {
		return clientRepository.getClientByCode(code);
	}

	@Override
	public ClientDTO getInvoiceCodeByClient(String code, String date) {
		return clientRepository.getInvoiceByClient(code, date);
	}

	


}
