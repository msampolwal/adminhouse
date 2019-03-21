package com.marianowal.adminhouse.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marianowal.adminhouse.domain.enumeration.DiaSemana;

/**
 * A Dia.
 */
@Entity
@Table(name = "dia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @ManyToOne
    @JsonIgnoreProperties("dias")
    private Grupo grupo;

    @OneToMany(mappedBy = "dia", cascade = { CascadeType.ALL })
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemDia> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public Dia diaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Dia grupo(Grupo grupo) {
        this.grupo = grupo;
        return this;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Set<ItemDia> getItems() {
        return items;
    }

    public Dia items(Set<ItemDia> itemDias) {
        this.items = itemDias;
        return this;
    }

    public Dia addItems(ItemDia itemDia) {
        this.items.add(itemDia);
        itemDia.setDia(this);
        return this;
    }

    public Dia removeItems(ItemDia itemDia) {
        this.items.remove(itemDia);
        itemDia.setDia(null);
        return this;
    }

    public void setItems(Set<ItemDia> itemDias) {
        this.items = itemDias;
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
        Dia dia = (Dia) o;
        if (dia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
    		String detalle = "";
    		Iterator<ItemDia> itemsDia = getItems().iterator();
    		while (itemsDia.hasNext()) {
			ItemDia itemDia = (ItemDia) itemsDia.next();
			detalle += itemDia.toString();
			if(itemsDia.hasNext()) 
				detalle += ", ";
		}
    		return detalle;
    }
    
    public List<String> getComidasDetalle() {
    		List<String> comidasDetalle = new ArrayList<String>();
    		for (ItemDia itemDia : items) {
			comidasDetalle.add(itemDia.toString());	
		}
    		return comidasDetalle;
    }
}
