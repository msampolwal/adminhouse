package com.marianowal.adminhouse.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marianowal.adminhouse.domain.CalendarioComida;
import com.marianowal.adminhouse.service.dto.CalendarioComidaDTO;

/**
 * Mapper for the entity CalendarioComida and its DTO CalendarioComidaDTO.
 */
@Mapper(componentModel = "spring", uses = {GrupoMapper.class, DiaMapper.class})
public interface CalendarioComidaMapper extends EntityMapper<CalendarioComidaDTO, CalendarioComida> {

    @Mapping(source = "grupo.id", target = "grupoId")
    @Mapping(source = "dia.id", target = "diaId")
    @Mapping(source = "dia", target = "dia")
    CalendarioComidaDTO toDto(CalendarioComida calendarioComida);

    @Mapping(source = "grupoId", target = "grupo")
    @Mapping(source = "diaId", target = "dia")
    CalendarioComida toEntity(CalendarioComidaDTO calendarioComidaDTO);

    default CalendarioComida fromId(Long id) {
        if (id == null) {
            return null;
        }
        CalendarioComida calendarioComida = new CalendarioComida();
        calendarioComida.setId(id);
        return calendarioComida;
    }
}
