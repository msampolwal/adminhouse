package com.marianowal.adminhouse.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Ingrediente entity.
 */
public class IngredienteDTO implements Serializable {

    private Long id;

    @NotNull
    private Float cantidad;

    private Long comidaId;

    private Long productoId;
    
    private String productoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public Long getComidaId() {
        return comidaId;
    }

    public void setComidaId(Long comidaId) {
        this.comidaId = comidaId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
    
    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IngredienteDTO ingredienteDTO = (IngredienteDTO) o;
        if (ingredienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredienteDTO{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", comida=" + getComidaId() +
            ", producto=" + getProductoId() +
            "}";
    }
}
