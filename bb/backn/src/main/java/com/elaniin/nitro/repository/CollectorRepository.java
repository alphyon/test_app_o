package com.elaniin.nitro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elaniin.nitro.entity.Collector;

@Repository
public interface CollectorRepository extends JpaRepository<Collector, Long> {

}
