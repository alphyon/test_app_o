package com.elaniin.nitro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elaniin.nitro.dto.OrchestratorDTO;
import com.elaniin.nitro.entity.Interpreters;
import com.elaniin.nitro.entity.Orchestrator;
import com.elaniin.nitro.repository.InterpretersRepository;
import com.elaniin.nitro.repository.OrchestratorRepository;
import com.elaniin.nitro.service.OrchestatorService;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

@Service
public class OrchestatorServiceImp implements OrchestatorService {
	@Autowired
	private OrchestratorRepository repository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private InterpretersRepository interpreterRepository;

	@Override
	public List<OrchestratorDTO> listAllDTO(int id) {
		List<OrchestratorDTO> orchestratorList = new ArrayList<>();
		for (Orchestrator orchestrator : repository.findAllByIdCollector(id)) {
			orchestratorList.add(convertToDto(orchestrator));
		}
		return orchestratorList;
	}

	private OrchestratorDTO convertToDto(Orchestrator orchestrator) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return modelMapper.map(orchestrator, OrchestratorDTO.class);
	}

	private Orchestrator convertToOrchestator(OrchestratorDTO dto) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return modelMapper.map(dto, Orchestrator.class);
	}

	@Override
	public Orchestrator saveOrchestrator(OrchestratorDTO dto) {
		Orchestrator orchestrator = null;
		Interpreters interpreters = interpreterRepository.findById(dto.getIdInterpreter()).get();
		if (interpreters != null) {
			orchestrator = new Orchestrator(dto.getJson(), dto.getEndpoint(), dto.getIdCollector(), interpreters);
		}
		return repository.save(orchestrator);
	}

	@Override
	public Orchestrator updateOrchestrator(OrchestratorDTO dto) {
		Orchestrator response;
		try {
			Optional<Orchestrator> orchestrator = repository.findById(dto.getId());
			if (orchestrator.isEmpty()) {
				throw new Exception("NOT FOUND ELEMENT");
			}
			response = repository.save(convertToOrchestator(dto));
		} catch (Exception e) {
			e.printStackTrace();
			response = convertToOrchestator(dto);
		}
		return response;
	}

	@Override
	public boolean deleteOrchestrator(int id) {
		boolean or = repository.existsById((long) id);
		if (or) {
			repository.deleteById((long) id);
		}
		return or;
	}

	@Override
	public Optional<Orchestrator> getOrchestratorById(int id) {
		return repository.findById((long) id);
	}

	@Override
	public OrchestratorDTO getSingleOrchestrator(long idInterpreter) {
		Orchestrator orchestrator = repository.singleOrchestrator(idInterpreter);
		JsonParser parser = new JsonParser();
		JsonArray jsonObject = new JsonArray();
		jsonObject = parser.parse(orchestrator.getJson()).getAsJsonArray();
		OrchestratorDTO orchestratorDTO = null;
		if (orchestrator != null) {
			orchestratorDTO = new OrchestratorDTO();
			orchestratorDTO.setId(orchestrator.getId());
			orchestratorDTO.setEndpoint(orchestrator.getEndpoint());
			orchestratorDTO.setJson(jsonObject.toString());
			orchestratorDTO.setIdCollector(orchestrator.getIdCollector());
		}
		System.err.println(orchestratorDTO);
		return orchestratorDTO;
	}

	@Override
	public OrchestratorDTO getSingleOrchestratorById(long id) {
		return convertToDto(repository.findById(id).get());
	}
	
	
}
