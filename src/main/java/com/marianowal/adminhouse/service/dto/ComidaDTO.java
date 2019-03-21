package com.marianowal.adminhouse.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Comida entity.
 */
public class ComidaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;
    
    private Set<IngredienteDTO> ingredientes;

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

    public Set<IngredienteDTO> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Set<IngredienteDTO> ingredientes) {
		this.ingredientes = ingredientes;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComidaDTO comidaDTO = (ComidaDTO) o;
        if (comidaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comidaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComidaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
