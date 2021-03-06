/*************************************************************************************************
 *
 * Validator.java - The Validator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.validator;

/**
 * Validator API
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 * @param <T> The object this validator validates.
 */
public interface Validator<T> {

    /**
     * Validates the object set to this validator.
     *
     * @return true if object is valid else false.
     */
    boolean validate();
}