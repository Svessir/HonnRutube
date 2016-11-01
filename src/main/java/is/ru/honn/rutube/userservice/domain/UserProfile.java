/*************************************************************************************************
 *
 * UserProfile.java - The UserProfile class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * User data domain class.
 *
 * @author Kári
 * @version 1.0, 26 okt. 2016
 */
public class UserProfile {
    protected int userId;
    protected String username;
    protected List<Integer> favoriteVideos;
    protected List<Integer> closeFriends;

    /**
     * Default constructor.
     */
    public UserProfile(){

    }

    /**
     * @param userId The user id.
     */
    public UserProfile(int userId, String username){
        setUserId(userId);
        setUsername(username);
        favoriteVideos = new ArrayList<Integer>();
        closeFriends = new ArrayList<Integer>();
    }

    /**
     * Get the user id.
     *
     * @return The user id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set the user id.
     *
     * @param userId The user id.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get the username.
     *
     * @return The username of the userProfile.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Sets the username of the userProfile.
     *
     * @param username The username of the userProfile.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Get the list of user favorite videos.
     *
     * @return The list of videos.
     */
    public List<Integer> getFavoriteVideos() {
        return favoriteVideos;
    }

    /**
     * Sets the list of user favorite videos.
     *
     * @param favoriteVideos The list of user favorite videos.
     */
    public void setFavoriteVideos(List<Integer> favoriteVideos) {
        this.favoriteVideos = favoriteVideos;
    }

    /**
     * Get the list of user close friends.
     *
     * @return The list of user close friends.
     */
    public List<Integer> getCloseFriends() {
        return closeFriends;
    }

    /**
     * Sets the list of user close friends.
     *
     * @param closeFriends The list of user close friends.
     */
    public void setCloseFriends(List<Integer> closeFriends) {
        this.closeFriends = closeFriends;
    }

}