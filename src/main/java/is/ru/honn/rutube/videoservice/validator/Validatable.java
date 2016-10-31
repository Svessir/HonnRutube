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
 */
public interface Validatable {

    /**
     * Adds a validator to the list of validators
     * validating this object.
     *
     * @param validator The validator being added.
     */
    void addValidator(Validator validator);

    /**
     * Validates the object.
     *
     * @throws ValidationException if object is not valid.
     */
    void validate() throws ValidationException;

    /**
     * Initializes this object with default validators if any
     * and does any other initialization required before validating.
     */
    void initialize();
}