package com.marianowal.adminhouse.repository;

import com.marianowal.adminhouse.domain.Comida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Comida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComidaRepository extends JpaRepository<Comida, Long>, JpaSpecificationExecutor<Comida> {

}
