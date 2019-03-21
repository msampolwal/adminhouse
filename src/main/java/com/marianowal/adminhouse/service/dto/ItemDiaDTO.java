package com.marianowal.adminhouse.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.marianowal.adminhouse.domain.enumeration.TipoComida;

/**
 * A DTO for the ItemDia entity.
 */
public class ItemDiaDTO implements Serializable {

    private Long id;

    private TipoComida tipo;

    private Long diaId;

    private Long comidaId;
    
    private String comidaNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoComida getTipo() {
        return tipo;
    }

    public void setTipo(TipoComida tipo) {
        this.tipo = tipo;
    }

    public Long getDiaId() {
        return diaId;
    }

    public void setDiaId(Long diaId) {
        this.diaId = diaId;
    }

    public Long getComidaId() {
        return comidaId;
    }

    public void setComidaId(Long comidaId) {
        this.comidaId = comidaId;
    }

    public String getComidaNombre() {
		return comidaNombre;
	}

	public void setComidaNombre(String comidaNombre) {
		this.comidaNombre = comidaNombre;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemDiaDTO itemDiaDTO = (ItemDiaDTO) o;
        if (itemDiaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemDiaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemDiaDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", dia=" + getDiaId() +
            ", comida=" + getComidaId() +
            "}";
    }
}
