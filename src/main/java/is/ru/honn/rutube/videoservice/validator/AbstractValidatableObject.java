/*************************************************************************************************
 *
 * AbstractValidatableObject.java - The AbstractValidatableObject class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that implements all
 * common validatable methods.
 *
 * @author Sverrir
 * @version 1.0, 31 okt. 2016
 */
public abstract class AbstractValidatableObject implements Validatable {

    private List<Validator> validators = new ArrayList<>();

    /**
     * Adds a validator to the list of validators
     * validating this object.
     *
     * @param validator The validator being added.
     */
    @Override
    public void addValidator(Validator validator) {
        if(validator != null)
            validators.add(validator);
    }

    /**
     * Validates the object.
     *
     * @throws ValidationException if object is not valid.
     */
    @Override
    public void validate() throws ValidationException {
        for(Validator validator: validators)
            validator.validate();
    }

    /**
     * Cleans the validator list.
     */
    protected void cleanValidators() {
        validators.clear();
    }
}