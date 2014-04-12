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
import javaeetutorial.dukesbookstore.entity.Product;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * <p>Backing bean for the <code>/productcatalog.xhtml</code> page.</p>
 */
@Named("catalog")
@SessionScoped
public class CatalogBean extends AbstractBean implements Serializable {

    private static final long serialVersionUID = -3594317405246398714L;
    private int totalProducts = 0;

    /**
     * <p>Return the currently selected
     * <code>Product</code> instance from the user request.</p>
     */
    protected Product product() {
        Product product;
        product = (Product) context().getExternalContext()
           .getRequestMap().get("product");

        return (product);
    }

    /**
     * <p>Add the selected item to our shopping cart.</p>
     */
    public String add() {
        Product product = product();
        cart.add(product.getId(), product);
        message(null, "ConfirmAdd", new Object[]{product.getName()});

        return ("productcatalog");
    }

    /**
     * <p>Show the details page for the current product.</p>
     */
    public String details() {
        context().getExternalContext().getSessionMap().put("selected", product());

        return ("productdetails");
    }

    public int getTotalProducts() {
        totalProducts = cart.getNumberOfItems();

        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getProductQuantity() {
        int productQuantity = 0;
        Product product = product();

        if (product == null) {
            return productQuantity;
        }

        List<ShoppingCartItem> results = cart.getItems();
        for (ShoppingCartItem item : results) {
            Product bd = (Product) item.getItem();

            if ((bd.getId()).equals(product.getId())) {
                productQuantity = item.getQuantity();

                break;
            }
        }

        return productQuantity;
    }
}
