package com.elaniin.nitro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elaniin.nitro.dto.CollectorDTO;
import com.elaniin.nitro.entity.Categories;
import com.elaniin.nitro.entity.Channel;
import com.elaniin.nitro.entity.Collector;
import com.elaniin.nitro.repository.CategoriesRepository;
import com.elaniin.nitro.repository.ChannelRepository;
import com.elaniin.nitro.repository.CollectorRepository;
import com.elaniin.nitro.service.ICollectorService;

@Service
public class CollectorServiceImpl implements ICollectorService {

	@Autowired
	private CollectorRepository collectorRepository;

	@Autowired
	private CategoriesRepository categoriesRespository;

	@Autowired
	private ChannelRepository channelRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CollectorDTO> collectorList() {
		List<CollectorDTO> collectorDTOs = new ArrayList<>();
		for (Collector collector : collectorRepository.findAll()) {
			collectorDTOs.add(convertToDTO(collector));
		}
		return collectorDTOs;
	}

	@Override
	public Collector storeCollector(CollectorDTO collectorDTO) {
		Categories categories = categoriesRespository.findById(collectorDTO.getCategories_id()).get();
		List<Channel> list = new ArrayList<>();
		for (Channel channel : collectorDTO.getChannels()) {
			Channel obj = channelRepository.findById(channel.getId()).get();
			if (obj != null && obj.getId() != null) {
				list.add(obj);
			}
		}
		final Collector collector = new Collector(collectorDTO.getName(), collectorDTO.getPhoto(),
				collectorDTO.getOptions(), categories, list);
		return collectorRepository.save(collector);
	}

	@Override
	public Collector updateCollector(CollectorDTO collectorDTO) {
		List<Channel> list = new ArrayList<>();
		Collector collector = collectorRepository.findById(collectorDTO.getId()).get();
		if (collector != null) {
			collector.setName(collectorDTO.getName());
			collector.setOptions(collectorDTO.getOptions());
			Categories categories = categoriesRespository.findById(collectorDTO.getCategories_id()).get();
			if (categories != null) {
				collector.setCategories(categories);
			}
			collectorDTO.getChannels().forEach(channels -> {
				Channel obj = channelRepository.findById(channels.getId()).get();
				if (obj != null && obj.getId() != null) {
					list.add(obj);
				}
				collector.setChannels(list);
			});
			collectorRepository.save(collector);
		}

		return collector;
	}

	@Override
	public boolean deleteCollector(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	private CollectorDTO convertToDTO(Collector collector) {
		CollectorDTO collectorDTO = modelMapper.map(collector, CollectorDTO.class);
		collectorDTO.setCategories_id(collectorDTO.getCategories().getId());
		return collectorDTO;
	}

}
