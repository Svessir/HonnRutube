/*************************************************************************************************
 *
 * Validatable.java - The Validatable class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain.validator;

/**
 * API for validatable objects.
 *
 * @author Sverrir
 * @version 1.0, 27 okt. 2016
 */
public interface Validatable {

    /**
     * Adds a validator to the list of validators
     * for this object.
     *
     * @param validator The validator to be added.
     */
    void addValidator(Validator validator);

    /**
     * Validate according to added validators.
     *
     * @return true if object is valid.
     */
    boolean validate();

    /**
     * initializes validators.
     */
    void initialize();
}