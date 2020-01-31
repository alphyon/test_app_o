package com.elaniin.nitro.repository;

import com.elaniin.nitro.entity.Orchestrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrchestratorRepository extends JpaRepository<Orchestrator, Long> {

	static String SINGLE_ORCHESTRATOR = "select o from Orchestrator o where o.interpreters.id = :idInterpreter";

	public List<Orchestrator> findAllByIdCollector(int id);

	@Query(value = SINGLE_ORCHESTRATOR)
	Orchestrator singleOrchestrator(@Param("idInterpreter") long idInterpreter);

}
