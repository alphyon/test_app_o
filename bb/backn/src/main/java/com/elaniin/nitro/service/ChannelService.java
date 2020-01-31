package com.elaniin.nitro.service;

import java.util.List;

import com.elaniin.nitro.dto.ChannelDTO;
import com.elaniin.nitro.entity.Channel;

public interface ChannelService {

	List<ChannelDTO> channelList();

	Channel storeChannel(ChannelDTO channelDTO);

	Channel updateChannels(ChannelDTO channelDTO);

	boolean deleteChannel(Integer id);

}
