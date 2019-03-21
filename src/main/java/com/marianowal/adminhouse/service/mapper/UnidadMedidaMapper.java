package com.marianowal.adminhouse.service.mapper;

import com.marianowal.adminhouse.domain.*;
import com.marianowal.adminhouse.service.dto.UnidadMedidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UnidadMedida and its DTO UnidadMedidaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnidadMedidaMapper extends EntityMapper<UnidadMedidaDTO, UnidadMedida> {



    default UnidadMedida fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadMedida unidadMedida = new UnidadMedida();
        unidadMedida.setId(id);
        return unidadMedida;
    }
}
