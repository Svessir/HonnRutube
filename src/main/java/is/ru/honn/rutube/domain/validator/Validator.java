/*************************************************************************************************
 *
 * Validator.java - The Validator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain.validator;

/**
 * Validator API
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public interface Validator<T> {
    boolean validate();
}