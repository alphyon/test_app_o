package com.elaniin.nitro.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.elaniin.nitro.dto.ChannelDTO;
import com.elaniin.nitro.entity.Channel;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.ChannelService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CRUD CHANNELS", description = "CRUD for manage all Channel for Collector")
@RestController
@RequestMapping("/api/v1/channel/")
public class ChannelController extends GenericBase {

	public static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

	@Autowired
	private ChannelService iChannelService;

	@ApiOperation(value = "List all Channels availables for an Collector")
	@GetMapping("channelList")
	public ResponseEntity<Object> channelList(WebRequest request) throws JsonProcessingException {
		List<ChannelDTO> channelDTOs = iChannelService.channelList();
		if (channelDTOs.isEmpty()) {
			throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCESS_LIST, request, channelDTOs);
	}

	@ApiOperation(value = "Function for store one channel")
	@PostMapping("store")
	public ResponseEntity<Object> storeChannel(@RequestBody ChannelDTO channelDTO, WebRequest request)
			throws JsonProcessingException {
		Channel channel;
		try {
			channel = iChannelService.storeChannel(channelDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, channel);
	}

	@ApiOperation(value = "Function for updated one channel")
	@PutMapping("update/{id}")
	public ResponseEntity<Object> updateCategories(@RequestBody ChannelDTO channelDTO,
			@ApiParam(value = "Channel ID from which channel will be updated from database", required = true, example = "1") @PathVariable Integer id,
			WebRequest request) throws JsonProcessingException {
		Channel channel;
		try {
			channel = iChannelService.updateChannels(channelDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.UPDATE_MESSAGE, request, channel);
	}

	@ApiOperation(value = "Function for delete one channel")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteCategory(
			@ApiParam(value = "Channel ID from which channel will be deleted from database", required = true, example = "1") @PathVariable Integer id,
			WebRequest request) throws JsonProcessingException {
		boolean deleted = false;
		deleted = iChannelService.deleteChannel(id.intValue());
		if (!deleted) {
			throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
		}
		return genericCustomMessageResponse(ConstantMessages.DELETE_MESSAGE, request, deleted);
	}

}
