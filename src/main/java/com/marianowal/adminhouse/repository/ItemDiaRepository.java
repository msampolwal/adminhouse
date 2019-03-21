package com.marianowal.adminhouse.repository;

import com.marianowal.adminhouse.domain.ItemDia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemDia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemDiaRepository extends JpaRepository<ItemDia, Long>, JpaSpecificationExecutor<ItemDia> {

}
