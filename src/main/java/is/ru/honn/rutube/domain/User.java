/*************************************************************************************************
 *
 * User.java - The User class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Component description
 *
 * @author Kári
 * @version 1.0, 26 okt. 2016
 */
public class User {
    protected int Id;
    protected List<Integer> favoriteVideos;
    protected List<Integer> closeFriends;

    public User(int id){
        setId(id);
        favoriteVideos = new ArrayList<Integer>();
        closeFriends = new ArrayList<Integer>();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public List<Integer> getFavoriteVideos() {
        return favoriteVideos;
    }

    public void setFavoriteVideos(List<Integer> favoriteVideos) {
        this.favoriteVideos = favoriteVideos;
    }

    public List<Integer> getCloseFriends() {
        return closeFriends;
    }

    public void setCloseFriends(List<Integer> closeFriends) {
        this.closeFriends = closeFriends;
    }

}