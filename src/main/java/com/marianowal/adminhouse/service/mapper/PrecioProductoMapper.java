package com.marianowal.adminhouse.service.mapper;

import com.marianowal.adminhouse.domain.*;
import com.marianowal.adminhouse.service.dto.PrecioProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PrecioProducto and its DTO PrecioProductoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, UnidadMedidaMapper.class})
public interface PrecioProductoMapper extends EntityMapper<PrecioProductoDTO, PrecioProducto> {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "unidadMedida.id", target = "unidadMedidaId")
    PrecioProductoDTO toDto(PrecioProducto precioProducto);

    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "unidadMedidaId", target = "unidadMedida")
    PrecioProducto toEntity(PrecioProductoDTO precioProductoDTO);

    default PrecioProducto fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrecioProducto precioProducto = new PrecioProducto();
        precioProducto.setId(id);
        return precioProducto;
    }
}
