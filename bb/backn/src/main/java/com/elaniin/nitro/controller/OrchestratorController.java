package com.elaniin.nitro.controller;

import com.elaniin.nitro.dto.OrchestratorDTO;
import com.elaniin.nitro.entity.Orchestrator;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.OrchestatorService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orchestrator")
public class OrchestratorController extends GenericBase {
	@Autowired
	private OrchestatorService orchestatorService;
	public static final Logger logger = LoggerFactory.getLogger(OrchestratorController.class);

	@GetMapping("listbycollector/{id}")
	public ResponseEntity<Object> listByCollectorId(@PathVariable(name = "id") int id, WebRequest request)
			throws JsonProcessingException {
		try {
			List<OrchestratorDTO> orchestratorDTOList = orchestatorService.listAllDTO(id);
			if (orchestratorDTOList.isEmpty()) {
				throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
			}
			return genericCustomMessageResponse(ConstantMessages.SUCESS_LIST, request, orchestratorDTOList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return genericCustomMessageResponse(ConstantMessages.EMPTY_DATA, request, new ArrayList<>());

		}
	}

	@PostMapping("store")
	public ResponseEntity<Object> saveOrchestrator(@RequestBody OrchestratorDTO dto, WebRequest request)
			throws JsonProcessingException {
		try {
			Orchestrator orchestrator;
			orchestrator = orchestatorService.saveOrchestrator(dto);
			return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, orchestrator);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return genericCustomMessageResponse(ConstantMessages.SERVER_ERROR, request, dto);
		}
	}

	@PutMapping("update/{id}")
	public ResponseEntity<Object> updateOrchestator(@RequestBody OrchestratorDTO dto, @PathVariable int id,
			WebRequest request) throws JsonProcessingException {
		try {
			Orchestrator or;
			or = orchestatorService.updateOrchestrator(dto);
			return genericCustomMessageResponse(ConstantMessages.UPDATE_MESSAGE, request, or);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return genericCustomMessageResponse(ConstantMessages.WARNING_MESSAGE, request, dto);
		}
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> delete(@RequestBody OrchestratorDTO dto, WebRequest request,
			@PathVariable(name = "id") int id) throws JsonProcessingException {
		try {
			boolean response = orchestatorService.deleteOrchestrator(id);
			if (!response) {
				throw new Exception();
			}
			return genericCustomMessageResponse(ConstantMessages.DELETE_MESSAGE, request, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return genericCustomMessageResponse(ConstantMessages.SERVER_ERROR, request, false);
		}
	}

}
