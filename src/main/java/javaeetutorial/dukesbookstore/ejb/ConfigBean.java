/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.dukesbookstore.ejb;

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
        request.createProduct("201", "Duke", "",
                "My Early Years: Growing Up on *7",
                30.75, false, "What a cool product.", 20);
        request.createProduct("202", "Jeeves", "",
                "Web Servers for Fun and Profit Test Four", 40.75, true,
                "What a cool product.", 20);
        request.createProduct("203", "Masterson", "Webster",
                "Web Components for Web Developers",
                27.75, false, "What a cool product.", 20);
        request.createProduct("205", "Novation", "Kevin",
                "From Oak to Java: The Revolution of a Language",
                10.75, true, "What a cool product.", 20);
        request.createProduct("206", "Thrilled", "Ben",
                "The Green Project: Programming for Consumer Devices",
                30.00, true, "What a cool product.", 20);
        request.createProduct("207", "Coding", "Happy",
                "Java Intermediate Bytecodes", 30.95, true,
                "What a cool product.", 20);

    }
}
