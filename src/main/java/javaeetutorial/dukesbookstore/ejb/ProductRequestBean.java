/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.dukesbookstore.ejb;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaeetutorial.dukesbookstore.entity.Product;
import javaeetutorial.dukesbookstore.exception.ProductNotFoundException;
import javaeetutorial.dukesbookstore.exception.ProductsNotFoundException;
import javaeetutorial.dukesbookstore.exception.OrderException;
import javaeetutorial.dukesbookstore.web.managedbeans.ShoppingCart;
import javaeetutorial.dukesbookstore.web.managedbeans.ShoppingCartItem;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>Stateful session bean for the petstore example.</p>
 */
@Stateful
public class ProductRequestBean {

    @PersistenceContext
    private EntityManager em;
    private static final Logger logger =
            Logger.getLogger("dukesbookstore.ejb.ProductRequestBean");

    public ProductRequestBean() throws Exception {
    }

    public void createProduct(String id, String weight, String size,
                              String name, Double price, Boolean onsale,
                              String description, Integer inventory) {
        try {
            Product product = new Product(id, weight, size, name, price,
                    onsale, description, inventory);
            logger.log(Level.INFO, "Created product {0}", id);
            em.persist(product);
            logger.log(Level.INFO, "Persisted product {0}", id);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public List<Product> getProducts() throws ProductsNotFoundException {
        try {
            return (List<Product>) em.createNamedQuery("findProducts").getResultList();
        } catch (Exception ex) {
            throw new ProductsNotFoundException(
                    "Could not get products: " + ex.getMessage());
        }
    }

    public Product getProduct(String id) throws ProductNotFoundException {
        Product requestedProduct = em.find(Product.class, id);

        if (requestedProduct == null) {
            throw new ProductNotFoundException("Couldn't find product: " + id);
        }

        return requestedProduct;
    }

    public void buyProducts(ShoppingCart cart) throws OrderException {
        Collection<ShoppingCartItem> items = cart.getItems();
        Iterator<ShoppingCartItem> i = items.iterator();

        try {
            while (i.hasNext()) {
                ShoppingCartItem sci = (ShoppingCartItem) i.next();
                Product bd = (Product) sci.getItem();
                String id = bd.getId();
                int quantity = sci.getQuantity();
                buyProduct(id, quantity);
            }
        } catch (OrderException ex) {
            throw new OrderException("Commit failed: " + ex.getMessage());
        }
    }

    public void buyProduct(String id, int quantity)
            throws OrderException {
        try {
            Product requestedProduct = em.find(Product.class, id);

            if (requestedProduct != null) {
                int inventory = requestedProduct.getInventory();

                if ((inventory - quantity) >= 0) {
                    int newInventory = inventory - quantity;
                    requestedProduct.setInventory(newInventory);
                } else {
                    throw new OrderException(
                            "Not enough of " + id
                            + " in stock to complete order.");
                }
            }
        } catch (OrderException ex) {
            throw new OrderException(
                    "Couldn't purchase product: " + id + ex.getMessage());
        }
    }

    public void updateInventory(ShoppingCart cart) throws OrderException {
        try {
            buyProducts(cart);
        } catch (OrderException ex) {
            throw new OrderException("Inventory update failed: " + ex.getMessage());
        }
    }
}
