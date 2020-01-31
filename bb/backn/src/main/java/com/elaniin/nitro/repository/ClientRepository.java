package com.elaniin.nitro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elaniin.nitro.dto.ClientDTO;
import com.elaniin.nitro.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
	
	static String SEARCH_CLIENT_BY_CODE = "select c from Client c where c.clientCode = :clientCode";
	
	
	@Query(value = SEARCH_CLIENT_BY_CODE)
	Client getClientByCode(@Param("clientCode") String code);
	
	@Query(nativeQuery = true)
	ClientDTO getInvoiceByClient(@Param("codeClient") String codeClient, @Param("paramDate") String paramDate);

}
