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
import java.util.logging.Level;
import java.util.logging.Logger;
import javaeetutorial.dukesbookstore.ejb.ProductRequestBean;
import javaeetutorial.dukesbookstore.entity.Product;
import javaeetutorial.dukesbookstore.exception.ProductNotFoundException;
import javaeetutorial.dukesbookstore.exception.ProductsNotFoundException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.FacesException;
import javax.inject.Named;

/**
 * <p>Backing bean for the <code>/bookstore.xhtml</code> page.</p>
 */
@Named("store")
@SessionScoped
public class PetstoreBean extends AbstractBean implements Serializable {

    private static final Logger logger =
            Logger.getLogger("dukesbookstore.web.managedBeans.PetStoreBean");
    private static final long serialVersionUID = 7829793160074383708L;
    private Product featured = null;
    protected String title;
    @EJB
    ProductRequestBean productRequestBean;

    /**
     * <p>Return the
     * <code>Product</code> for the featured product.</p>
     */
    public Product getFeatured() {
        int featuredProductPos = 4; // "The Green Project"
        if (featured == null) {
            try {
                featured = (Product) productRequestBean.getProducts().get(featuredProductPos);
            } catch (ProductsNotFoundException e) {
                // Real apps would deal with error conditions better than this
                throw new FacesException("Could not get books: " + e);
            }
        }

        return (featured);
    }

    /**
     * <p>Add the featured item to our shopping cart.</p>
     */
    public String add() {
        Product product = getFeatured();
        cart.add(product.getId(), product);
        message(null, "ConfirmAdd", new Object[]{product.getName()});

        return ("bookcatalog");
    }

    public String addSelected() {
        logger.log(Level.INFO, "Entering PetstoreBean.addSelected");
        try {
            String id = (String) context().getExternalContext().
                    getSessionMap().get("id");
            Product product = productRequestBean.getProduct(id);
            cart.add(id, product);
            message(null, "ConfirmAdd", new Object[]{product.getName()});
        } catch (ProductNotFoundException e) {
            throw new FacesException("Could not get product: " + e);
        }
        return ("bookcatalog");
    }

    /**
     * <p>Show the details page for the featured product.</p>
     */
    public String details() {
        context().getExternalContext().getSessionMap().put(
                "selected",
                getFeatured());

        return ("bookdetails");
    }

    public String selectedDetails() {
        logger.log(Level.INFO, "Entering PetstoreBean.selectedDetails");
        try {
            String id = (String) context().getExternalContext().getSessionMap().get("id");
            Product product = productRequestBean.getProduct(id);
            context().getExternalContext().getSessionMap().put("selected", product);
        } catch (ProductNotFoundException e) {
            throw new FacesException("Could not get product: " + e);
        }
        return ("bookdetails");
    }

    public String getSelectedTitle() {
        logger.log(Level.INFO, "Entering PetstoreBean.getSelectedTitle");
        try {
            String id = (String) context().getExternalContext().getSessionMap().get("id");
            Product product = productRequestBean.getProduct(id);
            title = product.getName();
        } catch (ProductNotFoundException e) {
            throw new FacesException("Could not get product title: " + e);
        }
        return title;
    }

    public List<Product> getProducts() {
        try {
            return productRequestBean.getProducts();
        } catch (ProductsNotFoundException e) {
            throw new FacesException("Exception: " + e);
        }
    }
}
