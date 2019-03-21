package com.marianowal.adminhouse.repository;

import com.marianowal.adminhouse.domain.PrecioProducto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrecioProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrecioProductoRepository extends JpaRepository<PrecioProducto, Long>, JpaSpecificationExecutor<PrecioProducto> {

}
