package com.elaniin.nitro.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.elaniin.nitro.dto.ClientDTO;
import com.elaniin.nitro.dto.InvoiceDTO;
import com.elaniin.nitro.dto.InvoiceDetailsDTO;
import com.elaniin.nitro.entity.Client;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.ClientService;
import com.elaniin.nitro.service.InvoiceDetailsService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/core/")
public class InvoiceDetailsController extends GenericBase {

	public static final Logger logger = LoggerFactory.getLogger(InvoiceDetailsController.class);

	@Autowired
	InvoiceDetailsService invoiceDetailsService;

	@Autowired
	ClientService clientService;
	
	@GetMapping("cliente")
	public ResponseEntity<Object> getClient(WebRequest request, @RequestBody String requestData) throws JsonProcessingException {
		ClientDTO client = null;
		JSONObject response = new JSONObject(requestData);
		boolean clientCode = !response.getString("clienteId").isEmpty() ? true : false;
		if (clientCode) {
			client = clientService.getInvoiceCodeByClient(response.getString("clienteId"), response.getString("payDate"));
			if (client == null) {
				throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
			}
		}else{
			throw new ResourceNotFoundException(ConstantMessages.CLIENT_RESOURCE_NOT_FOUND);
		}
		return genericCustomMessageResponse("Cliente encontrado ", request, client); 
	}

	@GetMapping("details/{code}")
	public ResponseEntity<Object> getInvoiceDetails(@PathVariable String code, WebRequest request) throws JsonProcessingException {
		List<InvoiceDetailsDTO> details = new ArrayList<>();
		Client client = clientService.getClientByCode(code);
		if (client != null && client.getId() != null) {
			details = invoiceDetailsService.getInvoiceDetailsByClient(client.getId());
			if (details.isEmpty()) {
				throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
			}
		} else {
			throw new ResourceNotFoundException(ConstantMessages.CLIENT_RESOURCE_NOT_FOUND);
		}
		return genericCustomMessageResponse("Lista de Pagos realizados", request, details);
	}
	
	@GetMapping("specificPayment/{code}")
	public ResponseEntity<Object> getSpecificPayment(@PathVariable String code, WebRequest request, @RequestBody String requestData) throws JsonProcessingException{
		InvoiceDetailsDTO details = null;
		JSONObject response=new JSONObject(requestData);
		String param = "";
		if (response.get("PayDate") != null) {
			param = response.get("PayDate").toString();
			Client client = clientService.getClientByCode(code);
			if (client != null && client.getId() != null) {
				details = invoiceDetailsService.getInvoiceDetailByMonth(client.getId(), param);
				if (details == null) {
					throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
				}
			} else {
				throw new ResourceNotFoundException(ConstantMessages.CLIENT_RESOURCE_NOT_FOUND);
			}
		}
		return genericCustomMessageResponse("Pago obtenido de la siguiente fecha: " + param, request, details);
	}
	
	@GetMapping("invoice")
	public ResponseEntity<Object> getInvoice(WebRequest request, @RequestBody String requestData) throws JsonProcessingException, JSONException{
		InvoiceDTO invoiceDTO = null;
		JSONObject response =new JSONObject(requestData);
		if (!response.getString("invoiceCod").isEmpty()) {
			invoiceDTO = invoiceDetailsService.getInvoice(response.getString("invoiceCod").toString());
			if (invoiceDTO == null) {
				throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
			}
		}else {
			throw new ResourceNotFoundException("El codigo de la factura es requerido");
		}
		return genericCustomMessageResponse("Factura encontrada con codigo " + response.getString("invoiceCod"), request, invoiceDTO);
	}
	
}
