package com.elaniin.nitro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elaniin.nitro.dto.InterpretersDTO;
import com.elaniin.nitro.entity.Collector;
import com.elaniin.nitro.entity.Interpreters;
import com.elaniin.nitro.repository.CollectorRepository;
import com.elaniin.nitro.repository.InterpretersRepository;
import com.elaniin.nitro.repository.ProxyRepository;
import com.elaniin.nitro.service.InterpretersService;

@Service
public class InterpretersServiceImpl implements InterpretersService {

	@Autowired
	private InterpretersRepository interpretersRepository;
	
	@Autowired
	private CollectorRepository collectorRepository;
	
	@Autowired
	private ProxyRepository proxyRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<InterpretersDTO> interpretersList() {
		List<InterpretersDTO> interpretersList = new ArrayList<>();
		for (Interpreters interpreters : interpretersRepository.findAll()) {
			interpretersList.add(convertToDTO(interpreters));
		}
		return interpretersList;
	}

	@Override
	public Interpreters storeInterpretador(InterpretersDTO interpretersDTO) {
		Interpreters interpreters = null;
		Collector collector = collectorRepository.findById(interpretersDTO.getIdCollector()).get();
		if (collector != null && collector.getId() != null) {
			interpreters = new Interpreters(interpretersDTO.getName(), interpretersDTO.getDescription(), collector);
		}
		return interpretersRepository.save(interpreters);
	}

	@Override
	public Interpreters updateInterpreters(InterpretersDTO interpretersDTO) {
		Interpreters interpreters = interpretersRepository.findById(interpretersDTO.getId()).get();
		if (interpreters != null && interpreters.getId() != null) {
			interpreters.setName(interpretersDTO.getName());
			interpreters.setDescription(interpretersDTO.getDescription());
			
			Collector collector = collectorRepository.findById(interpretersDTO.getId()).get();
			if (collector != null && collector.getId() != null) {
				interpreters.setCollector(collector);
			}
		}
		return interpretersRepository.save(interpreters);
	}

	@Override
	public boolean deleteCollector(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	private InterpretersDTO convertToDTO(Interpreters interpreters) {
		InterpretersDTO interpretersDTO = modelMapper.map(interpreters, InterpretersDTO.class);
		return interpretersDTO;
	}

	@Override
	public InterpretersDTO getInfoInterpreters(Long id) {
		InterpretersDTO interpretersDTO = convertToDTO(interpretersRepository.findById(id).get());
		return interpretersDTO;
	}

	@Override
	public InterpretersDTO getSingleInterpreter(Long id) {
		Interpreters interpreters = interpretersRepository.findById(id).get();
		InterpretersDTO interpretersDTO = null;
		if (interpreters != null) {
			interpretersDTO = convertToDTO(interpreters);
		}
		return interpretersDTO;
	}

	@Override
	public void deleteProxy(String uid) {
		proxyRepository.deleteProxyByCode(uid);
	}

}
