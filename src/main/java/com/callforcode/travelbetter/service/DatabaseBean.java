package com.callforcode.travelbetter.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

@Component
public class DatabaseBean {
	
	@Bean
	public Database getDB(CloudantClient cloudant) {
		return cloudant.database("travelbetter", true);
	}
}