package com.marianowal.adminhouse.service.mapper;

import com.marianowal.adminhouse.domain.*;
import com.marianowal.adminhouse.service.dto.ComidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comida and its DTO ComidaDTO.
 */
@Mapper(componentModel = "spring", uses = {IngredienteMapper.class})
public interface ComidaMapper extends EntityMapper<ComidaDTO, Comida> {

	@Mapping(source = "ingredientes", target = "ingredientes")
    ComidaDTO toDto(Comida comida);
	
    @Mapping(target = "ingredientes", ignore = true)
    Comida toEntity(ComidaDTO comidaDTO);

    default Comida fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comida comida = new Comida();
        comida.setId(id);
        return comida;
    }
}
