package com.elaniin.nitro.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.elaniin.nitro.dto.CollectorDTO;
import com.elaniin.nitro.entity.Collector;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.ICollectorService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/collector/")
public class CollectorController extends GenericBase {

	public static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private ICollectorService iCollectorService;

	@ApiOperation(value = "List all Collectors ")
	@GetMapping("collectorList")
	public ResponseEntity<Object> collectorList(WebRequest request) throws JsonProcessingException {
		List<CollectorDTO> collectorDTOs = iCollectorService.collectorList();
		if (collectorDTOs.isEmpty()) {
			throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCESS_LIST, request, collectorDTOs);
	}

	@ApiOperation(value = "Function for store Collectos")
	@PostMapping("store")
	public ResponseEntity<Object> storeManifold(@RequestBody CollectorDTO collectorDTO, WebRequest request)
			throws JsonProcessingException {
		Collector collector;
		try {
			collector = iCollectorService.storeCollector(collectorDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, collector);
	}

	@ApiOperation(value = "Function for updated collectos")
	@PutMapping("update/{id}")
	public ResponseEntity<Object> updateCollector(@RequestBody CollectorDTO collectorDTO,
			@ApiParam(value = "Collector ID from which collector will be updated from databas", required = true, example = "1") @PathVariable Integer id,
			WebRequest request) throws JsonProcessingException {
		Collector collector;
		try {
			collector = iCollectorService.updateCollector(collectorDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.UPDATE_MESSAGE, request, collector);
	}
}
