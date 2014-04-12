/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.dukesbookstore.listeners;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * <p>Action listener for the image map on the index page.</p>
 */
public class MapProductChangeListener implements ActionListener {

    private static final Logger logger =
            Logger.getLogger("dukesbookstore.listeners.MapProductChangeListener");
    private HashMap<String, String> products = null;

    public MapProductChangeListener() {
        products = new HashMap<>(6);

        String product1 = products.put("Duke", "201");
        String product2 = products.put("Jeeves", "202");
        String product3 = products.put("Masterson", "203");
        String product4 = products.put("Novation", "205");
        String product5 = products.put("Thrilled", "206");
        String product6 = products.put("Coding", "207");
    }

    @Override
    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException {
        logger.log(Level.INFO, "Entering MapProductChangeListener.processAction");
        AreaSelectedEvent event = (AreaSelectedEvent) actionEvent;
        String current = event.getMapComponent().getCurrent();
        FacesContext context = FacesContext.getCurrentInstance();
        String id = products.get(current);
        context.getExternalContext().getSessionMap().put("id", id);
    }
}
