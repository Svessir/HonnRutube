/*************************************************************************************************
 *
 * Validator.java - The Validator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.validator;

/**
 * API for a validator.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 * @param <T> The object this validator can validate.
 */
public interface Validator<T> {

    /**
     * Validates the object set to this validator.
     *
     * @throws ValidationException if object is not valid.
     */
    void validate() throws ValidationException;

    /**
     * Sets the object to validate.
     *
     * @param object The object to validate.
     */
    void setObject(T object);
}