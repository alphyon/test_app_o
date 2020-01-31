package com.elaniin.nitro.service;

import java.util.List;

import com.elaniin.nitro.dto.InterpretersDTO;
import com.elaniin.nitro.entity.Interpreters;

public interface InterpretersService {

	List<InterpretersDTO> interpretersList();

	Interpreters storeInterpretador(InterpretersDTO interpretersDTO);

	Interpreters updateInterpreters(InterpretersDTO interpretersDTO);

	boolean deleteCollector(Integer id);
	
	InterpretersDTO getInfoInterpreters(Long id);
	
	InterpretersDTO getSingleInterpreter(Long id);
	
	void deleteProxy(String uid);

}
