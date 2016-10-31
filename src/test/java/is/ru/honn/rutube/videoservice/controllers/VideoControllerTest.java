/*************************************************************************************************
 *
 * VideoControllerTest.java - The VideoControllerTest class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.controllers;

import is.ru.honn.rutube.clients.authentication.AuthenticationClient;
import is.ru.honn.rutube.videoservice.domain.Channel;
import is.ru.honn.rutube.videoservice.domain.Video;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Integration test for the video service.
 *
 * @author Sverrir
 * @version 1.0, 31 okt. 2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VideoControllerTest  implements ApplicationContextAware {

    private static URL base;

    private static DataSource dataSource;

    private TestRestTemplate template = new TestRestTemplate();

    private JdbcTemplate jdbcTemplate;

    private static boolean isSetupDone;

    private static HttpHeaders httpHeaders;

    private static AuthenticationClient authenticationClient;

    private static Video videoToAdd;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        dataSource = (DataSource) applicationContext.getBean("dataSource");
        authenticationClient = (AuthenticationClient) applicationContext.getBean("authenticationClient");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @AfterClass
    public static void cleanDatabase() {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        // Clean test database before integration test.
        template.update("DELETE Video;");
        template.update("DELETE Channel;");
        template.update("DELETE ChannelVideo;");
        template.update("DELETE Account");
    }

    public static void insertIntoDatabase() {
        NamedParameterJdbcTemplate insert = new NamedParameterJdbcTemplate(dataSource);

        for(int i = 0; i < 5; i++) {
            Video video = new Video("Title" + i, "Description" + i, "url" + i, i, i);
            Channel channel = new Channel("Channel" + i);

            insert.update("INSERT INTO Video (title, description, url, viewCount, numberOfLikes) " +
                            "VALUES(:title, :description, :url, :viewCount, :numberOfLikes);",
                    new BeanPropertySqlParameterSource(video));

            insert.update("INSERT INTO Channel (channelName) VALUES(:channelName);",
                    new BeanPropertySqlParameterSource(channel));
        }
    }

    public static void signIn() throws Exception {
        boolean success = authenticationClient.signUp("TestUser1", "12345", "12345");

        if(!success)
            throw new Exception("Singing up test user failed.");

        String token = authenticationClient.logIn("TestUser1", "12345");

        if(token == null)
            throw new Exception("Logging into test account failed.");

        httpHeaders = new HttpHeaders();
        httpHeaders.set("Token", token);
    }

    /**
     * Set up url and clean database before first test.
     *
     * @throws Exception if setup fails.
     */
    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + 8080 + "/video/");

        if(!isSetupDone) {
            cleanDatabase();
            insertIntoDatabase();
            signIn();
        }
        isSetupDone = true;
    }

    /**
     * Tests if getting all videos works correctly.
     * @throws Exception if test fails.
     */
    @Test
    public void stage1_getAllVideos() throws Exception {
        // Request for all videos.
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<List<Video>> response = template.exchange(base.toString(), HttpMethod.GET,
                httpEntity, new ParameterizedTypeReference<List<Video>>() {});

        // Verify that the operation was successful
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the response includes 5 videos.
        assertEquals(5, response.getBody().size());

        // Get the video to add to channel in the next stage.
        videoToAdd = response.getBody().get(0);
    }

    /**
     * Tests of adding video channel works correctly.
     * @throws Exception if test fails.
     */
    @Test
    public void stage2_addVideoToChannel() throws Exception {
        // Make sure the previous test did not fail.
        assertNotNull(videoToAdd);

        // Request for all videos for channel1.
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<List<Video>> channelVideoResponse = template.exchange(base.toString() + "channel/channel1", HttpMethod.GET,
                httpEntity, new ParameterizedTypeReference<List<Video>>() {});

        // Verify that there are no videos on channel.
        assertEquals(HttpStatus.OK, channelVideoResponse.getStatusCode());
        assertEquals(0, channelVideoResponse.getBody().size());

        // add the video from the previous stage to channel1.
        ResponseEntity response = template.exchange(base.toString() + "/channel/channel1/" + videoToAdd.getVideoId(),
                HttpMethod.POST, httpEntity, String.class);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Request for all videos.
        ResponseEntity<List<Video>> response1 = template.exchange(base.toString(), HttpMethod.GET,
                httpEntity, new ParameterizedTypeReference<List<Video>>() {});

        // Confirm the video is listed in all videos.
        Video video = null;
        for(Video v : response1.getBody()) {
            if(videoToAdd.getVideoId() == v.getVideoId())
                video = v;
        }
        assertNotNull(video);

        // Request for all videos for channel1.
        ResponseEntity<List<Video>> response2 = template.exchange(base.toString() + "channel/channel1", HttpMethod.GET,
                httpEntity, new ParameterizedTypeReference<List<Video>>() {});

        // Verify that the added video to channel is in the channel.
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(1, response2.getBody().size());
        assertEquals(videoToAdd.getVideoId(), response2.getBody().get(0).getVideoId());

    }

    /**
     * Tests if deleting a video works correctly.
     * @throws Exception if test fails.
     */
    @Test
    public void stage3_deleteVideo() throws Exception {
        // Make sure the previous test did not fail.
        assertNotNull(videoToAdd);

        // Request for all videos for channel1.
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity response = template.exchange(base.toString() + videoToAdd.getVideoId(), HttpMethod.DELETE,
                httpEntity, String.class);

        // Assert we got a success status.
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Request for all videos.
        ResponseEntity<List<Video>> response1 = template.exchange(base.toString(), HttpMethod.GET,
                httpEntity, new ParameterizedTypeReference<List<Video>>() {});

        // Confirm the video is not listed in all videos.
        Video video = null;
        for(Video v : response1.getBody()) {
            if(videoToAdd.getVideoId() == v.getVideoId())
                video = v;
        }
        assertEquals(null, video);

        // Request for all videos for channel1.
        ResponseEntity<List<Video>> response2 = template.exchange(base.toString() + "channel/channel1", HttpMethod.GET,
                httpEntity, new ParameterizedTypeReference<List<Video>>() {});

        // Assert the video is not listed in channel1
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(0, response2.getBody().size());


    }
}