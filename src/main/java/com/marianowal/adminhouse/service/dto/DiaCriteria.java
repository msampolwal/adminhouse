package com.marianowal.adminhouse.service.dto;

import java.io.Serializable;
import com.marianowal.adminhouse.domain.enumeration.DiaSemana;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Dia entity. This class is used in DiaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /dias?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DiaCriteria implements Serializable {
    /**
     * Class for filtering DiaSemana
     */
    public static class DiaSemanaFilter extends Filter<DiaSemana> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private DiaSemanaFilter diaSemana;

    private LongFilter grupoId;

    private LongFilter itemsId;

    public DiaCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DiaSemanaFilter getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemanaFilter diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LongFilter getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(LongFilter grupoId) {
        this.grupoId = grupoId;
    }

    public LongFilter getItemsId() {
        return itemsId;
    }

    public void setItemsId(LongFilter itemsId) {
        this.itemsId = itemsId;
    }

    @Override
    public String toString() {
        return "DiaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (diaSemana != null ? "diaSemana=" + diaSemana + ", " : "") +
                (grupoId != null ? "grupoId=" + grupoId + ", " : "") +
                (itemsId != null ? "itemsId=" + itemsId + ", " : "") +
            "}";
    }

}
