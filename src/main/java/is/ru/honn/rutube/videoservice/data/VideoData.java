/*************************************************************************************************
 *
 * VideoData.java - The VideoData class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.data;

import is.ru.honn.rutube.videoservice.domain.Channel;
import is.ru.honn.rutube.videoservice.domain.Video;
import is.ruframework.data.RuData;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import java.util.List;

/**
 * RuTube's video data gateway implementation.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public class VideoData extends RuData implements VideoDataGateway {

    /**
     * Gets all videos on RuTube.
     *
     * @return list of all videos.
     */
    @Override
    public List<Video> getAllVideos() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        return jdbcTemplate.query("SELECT * FROM Video;",
                new BeanPropertyRowMapper<>(Video.class));
    }

    /**
     * Gets all videos for a given channel.
     *
     * @param channelId The id of the channel.
     * @return List of all videos in channel.
     */
    @Override
    public List<Video> getVideosByChannelId(int channelId) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
        SqlParameterSource sqlParameters = new MapSqlParameterSource("channelId", channelId);
        return template.query("SELECT V.videoId, V.title, V.description, V.url, V.viewCount, V.numberOfLikes " +
                        "FROM Video V " +
                        "INNER JOIN ChannelVideo C " +
                        "ON V.videoId = C.videoId " +
                        "WHERE C.channelId = :channelId;",
                sqlParameters, new BeanPropertyRowMapper<>(Video.class));
    }

    /**
     * Creates a relation in database between a video and channel.
     *
     * @param videoId The id of the video.
     * @param channelId The id of the channel.
     * @throws VideoDataGatewayException If relation already exist or if the video
     *                                   or the channel doesn't exist
     */
    @Override
    public void addVideoToChannelRelation(int videoId, int channelId) throws VideoDataGatewayException {
        try
        {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource())
                    .withTableName("ChannelVideo")
                    .usingColumns("channelId", "videoId");
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource().
                    addValue("channelId", channelId)
                    .addValue("videoId", videoId);
            insert.execute(sqlParameterSource);
        }
        catch (DataAccessException daex)
        {
            throw new VideoDataGatewayException("Relation already exists");
        }
    }

    /**
     * Deletes a video from the database.
     *
     * @param videoId The id of the video.
     */
    @Override
    public void deleteVideo(int videoId) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
        SqlParameterSource sqlParamters = new MapSqlParameterSource("videoId", videoId);
        template.update("DELETE FROM Video WHERE videoId = :videoId;", sqlParamters);
    }

    /**
     * Gets a single video from database.
     *
     * @param videoId The id of the video.
     * @return The video if exists else null.
     */
    @Override
    public Video getVideoById(int videoId) {

        try
        {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
            SqlParameterSource sqlParameters = new MapSqlParameterSource("videoId", videoId);
            return template.queryForObject("SELECT * FROM Video WHERE videoId = :videoId;",
                    sqlParameters, new BeanPropertyRowMapper<>(Video.class));
        }
        catch (EmptyResultDataAccessException erdex)
        {
            return null;
        }
    }

    /**
     * Gets a single channel from database.
     *
     * @param channelName The name of the channel.
     * @return The channel if exists else null.
     */
    @Override
    public Channel getChannelByName(String channelName) {

        try
        {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
            SqlParameterSource sqlParameters = new MapSqlParameterSource("channelName", channelName);
            return template.queryForObject("SELECT * FROM Channel WHERE channelName = :channelName;",
                    sqlParameters, new BeanPropertyRowMapper<>(Channel.class));
        }
        catch (EmptyResultDataAccessException erdex)
        {
            return null;
        }
    }
}