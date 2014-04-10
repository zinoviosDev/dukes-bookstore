/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.dukesbookstore.web.managedbeans;

import java.io.Serializable;
import java.util.List;
import javaeetutorial.dukesbookstore.entity.Pet;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * <p>Backing bean for the <code>/bookcatalog.xhtml</code> page.</p>
 */
@Named("catalog")
@SessionScoped
public class CatalogBean extends AbstractBean implements Serializable {

    private static final long serialVersionUID = -3594317405246398714L;
    private int totalBooks = 0;

    /**
     * <p>Return the currently selected
     * <code>Pet</code> instance from the user request.</p>
     */
    protected Pet book() {
        Pet pet;
        pet = (Pet) context().getExternalContext()
           .getRequestMap().get("pet");

        return (pet);
    }

    /**
     * <p>Add the selected item to our shopping cart.</p>
     */
    public String add() {
        Pet pet = book();
        cart.add(pet.getPetId(), pet);
        message(null, "ConfirmAdd", new Object[]{pet.getTitle()});

        return ("bookcatalog");
    }

    /**
     * <p>Show the details page for the current book.</p>
     */
    public String details() {
        context().getExternalContext().getSessionMap().put("selected", book());

        return ("bookdetails");
    }

    public int getTotalBooks() {
        totalBooks = cart.getNumberOfItems();

        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    public int getBookQuantity() {
        int bookQuantity = 0;
        Pet pet = book();

        if (pet == null) {
            return bookQuantity;
        }

        List<ShoppingCartItem> results = cart.getItems();
        for (ShoppingCartItem item : results) {
            Pet bd = (Pet) item.getItem();

            if ((bd.getPetId()).equals(pet.getPetId())) {
                bookQuantity = item.getQuantity();

                break;
            }
        }

        return bookQuantity;
    }
}
