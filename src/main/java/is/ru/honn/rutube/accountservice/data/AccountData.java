/*************************************************************************************************
 *
 * AccountData.java - The AccountData class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.data;

import is.ru.honn.rutube.accountservice.domain.Account;
import is.ruframework.data.RuData;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
        try
        {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource())
                    .withTableName("Account")
                    .usingGeneratedKeyColumns("userId");
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(account);
            insert.executeAndReturnKey(namedParameters).intValue();
        }
        catch (DataAccessException daex)
        {
            throw new AccountDataGatewayException("Duplicate add.", daex.getCause());
        }
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
     * Deletes account with given username.
     *
     * @param username The unique username of the account being deleted.
     * @return The account that was deleted, null if deletion failed.
     * @throws AccountDataGatewayException On a failed deletion.
     */
    @Override
    public Account deleteAccount(String username) throws AccountDataGatewayException{
        Account account = getAccountByUsername(username);

        if(account == null)
            throw new AccountDataGatewayException("No account found with that username.");

        String sql = "DELETE FROM Account WHERE username = :username;";
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
        SqlParameterSource sqlParameters = new MapSqlParameterSource("username", username);
        template.update(sql, sqlParameters);

        return account;
    }


    /**
     * Updates account information.
     *
     * @param userId The id of the user being updated.
     * @param account The new account information.
     * @return The Account with the new account information.
     * @throws AccountDataGatewayException If update fails.
     */
    @Override
    public Account updateAccount(int userId, Account account) throws AccountDataGatewayException {
        try
        {
            String sql = "UPDATE ACCOUNT " +
                    "SET username = :username, password = :password " +
                    "WHERE userId = :userId;";

            account.setUserId(userId);
            SqlParameterSource sqlParameters = new BeanPropertySqlParameterSource(account);
            NamedParameterJdbcTemplate update = new NamedParameterJdbcTemplate(getDataSource());
            update.update(sql, sqlParameters);
            return account;
        }
        catch (DataAccessException daex)
        {
            throw new AccountDataGatewayException("Username already exists. Update aborted.", daex.getCause());
        }
    }
}