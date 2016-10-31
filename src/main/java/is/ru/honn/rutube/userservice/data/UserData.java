/*************************************************************************************************
 *
 * UserData.java - The UserData class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.data;

import is.ru.honn.rutube.userservice.domain.UserProfile;
import is.ruframework.data.RuData;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;


/**
 * RuTube implementation of user data gateway
 *
 * @author Kári
 * @version 1.0, 27 okt. 2016
 */
public class UserData extends RuData implements UserDataGateway {

    /**
     * Default constructor.
     */
    public UserData(){

    }

    /**
     * Adds a userProfile to database.
     *
     * @param userId The userId of the user.
     */
    @Override
    public void createUserProfile(int userId) throws UserDataGatewayException {
        try {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource())
                    .withTableName("UserProfile");
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("userId", userId);
            insert.execute(namedParameters);
        }catch (DataAccessException dex){
            throw new UserDataGatewayException("Duplicate add.");
        }
    }

    /**
     * Gets the profile of user.
     *
     * @return Gets a profile for user..
     */
    @Override
    public UserProfile getUserProfile(int userId) {
        try {
            String sql = "SELECT * FROM UserProfile WHERE userId = :userId;";
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
            SqlParameterSource sqlParameters = new MapSqlParameterSource("userId", userId);
            UserProfile userProfile = (UserProfile) template.
                    queryForObject(sql, sqlParameters, new BeanPropertyRowMapper(UserProfile.class));
            userProfile.setCloseFriends(getListOfFriendIds(userId));
            userProfile.setFavoriteVideos(getListOfFavouriteVideoIds(userId));
            return userProfile;
        } catch (DataAccessException daex) {
            return null;
        }
    }

    /**
     * Deletes user.
     *
     * @param userId The id of a userProfile
     */
    @Override
    public void deleteUserProfile(int userId) throws UserDataGatewayException {
        try {
            String sql = "DELETE FROM UserProfile WHERE userId = :userId;";
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
            SqlParameterSource sqlParameters = new MapSqlParameterSource("userId", userId);
            template.update(sql, sqlParameters);
        }catch (DataAccessException dex){
            throw new UserDataGatewayException("Deletion failed.");
        }
    }

    /**
     * Adds a favorite video to a user.
     *
     * @param userId The id of a video
     * @param videoId The id of a video
     */
    @Override
    public void addFavoriteVideo(int userId, int videoId) throws UserDataGatewayException {
        try {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource())
                    .withTableName("Favorites");
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("videoId", videoId);
            insert.execute(namedParameters);
        }catch (DataAccessException dex){
            throw new UserDataGatewayException("Duplicate add.");
        }
    }

    /**
     * Deletes a favorite video from a user.
     *
     * @param userId The id of a video
     * @param videoId The id of a video
     */
    @Override
    public void deleteFavoriteVideo(int userId, int videoId) throws UserDataGatewayException {
        try {
            String sql = "DELETE FROM Favorites WHERE userId = :userId AND videoId = :videoId;";
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
            SqlParameterSource sqlParameters = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("videoId", videoId);
            template.update(sql, sqlParameters);
        }catch (DataAccessException dex){
            throw new UserDataGatewayException("Deletion failed.");
        }
    }

    /**
     * Adds a close friend to a user.
     *
     * @param userId The id of a video
     * @param friendId The id of a userProfile
     */
    @Override
    public void addCloseFriend(int userId, int friendId) throws UserDataGatewayException {
        try {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource())
                    .withTableName("Friends");
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("friendId", friendId);
            insert.execute(namedParameters);
        }catch (DataAccessException dex){
            throw new UserDataGatewayException("Duplicate add.");
        }
    }

    /**
     * Deletes a close friend from a user.
     *
     * @param userId The id of a video
     * @param friendId The id of a userProfile
     */
    @Override
    public void deleteCloseFriend(int userId, int friendId) throws UserDataGatewayException {
        try {
            String sql = "DELETE FROM Friends WHERE userId = :userId AND friendId = :friendId;";
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
            SqlParameterSource sqlParameters = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("friendId", friendId);
            template.update(sql, sqlParameters);
        }catch (DataAccessException dex){
            throw new UserDataGatewayException("Deletion failed.");
        }
    }

    /**
     * Gets the list of friend ids
     *
     * @param userId The id of the user.
     * @return List of friend id's
     */
    private List<Integer> getListOfFriendIds(int userId) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
        SqlParameterSource sqlParameters = new MapSqlParameterSource("userId", userId);
        return template.query("SELECT F.friendId FROM Friends F WHERE F.userId = :userId;",
                sqlParameters, new IntegerRowMapper(1));
    }

    /**
     * Gets the list of favourite video ids.
     *
     * @param userId The id of the user.
     * @return list of favourite videos of user.
     */
    private List<Integer> getListOfFavouriteVideoIds(int userId) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
        SqlParameterSource sqlParameters = new MapSqlParameterSource("userId", userId);
        return template.query("SELECT F.videoId FROM Favorites F WHERE F.userId = :userId;",
                sqlParameters, new IntegerRowMapper(1));
    }
}