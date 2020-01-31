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

import com.elaniin.nitro.dto.InterpretersDTO;
import com.elaniin.nitro.dto.OrchestratorDTO;
import com.elaniin.nitro.entity.Interpreters;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.InterpretersService;
import com.elaniin.nitro.service.OrchestatorService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="CRUD Interpreters", description = "CRUD for manage all Interpresets for System")
@RestController
@RequestMapping("/api/v1/interpreters/")
public class InterpretersController extends GenericBase {
	
	public static final Logger logger = LoggerFactory.getLogger(InterpretersController.class);
	
	@Autowired
	private InterpretersService iInterpretersService;
	
	@Autowired
	private OrchestatorService orchestratorService;


	@ApiOperation(value="List all Interpreters availables for Users")
	@GetMapping("interpretadoresList")
	public ResponseEntity<Object> interpretadoresList(WebRequest request) throws JsonProcessingException {
		List<InterpretersDTO> interpretersDTOs = iInterpretersService.interpretersList();
		if (interpretersDTOs.isEmpty()) {
			throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCESS_LIST, request, interpretersDTOs);
	}
	
	@GetMapping("orchestratorobj/{id}")
	public ResponseEntity<Object> getSingleInterpreter(WebRequest request, @PathVariable Long id) throws JsonProcessingException {
		InterpretersDTO interpretersDTO = null;
		OrchestratorDTO orchestratorDTO = null;
		try {
			interpretersDTO = iInterpretersService.getSingleInterpreter(id);
			
			if (interpretersDTO == null) {
				throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
			}else {
				orchestratorDTO = orchestratorService.getSingleOrchestrator(interpretersDTO.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		System.err.println(orchestratorDTO);
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, orchestratorDTO);
	}

	@ApiOperation(value="Function for store Interpreters")
	@PostMapping("store")
	public ResponseEntity<Object> storeInterpreters(@RequestBody InterpretersDTO interpretersDTO, WebRequest request)
			throws JsonProcessingException {
		Interpreters interpreters;
		try {
			interpreters = iInterpretersService.storeInterpretador(interpretersDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, interpreters);
	}

	@ApiOperation(value="Function for update Interpreters")
	@PutMapping("update/{id}")
	public ResponseEntity<Object> updateManifold(@RequestBody InterpretersDTO interpretersDTO, @ApiParam(value="Interpreters ID from which interpreters will be updated from database", required = true, example="1") @PathVariable Integer id,
			WebRequest request) throws JsonProcessingException {
		Interpreters interpreters;
		try {
			interpreters = iInterpretersService.updateInterpreters(interpretersDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.UPDATE_MESSAGE, request, interpreters);
	}
}