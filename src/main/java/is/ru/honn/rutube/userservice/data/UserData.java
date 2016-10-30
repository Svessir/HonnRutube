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
            throw new UserDataGatewayException("Adding user failed.");
        }
    }

    /**
     * Get the logged in user.
     *
     * @return The userProfile logged in.
     */
    @Override
    public UserProfile getUserProfile(int userId) {
        String sql = "SELECT * FROM UserProfile WHERE userId = :userId;";
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
        SqlParameterSource sqlParameters = new MapSqlParameterSource("userId", userId);
        UserProfile userProfile = (UserProfile) template.
                queryForObject(sql, sqlParameters, new BeanPropertyRowMapper(UserProfile.class));
        return userProfile;
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
            throw new UserDataGatewayException("Adding failed.");
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
            String sql = "DELETE * FROM Favorits WHERE userId = : userId AND videoId = :videoId;";
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
            throw new UserDataGatewayException("Adding failed.");
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
            String sql = "DELETE * FROM Friends WHERE userId = : userId AND friendId = :friendId;";
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
            SqlParameterSource sqlParameters = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("friendId", friendId);
            template.update(sql, sqlParameters);
        }catch (DataAccessException dex){
            throw new UserDataGatewayException("deletion failed.");
        }
    }
}