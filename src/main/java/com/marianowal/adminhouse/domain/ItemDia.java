package com.marianowal.adminhouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.marianowal.adminhouse.domain.enumeration.TipoComida;

/**
 * A ItemDia.
 */
@Entity
@Table(name = "item_dia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemDia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoComida tipo;

    @ManyToOne
    @JsonIgnoreProperties("items")
    private Dia dia;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Comida comida;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoComida getTipo() {
        return tipo;
    }

    public ItemDia tipo(TipoComida tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoComida tipo) {
        this.tipo = tipo;
    }

    public Dia getDia() {
        return dia;
    }

    public ItemDia dia(Dia dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Comida getComida() {
        return comida;
    }

    public ItemDia comida(Comida comida) {
        this.comida = comida;
        return this;
    }

    public void setComida(Comida comida) {
        this.comida = comida;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemDia itemDia = (ItemDia) o;
        if (itemDia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemDia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return getTipo().name() + ": " + getComida().getNombre();
    }
}
