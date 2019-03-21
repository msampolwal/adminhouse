package com.marianowal.adminhouse.repository;

import com.marianowal.adminhouse.domain.CalendarioComida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CalendarioComida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarioComidaRepository extends JpaRepository<CalendarioComida, Long>, JpaSpecificationExecutor<CalendarioComida> {

}
