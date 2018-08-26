package com.petproject.monitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
@PropertySource("classpath:application-prod.yml")
@PropertySource("classpath:application-dev.yml")
public class Twitter4JConfiguration {

    @Value("${twitter4j.oauth.consumer-key}") String consumerKey;
    @Value("${twitter4j.oauth.consumer-secret}") String consumerSecret;
    @Value("${twitter4j.oauth.access-token}") String accessToken;
    @Value("${twitter4j.oauth.access-token-secret}") String accessTokenSecret;

    @Bean
    public Twitter getTwitterInstance() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(consumerKey)
		  .setOAuthConsumerSecret(consumerSecret)
		  .setOAuthAccessToken(accessToken)
		  .setOAuthAccessTokenSecret(accessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());

        return tf.getInstance();
    }
}
