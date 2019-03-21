package com.marianowal.adminhouse.service.mapper;

import com.marianowal.adminhouse.domain.*;
import com.marianowal.adminhouse.service.dto.IngredienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ingrediente and its DTO IngredienteDTO.
 */
@Mapper(componentModel = "spring", uses = {ComidaMapper.class, ProductoMapper.class})
public interface IngredienteMapper extends EntityMapper<IngredienteDTO, Ingrediente> {

    @Mapping(source = "comida.id", target = "comidaId")
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "productoNombre")
    IngredienteDTO toDto(Ingrediente ingrediente);

    @Mapping(source = "comidaId", target = "comida")
    @Mapping(source = "productoId", target = "producto")
    Ingrediente toEntity(IngredienteDTO ingredienteDTO);

    default Ingrediente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(id);
        return ingrediente;
    }
}
