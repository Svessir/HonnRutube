/*************************************************************************************************
 *
 * AccountData.java - The AccountData class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.data;

import is.ru.honn.rutube.domain.account.Account;
import is.ruframework.data.RuData;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 * RuTubes account data gateway implementation.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class AccountData extends RuData implements AccountDataGateway {

    /**
     * Adds a new account into the system database.
     *
     * @param account The account to be added to database.
     * @throws AccountDataGatewayException If the account could not be added to the database.
     */
    @Override
    public void addAccount(Account account) throws AccountDataGatewayException {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource())
                .withTableName("Account")
                .usingGeneratedKeyColumns("userId");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(account);
        insert.executeAndReturnKey(namedParameters).intValue();
    }

    /**
     * Gets an account from the system database.
     * queried via username.
     *
     * @param username The username of the account being queried.
     * @return The account if found else null.
     */
    @Override
    public Account getAccountByUsername(String username) {
        try
        {
            String sql = "SELECT * FROM Account A WHERE A.username = :username;";
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
            SqlParameterSource sqlParameters = new MapSqlParameterSource("username", username);
            Account account = (Account) template
                    .queryForObject(sql, sqlParameters, new BeanPropertyRowMapper(Account.class));
            return account;
        }
        catch (DataAccessException daex)
        {
            return null;
        }
    }

    /**
     * Deletes an account from the database.
     *
     * @param username The unique username of the account being deleted.
     * @return The account that was deleted, null if deletion failed.
     */
    @Override
    public Account deleteAccount(String username) {
        Account account = getAccountByUsername(username);

        if(account == null)
            return null;

        try
        {
            String sql = "DELETE FROM Account WHERE username = :username;";
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
            SqlParameterSource sqlParameters = new MapSqlParameterSource("username", username);
            template.update(sql, sqlParameters);
        }
        catch (DataAccessException daex)
        {
            // Deletion failed.
            return null;
        }

        return account;
    }


    @Override
    public void updateAccount(int userId, Account account) {

    }
}