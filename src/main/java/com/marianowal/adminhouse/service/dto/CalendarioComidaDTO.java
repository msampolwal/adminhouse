package com.marianowal.adminhouse.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the CalendarioComida entity.
 */
public class CalendarioComidaDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fecha;

    private Long grupoId;

    private Long diaId;
    
    private DiaDTO dia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public Long getDiaId() {
        return diaId;
    }

    public void setDiaId(Long diaId) {
        this.diaId = diaId;
    }

	public DiaDTO getDia() {
		return dia;
	}

	public void setDia(DiaDTO dia) {
		this.dia = dia;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CalendarioComidaDTO calendarioComidaDTO = (CalendarioComidaDTO) o;
        if (calendarioComidaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calendarioComidaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CalendarioComidaDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", grupo=" + getGrupoId() +
            ", dia=" + getDiaId() +
            "}";
    }
}
