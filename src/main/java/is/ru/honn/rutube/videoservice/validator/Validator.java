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
     * @return true if object is valid else false.
     */
    boolean validate();
}