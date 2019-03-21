package com.marianowal.adminhouse.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Producto entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private Long unidadMedidaId;
    
    private String unidadMedidaNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(Long unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    public String getUnidadMedidaNombre() {
		return unidadMedidaNombre;
	}

	public void setUnidadMedidaNombre(String unidadMedidaNombre) {
		this.unidadMedidaNombre = unidadMedidaNombre;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (productoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", unidadMedida=" + getUnidadMedidaId() +
            "}";
    }
}
