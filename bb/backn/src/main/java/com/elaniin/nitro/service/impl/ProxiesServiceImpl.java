package com.elaniin.nitro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.elaniin.nitro.dto.ProxyDTO;
import com.elaniin.nitro.entity.Interpreters;
import com.elaniin.nitro.entity.Proxy;
import com.elaniin.nitro.repository.InterpretersRepository;
import com.elaniin.nitro.repository.ProxyRepository;
import com.elaniin.nitro.service.ProxyService;

@Service
public class ProxiesServiceImpl  implements ProxyService {

	@Autowired
	private ProxyRepository proxyRepository;

	@Autowired
	private InterpretersRepository interpreterRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ProxyDTO> listAllProxiesByCollector(int id) {
		List<ProxyDTO> proxyDTOS = new ArrayList<ProxyDTO>();
		for (Proxy proxy : proxyRepository.findAllByIdCollector(id)) {
			proxyDTOS.add(convertToDTO(proxy));
		}
		return proxyDTOS;
	}

	@Override
	public Proxy saveProxy(ProxyDTO proxyDTO) {
		Proxy proxy = null;
		Interpreters interpreters = interpreterRepository.findById(proxyDTO.getInterpreterId()).get();
		if (interpreters != null) {
			proxy = new Proxy(proxyDTO.getName(), proxyDTO.getCode(), proxyDTO.getUrl(), proxyDTO.getInputParameters(),
					proxyDTO.getOutParameters(), proxyDTO.getIdCollector(), interpreters, proxyDTO.getStep(), proxyDTO.getMethod());
		}
		return proxyRepository.save(proxy);
	}

	@Override
	public Proxy updateProxy(ProxyDTO proxyDTO) {
		Proxy response = new Proxy();
		try {
			Optional<Proxy> proxy = proxyRepository.findById(proxyDTO.getId());
			if (proxy == null) {
				throw new Exception("NOT FOUND ELEMENT");
			}
			;
			response = proxyRepository.save(convertToPoxy(proxyDTO));
		} catch (Exception e) {
			e.printStackTrace();
			response = convertToPoxy(proxyDTO);
		}
		return response;
	}

	@Override
	public boolean deleteProxy(Long id) {
		return false;
	}


	private ProxyDTO convertToDTO(Proxy proxy) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return modelMapper.map(proxy, ProxyDTO.class);
	}

	private Proxy convertToPoxy(ProxyDTO dto) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return modelMapper.map(dto, Proxy.class);
	}

	@Override
	public Proxy getLastProxyRecord() {
		Page<Proxy> page = proxyRepository.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")));
		System.err.println(page.getContent());
		if (page.getContent().size() > 0) {
			return page.getContent().get(0);
		}
		return null;
	}

	@Override
	public Proxy getProxyByIdPageable(long id) {
		Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"));
		Page<Proxy> proxies = proxyRepository.getProxyByIdPageable(id, pageable);
		if (proxies.getContent().size() > 0) {
			return proxies.getContent().get(0);
		}
		return null;
	}

}
