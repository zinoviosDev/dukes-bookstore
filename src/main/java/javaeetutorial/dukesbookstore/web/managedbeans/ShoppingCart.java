/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.dukesbookstore.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaeetutorial.dukesbookstore.entity.Product;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * <p>Backing bean used by the
 * <code>/productcatalog.xhtml</code>,
 * <code>/productshowcart.xhtml</code>, and
 * <code>/productcashier.xhtml</code> pages.</p>
 */
@Named("cart")
@SessionScoped
public class ShoppingCart extends AbstractBean implements Serializable {

    private static final Logger logger =
            Logger.getLogger("dukesbookstore.web.managedbeans.ShoppingCart");
    private static final long serialVersionUID = -115105724952554868L;
    HashMap<String, ShoppingCartItem> items = null;
    int numberOfItems = 0;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    public synchronized void add(String id, Product product) {
        if (items.containsKey(id)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(id);
            scitem.incrementQuantity();
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(product);
            items.put(id, newItem);
        }
    }

    public synchronized void remove(String bookId) {
        if (items.containsKey(bookId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(bookId);
            scitem.decrementQuantity();

            if (scitem.getQuantity() <= 0) {
                items.remove(bookId);
            }

            numberOfItems--;
        }
    }

    public synchronized List<ShoppingCartItem> getItems() {
        List<ShoppingCartItem> results = new ArrayList<>();
        results.addAll(this.items.values());

        return results;
    }

    public synchronized int getNumberOfItems() {
        numberOfItems = 0;
        for (ShoppingCartItem item : getItems()) {
            numberOfItems += item.getQuantity();
        }

        return numberOfItems;
    }

    public synchronized double getTotal() {
        double amount = 0.0;
        for (ShoppingCartItem item : getItems()) {
            Product productDetails = (Product) item.getItem();

            amount += (item.getQuantity() * productDetails.getPrice());
        }

        return roundOff(amount);
    }

    private double roundOff(double x) {
        long val = Math.round(x * 100); // cents

        return val / 100.0;
    }

    /**
     * <p>Buy the items currently in the shopping cart.</p>
     */
    public String buy() {
        // "Cannot happen" sanity check
        if (getNumberOfItems() < 1) {
            message(null, "CartEmpty");

            return (null);
        } else {
            return ("productcashier");
        }
    }

    public synchronized void clear() {
        logger.log(Level.INFO, "Clearing cart.");
        items.clear();
        numberOfItems = 0;
        message(null, "CartCleared");
    }
}
