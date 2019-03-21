package com.marianowal.adminhouse.service.mapper;

import com.marianowal.adminhouse.domain.*;
import com.marianowal.adminhouse.service.dto.ProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Producto and its DTO ProductoDTO.
 */
@Mapper(componentModel = "spring", uses = {UnidadMedidaMapper.class})
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {

    @Mapping(source = "unidadMedida.id", target = "unidadMedidaId")
    @Mapping(source = "unidadMedida.nombre", target = "unidadMedidaNombre")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "unidadMedidaId", target = "unidadMedida")
    Producto toEntity(ProductoDTO productoDTO);

    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
