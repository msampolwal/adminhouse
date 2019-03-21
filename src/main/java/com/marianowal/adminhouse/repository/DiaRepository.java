package com.marianowal.adminhouse.repository;

import com.marianowal.adminhouse.domain.Dia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiaRepository extends JpaRepository<Dia, Long>, JpaSpecificationExecutor<Dia> {

}
