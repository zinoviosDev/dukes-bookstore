/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.martinspetstore.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * <p>Singleton bean that initializes the product database for the petstore
 * example.</p>
 */
@Singleton
@Startup
public class ConfigBean {

    @EJB
    private ProductRequestBean request;

    @PostConstruct
    public void createData() {
        request.createProduct("201", "5kg", "",
                "Cat Food",
                19.99, false, "Good cat food.", 20);
        request.createProduct("202", "10kg", "",
                "Cat Food",
                34.99, false, "Bigger than the smaller one.", 20);
        request.createProduct("203", "100g", "",
                "Fish Food",
                4.99, false, "Flakey.", 20);
        request.createProduct("205", "", "Large",
                "Dog Collar",
                9.99, true, "Collars my dog nicely.", 20);
        request.createProduct("206", "20kg", "",
                "Dog Food",
                50.00, true, "Very heavy.", 20);
        request.createProduct("207", "", "30cm x 70cm",
                "Cat Litter Tray", 30.95, true,
                "Necessary.", 20);

    }
}
