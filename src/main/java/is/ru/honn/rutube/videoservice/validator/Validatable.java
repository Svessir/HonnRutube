/*************************************************************************************************
 *
 * Validatable.java - The Validatable class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.validator;

/**
 * API for an object that can be validated.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 * @param <T>
 */
public interface Validatable<T> {

    /**
     * Adds a validator to the list of validators
     * validating this object.
     *
     * @param validator The validator being added.
     */
    void addValidator(Validator<T> validator);

    /**
     * Validates the object.
     *
     * @return true if object is valid else false.
     */
    boolean validate();

    /**
     * Initializes this object with default validators if any.
     */
    void initialize();
}