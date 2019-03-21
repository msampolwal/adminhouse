package com.marianowal.adminhouse.service.mapper;

import com.marianowal.adminhouse.domain.*;
import com.marianowal.adminhouse.service.dto.ItemDiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItemDia and its DTO ItemDiaDTO.
 */
@Mapper(componentModel = "spring", uses = {DiaMapper.class, ComidaMapper.class})
public interface ItemDiaMapper extends EntityMapper<ItemDiaDTO, ItemDia> {

    @Mapping(source = "dia.id", target = "diaId")
    @Mapping(source = "comida.id", target = "comidaId")
    @Mapping(source = "comida.nombre", target = "comidaNombre")
    ItemDiaDTO toDto(ItemDia itemDia);

    @Mapping(source = "diaId", target = "dia")
    @Mapping(source = "comidaId", target = "comida")
    ItemDia toEntity(ItemDiaDTO itemDiaDTO);

    default ItemDia fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemDia itemDia = new ItemDia();
        itemDia.setId(id);
        return itemDia;
    }
}
