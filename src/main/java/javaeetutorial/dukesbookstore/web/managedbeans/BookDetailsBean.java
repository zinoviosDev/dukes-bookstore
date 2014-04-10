/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.dukesbookstore.web.managedbeans;

import java.io.Serializable;
import javaeetutorial.dukesbookstore.entity.Pet;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * <p>Backing bean for the <code>/bookdetails.xhtml</code> page.</p>
 */
@Named("details")
@SessionScoped
public class BookDetailsBean extends AbstractBean implements Serializable {

    private static final long serialVersionUID = 2209748452115843974L;

    /**
     * <p>Add the displayed item to our shopping cart.</p>
     */
    public String add() {
        Pet pet = (Pet) context().getExternalContext()
                .getSessionMap().get("selected");
        cart.add(pet.getPetId(), pet);
        message(null, "ConfirmAdd", new Object[]{pet.getTitle()});

        return ("bookcatalog");
    }
}
