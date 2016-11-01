/*************************************************************************************************
 *
 * UserProfileChange.java - The UserProfileChange class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.domain;

/**
 * User profile change domain class.
 *
 * @author Kári
 * @version 1.0, 26 okt. 2016
 */
public class UserProfileChange {
    private String newUserName;
    private String currentPassword;
    private String newPassword;
    private String repeatedPassword;

    /**
     * Default constructor.
     */
    public UserProfileChange(){

    }

    /**
     * @param newUserName The userProfileChange new username.
     * @param currentPassword The userProfileChange current password.
     * @param newPassword The userProfileChange new password.
     * @param repeatedPassword The userProfileChange repeated password.
     */
    public UserProfileChange(String newUserName, String currentPassword,
                             String newPassword, String repeatedPassword){
        setNewUserName(newUserName);
        setCurrentPassword(currentPassword);
        setNewPassword(newPassword);
        setRepeatedPassword(repeatedPassword);
    }

    /**
     * Get the new username.
     *
     * @return The new username of userProfileChange.
     */
    public String getNewUserName() {
        return newUserName;
    }

    /**
     * Sets the new username.
     *
     * @param newUserName The new username of userProfileChange.
     */
    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    /**
     * Get the current password.
     *
     * @return The current password of userProfileChange.
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * Sets the current password.
     *
     * @param currentPassword The current password of userProfileChange.
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * Get the new password.
     *
     * @return The new password of userProfileChange.
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the new password.
     *
     * @param newPassword The new password of userProfileChange.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Get the repeated password.
     *
     * @return The repeated password of userProfileChange.
     */
    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    /**
     * Sets the repeated password.
     *
     * @param repeatedPassword The repeated password of userProfileChange.
     */
    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

}