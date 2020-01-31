package com.elaniin.nitro.service;

import com.elaniin.nitro.dto.ProxyDTO;
import com.elaniin.nitro.entity.Proxy;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

public interface ProxyService {
    List<ProxyDTO> listAllProxiesByCollector(int id);
    Proxy saveProxy(ProxyDTO proxyDTO);
    Proxy updateProxy(ProxyDTO proxyDTO);
    boolean deleteProxy(Long id);
    Proxy getLastProxyRecord();
    Proxy getProxyByIdPageable(long id);
}
