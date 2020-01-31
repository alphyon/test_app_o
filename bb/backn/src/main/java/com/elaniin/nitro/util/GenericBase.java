package com.elaniin.nitro.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GenericBase {

	@SuppressWarnings("deprecation")
	protected ResponseEntity<Object> genericCustomMessageResponse(String message, WebRequest request, Object object)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String node = mapper.writeValueAsString(object);
		JsonNode jsonNode = mapper.readTree(node);
		ArrayNode arrayNode = mapper.createArrayNode();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String dateFormated = format.format(new Date());

		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("date", dateFormated);
		objectNode.put("message", message);
		objectNode.put("details", jsonNode);
		objectNode.put("status", HttpStatus.OK.value());
		objectNode.put("locale", request.getLocale().toString());
		arrayNode.add(objectNode);
		return new ResponseEntity<Object>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode),
				HttpStatus.OK);
	}

}
