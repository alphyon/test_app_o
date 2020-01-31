package com.elaniin.nitro.service;

import java.util.List;

import com.elaniin.nitro.dto.CollectorDTO;
import com.elaniin.nitro.entity.Collector;

public interface ICollectorService {
	
	List<CollectorDTO> collectorList();

	Collector storeCollector(CollectorDTO collectorDTO);

	Collector updateCollector(CollectorDTO collectorDTO);
	
	boolean deleteCollector(Integer id);

}
