package com.marianowal.adminhouse.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.marianowal.adminhouse.domain.enumeration.DiaSemana;

/**
 * A DTO for the Dia entity.
 */
public class DiaDTO implements Serializable {

    private Long id;

    private DiaSemana diaSemana;

    private Long grupoId;
    
    private Set<ItemDiaDTO> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public Set<ItemDiaDTO> getItems() {
		return items;
	}

	public void setItems(Set<ItemDiaDTO> items) {
		this.items = items;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiaDTO diaDTO = (DiaDTO) o;
        if (diaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiaDTO{" +
            "id=" + getId() +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", grupo=" + getGrupoId() +
            "}";
    }
}
