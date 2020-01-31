package com.elaniin.nitro.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elaniin.nitro.entity.Proxy;

@Repository
@Transactional
public interface ProxyRepository extends JpaRepository<Proxy, Long> {

	static String GET_LAST_RECORD = "select p from Proxy p where p.interpreters.id = :id ";
	static String DELETE_PROXY_BY_CODE = "delete from Proxy p where p.code  = :code ";

	public Proxy findByName(String name);

	public List<Proxy> findAllByIdCollector(int id);

	@Query(value = GET_LAST_RECORD)
	public Page<Proxy> getProxyByIdPageable(@Param("id") long id, Pageable pageable);

	@Modifying
	@Query(value = DELETE_PROXY_BY_CODE)
	void deleteProxyByCode(@Param("code") String code);

}
