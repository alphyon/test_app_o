package com.elaniin.nitro.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.elaniin.nitro.dto.ProxyDTO;
import com.elaniin.nitro.entity.Proxy;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.ProxyService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/proxy")
public class ProxyIntegratorController extends GenericBase {

    @Autowired
    private ProxyService proxyService;

    public static final Logger logger = LoggerFactory.getLogger(ProxyIntegratorController.class);
    
    @GetMapping("listbycollector/{id}")
    public ResponseEntity<Object> listByCollectorId(@PathVariable(name = "id") int id, WebRequest request) throws JsonProcessingException {
        List<ProxyDTO> proxyDTOList = proxyService.listAllProxiesByCollector(id);
        if(proxyDTOList.isEmpty()){
            throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
        }
        try {
            return genericCustomMessageResponse(ConstantMessages.SUCESS_LIST,request,proxyDTOList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return genericCustomMessageResponse(ConstantMessages.EMPTY_DATA,request,new ArrayList<>());
        }
    }

    @PostMapping("store")
    public ResponseEntity<Object> saveProxy(@RequestBody ProxyDTO proxyDTO, WebRequest request) throws JsonProcessingException {
        try {
            Proxy proxy;
            proxy = proxyService.saveProxy(proxyDTO);
            return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE,request,proxy);
        }catch (Exception e){
            logger.error(e.getMessage());
            return  genericCustomMessageResponse(ConstantMessages.SERVER_ERROR,request, new ProxyDTO());
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Object> updateProxy(@RequestBody ProxyDTO proxyDTO, @PathVariable Integer id, WebRequest request) throws JsonProcessingException {

        try {
            Proxy proxy;
            proxy = proxyService.updateProxy(proxyDTO);
            return genericCustomMessageResponse(ConstantMessages.UPDATE_MESSAGE, request, proxy);
        } catch (Exception e) {
            logger.error(e.getMessage());
           return genericCustomMessageResponse(ConstantMessages.WARNING_MESSAGE,request,new ProxyDTO());
        }
    }

}
