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
import javaeetutorial.dukesbookstore.entity.Pet;
import javaeetutorial.dukesbookstore.exception.BookNotFoundException;
import javaeetutorial.dukesbookstore.exception.BooksNotFoundException;
import javaeetutorial.dukesbookstore.exception.OrderException;
import javaeetutorial.dukesbookstore.web.managedbeans.ShoppingCart;
import javaeetutorial.dukesbookstore.web.managedbeans.ShoppingCartItem;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>Stateful session bean for the bookstore example.</p>
 */
@Stateful
public class BookRequestBean {

    @PersistenceContext
    private EntityManager em;
    private static final Logger logger =
            Logger.getLogger("dukesbookstore.ejb.BookRequestBean");

    public BookRequestBean() throws Exception {
    }

    public void createBook(String bookId, String surname, String firstname,
            String title, Double price, Boolean onsale, Integer calendarYear,
            String description, Integer inventory) {
        try {
            Pet pet = new Pet(bookId, surname, firstname, title, price,
                    onsale, calendarYear, description, inventory);
            logger.log(Level.INFO, "Created pet {0}", bookId);
            em.persist(pet);
            logger.log(Level.INFO, "Persisted pet {0}", bookId);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public List<Pet> getBooks() throws BooksNotFoundException {
        try {
            return (List<Pet>) em.createNamedQuery("findPets").getResultList();
        } catch (Exception ex) {
            throw new BooksNotFoundException(
                    "Could not get books: " + ex.getMessage());
        }
    }

    public Pet getBook(String bookId) throws BookNotFoundException {
        Pet requestedPet = em.find(Pet.class, bookId);

        if (requestedPet == null) {
            throw new BookNotFoundException("Couldn't find book: " + bookId);
        }

        return requestedPet;
    }

    public void buyBooks(ShoppingCart cart) throws OrderException {
        Collection<ShoppingCartItem> items = cart.getItems();
        Iterator<ShoppingCartItem> i = items.iterator();

        try {
            while (i.hasNext()) {
                ShoppingCartItem sci = (ShoppingCartItem) i.next();
                Pet bd = (Pet) sci.getItem();
                String id = bd.getPetId();
                int quantity = sci.getQuantity();
                buyBook(id, quantity);
            }
        } catch (OrderException ex) {
            throw new OrderException("Commit failed: " + ex.getMessage());
        }
    }

    public void buyBook(String bookId, int quantity)
            throws OrderException {
        try {
            Pet requestedPet = em.find(Pet.class, bookId);

            if (requestedPet != null) {
                int inventory = requestedPet.getInventory();

                if ((inventory - quantity) >= 0) {
                    int newInventory = inventory - quantity;
                    requestedPet.setInventory(newInventory);
                } else {
                    throw new OrderException(
                            "Not enough of " + bookId
                            + " in stock to complete order.");
                }
            }
        } catch (OrderException ex) {
            throw new OrderException(
                    "Couldn't purchase book: " + bookId + ex.getMessage());
        }
    }

    public void updateInventory(ShoppingCart cart) throws OrderException {
        try {
            buyBooks(cart);
        } catch (OrderException ex) {
            throw new OrderException("Inventory update failed: " + ex.getMessage());
        }
    }
}
