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
 * Criteria class for the PrecioProducto entity. This class is used in PrecioProductoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /precio-productos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrecioProductoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private FloatFilter precio;

    private FloatFilter cantidad;

    private LongFilter productoId;

    private LongFilter unidadMedidaId;

    public PrecioProductoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getPrecio() {
        return precio;
    }

    public void setPrecio(FloatFilter precio) {
        this.precio = precio;
    }

    public FloatFilter getCantidad() {
        return cantidad;
    }

    public void setCantidad(FloatFilter cantidad) {
        this.cantidad = cantidad;
    }

    public LongFilter getProductoId() {
        return productoId;
    }

    public void setProductoId(LongFilter productoId) {
        this.productoId = productoId;
    }

    public LongFilter getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(LongFilter unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    @Override
    public String toString() {
        return "PrecioProductoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (precio != null ? "precio=" + precio + ", " : "") +
                (cantidad != null ? "cantidad=" + cantidad + ", " : "") +
                (productoId != null ? "productoId=" + productoId + ", " : "") +
                (unidadMedidaId != null ? "unidadMedidaId=" + unidadMedidaId + ", " : "") +
            "}";
    }

}
