package com.marianowal.adminhouse.service.mapper;

import com.marianowal.adminhouse.domain.*;
import com.marianowal.adminhouse.service.dto.DiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dia and its DTO DiaDTO.
 */
@Mapper(componentModel = "spring", uses = {GrupoMapper.class, ItemDiaMapper.class})
public interface DiaMapper extends EntityMapper<DiaDTO, Dia> {

    @Mapping(source = "grupo.id", target = "grupoId")
    @Mapping(source = "items", target = "items")
    DiaDTO toDto(Dia dia);

    @Mapping(source = "grupoId", target = "grupo")
    @Mapping(target = "items", ignore = true)
    Dia toEntity(DiaDTO diaDTO);

    default Dia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dia dia = new Dia();
        dia.setId(id);
        return dia;
    }
}
