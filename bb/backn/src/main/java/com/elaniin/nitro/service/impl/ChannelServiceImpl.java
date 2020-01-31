package com.elaniin.nitro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elaniin.nitro.dto.ChannelDTO;
import com.elaniin.nitro.entity.Channel;
import com.elaniin.nitro.repository.ChannelRepository;
import com.elaniin.nitro.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelRepository channelRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ChannelDTO> channelList() {
		List<ChannelDTO> categoriesList = new ArrayList<>();
		for (Channel channel : channelRepository.findAll()) {
			categoriesList.add(convertToDTO(channel));
		}
		return categoriesList;
	}

	@Override
	public Channel storeChannel(ChannelDTO channelDTO) {
		final Channel channel = new Channel(channelDTO.getName());
		return channelRepository.save(channel);
	}

	@Override
	public Channel updateChannels(ChannelDTO channelDTO) {
		Channel channel = channelRepository.findById(channelDTO.getId()).get();
		if (channel != null) {
			channel.setName(channelDTO.getName());
			channelRepository.save(channel);
		}
		return channel;
	}

	@Override
	public boolean deleteChannel(Integer id) {
		boolean existCategory = channelRepository.existsById(id.longValue());
		if (existCategory) {
			channelRepository.deleteById(id.longValue());
		}
		return existCategory;
	}

	private ChannelDTO convertToDTO(Channel channel) {
		ChannelDTO channelDTO = modelMapper.map(channel, ChannelDTO.class);
		return channelDTO;
	}

}
