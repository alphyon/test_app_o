package com.elaniin.nitro.service;

import com.elaniin.nitro.dto.ClientDTO;
import com.elaniin.nitro.entity.Client;

public interface ClientService {
	
	Client getClientByCode(String code);
	
	ClientDTO getInvoiceCodeByClient(String code, String date);

}
