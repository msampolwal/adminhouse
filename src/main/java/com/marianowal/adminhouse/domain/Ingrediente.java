package com.marianowal.adminhouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Ingrediente.
 */
@Entity
@Table(name = "ingrediente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ingrediente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Float cantidad;

    @ManyToOne
    @JsonIgnoreProperties("ingredientes")
    private Comida comida;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public Ingrediente cantidad(Float cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public Comida getComida() {
        return comida;
    }

    public Ingrediente comida(Comida comida) {
        this.comida = comida;
        return this;
    }

    public void setComida(Comida comida) {
        this.comida = comida;
    }

    public Producto getProducto() {
        return producto;
    }

    public Ingrediente producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
        Ingrediente ingrediente = (Ingrediente) o;
        if (ingrediente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingrediente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
