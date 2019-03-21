package com.marianowal.adminhouse.service.dto;

import java.io.Serializable;
import com.marianowal.adminhouse.domain.enumeration.TipoComida;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the ItemDia entity. This class is used in ItemDiaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /item-dias?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemDiaCriteria implements Serializable {
    /**
     * Class for filtering TipoComida
     */
    public static class TipoComidaFilter extends Filter<TipoComida> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private TipoComidaFilter tipo;

    private LongFilter diaId;

    private LongFilter comidaId;

    public ItemDiaCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TipoComidaFilter getTipo() {
        return tipo;
    }

    public void setTipo(TipoComidaFilter tipo) {
        this.tipo = tipo;
    }

    public LongFilter getDiaId() {
        return diaId;
    }

    public void setDiaId(LongFilter diaId) {
        this.diaId = diaId;
    }

    public LongFilter getComidaId() {
        return comidaId;
    }

    public void setComidaId(LongFilter comidaId) {
        this.comidaId = comidaId;
    }

    @Override
    public String toString() {
        return "ItemDiaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (diaId != null ? "diaId=" + diaId + ", " : "") +
                (comidaId != null ? "comidaId=" + comidaId + ", " : "") +
            "}";
    }

}
