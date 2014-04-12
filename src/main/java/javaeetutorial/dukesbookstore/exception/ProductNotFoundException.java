/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.dukesbookstore.exception;

/**
 * <p>This application exception indicates that a product has not been found.</p>
 */
public class ProductNotFoundException extends Exception {

    private static final long serialVersionUID = 8712363279947073702L;

    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String msg) {
        super(msg);
    }
}
