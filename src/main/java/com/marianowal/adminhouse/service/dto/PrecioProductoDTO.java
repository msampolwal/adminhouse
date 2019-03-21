package com.marianowal.adminhouse.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PrecioProducto entity.
 */
public class PrecioProductoDTO implements Serializable {

    private Long id;

    @NotNull
    private Float precio;

    @NotNull
    private Float cantidad;

    private Long productoId;

    private Long unidadMedidaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(Long unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrecioProductoDTO precioProductoDTO = (PrecioProductoDTO) o;
        if (precioProductoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), precioProductoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrecioProductoDTO{" +
            "id=" + getId() +
            ", precio=" + getPrecio() +
            ", cantidad=" + getCantidad() +
            ", producto=" + getProductoId() +
            ", unidadMedida=" + getUnidadMedidaId() +
            "}";
    }
}
