package com.elaniin.nitro.service;

import com.elaniin.nitro.dto.OrchestratorDTO;
import com.elaniin.nitro.entity.Orchestrator;

import java.util.List;
import java.util.Optional;

public interface OrchestatorService {
    List<OrchestratorDTO> listAllDTO(int id);
    Orchestrator saveOrchestrator(OrchestratorDTO dto);
    Orchestrator updateOrchestrator(OrchestratorDTO dto);
    boolean deleteOrchestrator(int id);
    Optional<Orchestrator> getOrchestratorById(int id);
    OrchestratorDTO getSingleOrchestratorById(long id);
    OrchestratorDTO getSingleOrchestrator(long idInterpreter);
}
