/*************************************************************************************************
 *
 * IntegerRowMapper.java - The IntegerRowMapper class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.data;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps a single column to in result set to integer.
 *
 * @author Sverrir
 * @version 1.0, 31 okt. 2016
 */
public class IntegerRowMapper implements RowMapper<Integer> {

    private int column;

    /**
     * @param column The column number to map to integer.
     */
    public IntegerRowMapper(int column){
        this.column = column;
    }

    /**
     * @param resultSet The result set
     * @param i The row number
     * @return an integer.
     * @throws SQLException on failure.
     */
    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt(column);
    }
}