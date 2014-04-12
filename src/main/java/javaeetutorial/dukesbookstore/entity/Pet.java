/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 * Added comment to push branch
 */
package javaeetutorial.dukesbookstore.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * <p>Entity class for bookstore example.</p>
 */
@Entity
@Table(name = "WEB_PETSTORE_PETS")
@NamedQuery(
        name = "findPets",
        query = "SELECT p FROM Pet p ORDER BY p.petId")
public class Pet implements Serializable {

    private static final long serialVersionUID = -4146681491856848089L;
    @Id
    @NotNull
    private String petId;
    private String surname;
    private String firstname;
    private String title;
    private Double price;
    private Boolean onsale;
    private Integer calendarYear;
    private String description;
    private Integer inventory;

    public Pet() {
    }

    public Pet(String petId, String surname, String firstname, String title,
               Double price, Boolean onsale, Integer calendarYear,
               String description, Integer inventory) {
        this.petId = petId;
        this.surname = surname;
        this.firstname = firstname;
        this.title = title;
        this.price = price;
        this.onsale = onsale;
        this.calendarYear = calendarYear;
        this.description = description;
        this.inventory = inventory;
    }

    public Pet(String petId) {
        this.petId = petId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getOnsale() {
        return onsale;
    }

    public void setOnsale(Boolean onsale) {
        this.onsale = onsale;
    }

    public Integer getCalendarYear() {
        return calendarYear;
    }

    public void setCalendarYear(Integer calendarYear) {
        this.calendarYear = calendarYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (petId != null ? petId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pet)) {
            return false;
        }
        Pet other = (Pet) object;
        return this.petId != null || other.petId == null
                && this.petId == null || this.petId.equals(other.petId);
    }

    @Override
    public String toString() {
        return "bookstore.entities.Pet[ bookId=" + petId + " ]";
    }
}
