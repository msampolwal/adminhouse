package com.marianowal.adminhouse.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Ingrediente entity. This class is used in IngredienteResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /ingredientes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IngredienteCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private FloatFilter cantidad;

    private LongFilter comidaId;

    private LongFilter productoId;

    public IngredienteCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getCantidad() {
        return cantidad;
    }

    public void setCantidad(FloatFilter cantidad) {
        this.cantidad = cantidad;
    }

    public LongFilter getComidaId() {
        return comidaId;
    }

    public void setComidaId(LongFilter comidaId) {
        this.comidaId = comidaId;
    }

    public LongFilter getProductoId() {
        return productoId;
    }

    public void setProductoId(LongFilter productoId) {
        this.productoId = productoId;
    }

    @Override
    public String toString() {
        return "IngredienteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cantidad != null ? "cantidad=" + cantidad + ", " : "") +
                (comidaId != null ? "comidaId=" + comidaId + ", " : "") +
                (productoId != null ? "productoId=" + productoId + ", " : "") +
            "}";
    }

}
