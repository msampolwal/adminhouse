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
 * Criteria class for the Grupo entity. This class is used in GrupoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /grupos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GrupoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter calendarioComidasId;

    private LongFilter diasId;

    private LongFilter usersId;

    public GrupoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCalendarioComidasId() {
        return calendarioComidasId;
    }

    public void setCalendarioComidasId(LongFilter calendarioComidasId) {
        this.calendarioComidasId = calendarioComidasId;
    }

    public LongFilter getDiasId() {
        return diasId;
    }

    public void setDiasId(LongFilter diasId) {
        this.diasId = diasId;
    }

    public LongFilter getUsersId() {
        return usersId;
    }

    public void setUsersId(LongFilter usersId) {
        this.usersId = usersId;
    }

    @Override
    public String toString() {
        return "GrupoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (calendarioComidasId != null ? "calendarioComidasId=" + calendarioComidasId + ", " : "") +
                (diasId != null ? "diasId=" + diasId + ", " : "") +
                (usersId != null ? "usersId=" + usersId + ", " : "") +
            "}";
    }

}
