/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.martinspetstore.web.managedbeans;

import java.io.Serializable;
import javaeetutorial.martinspetstore.entity.Product;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * <p>Backing bean for the <code>/productdetails.xhtml</code> page.</p>
 */
@Named("details")
@SessionScoped
public class ProductDetailsBean extends AbstractBean implements Serializable {

    private static final long serialVersionUID = 2209748452115843974L;

    /**
     * <p>Add the displayed item to our shopping cart.</p>
     */
    public String add() {
        Product product = (Product) context().getExternalContext()
                .getSessionMap().get("selected");
        cart.add(product.getId(), product);
        message(null, "ConfirmAdd", new Object[]{product.getName()});

        return ("productcatalog");
    }
}
