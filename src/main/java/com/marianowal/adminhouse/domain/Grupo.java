package com.marianowal.adminhouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Grupo.
 */
@Entity
@Table(name = "grupo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "grupo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CalendarioComida> calendarioComidas = new HashSet<>();

    @OneToMany(mappedBy = "grupo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dia> dias = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "grupo_users",
               joinColumns = @JoinColumn(name = "grupos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<CalendarioComida> getCalendarioComidas() {
        return calendarioComidas;
    }

    public Grupo calendarioComidas(Set<CalendarioComida> calendarioComidas) {
        this.calendarioComidas = calendarioComidas;
        return this;
    }

    public Grupo addCalendarioComidas(CalendarioComida calendarioComida) {
        this.calendarioComidas.add(calendarioComida);
        calendarioComida.setGrupo(this);
        return this;
    }

    public Grupo removeCalendarioComidas(CalendarioComida calendarioComida) {
        this.calendarioComidas.remove(calendarioComida);
        calendarioComida.setGrupo(null);
        return this;
    }

    public void setCalendarioComidas(Set<CalendarioComida> calendarioComidas) {
        this.calendarioComidas = calendarioComidas;
    }

    public Set<Dia> getDias() {
        return dias;
    }

    public Grupo dias(Set<Dia> dias) {
        this.dias = dias;
        return this;
    }

    public Grupo addDias(Dia dia) {
        this.dias.add(dia);
        dia.setGrupo(this);
        return this;
    }

    public Grupo removeDias(Dia dia) {
        this.dias.remove(dia);
        dia.setGrupo(null);
        return this;
    }

    public void setDias(Set<Dia> dias) {
        this.dias = dias;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Grupo users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Grupo addUsers(User user) {
        this.users.add(user);
        return this;
    }

    public Grupo removeUsers(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
        Grupo grupo = (Grupo) o;
        if (grupo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grupo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Grupo{" +
            "id=" + getId() +
            "}";
    }
}
