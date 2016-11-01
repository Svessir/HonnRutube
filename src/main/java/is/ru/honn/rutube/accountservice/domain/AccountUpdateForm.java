/*************************************************************************************************
 *
 * AccountUpdateForm.java - The AccountUpdateForm class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.domain;

/**
 * Form for account information update.
 *
 * @author Sverrir
 * @version 1.0, 01 nóv. 2016
 */
public class AccountUpdateForm extends AccountRegistration {

    private String currentPassword;

    /**
     * Gets the current password for the
     * account update form.
     *
     * @return The current password of the account.
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * Sets the current password of the
     * account update form.
     *
     * @param currentPassword The current password.
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}