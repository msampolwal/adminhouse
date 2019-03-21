package com.marianowal.adminhouse.service.mapper;

import com.marianowal.adminhouse.domain.*;
import com.marianowal.adminhouse.service.dto.GrupoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Grupo and its DTO GrupoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface GrupoMapper extends EntityMapper<GrupoDTO, Grupo> {


    @Mapping(target = "calendarioComidas", ignore = true)
    @Mapping(target = "dias", ignore = true)
    Grupo toEntity(GrupoDTO grupoDTO);

    default Grupo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Grupo grupo = new Grupo();
        grupo.setId(id);
        return grupo;
    }
}
