package com.callforcode.travelbetter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.callforcode.travelbetter.persistence.User;
import com.callforcode.travelbetter.persistence.UserActivity;
import com.callforcode.travelbetter.persistence.UserActivityResponse;
import com.callforcode.travelbetter.repo.UserActivityUtils;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;

@RestController
@RequestMapping("/travelbetter")
public class TravelBetterController {
	
	@Autowired
	private CloudantClient client;
	
	@GetMapping("cloudant")
	String cloudant() {
		List<String> list = new ArrayList<>();
		try {
			list = client.getAllDbs();
		} catch (Exception e) {
			return "Server error " + e.getMessage();
		}
		return "Your Cloudant service is running at " + list.toString();
		
	}
	
	@GetMapping("/userActivities/{userId}")
	public @ResponseBody UserActivityResponse getUserRewards(@PathVariable String userId , 
											   @RequestParam(required = false) Date startDate, 
											   @RequestParam(required = false) Date endDate) {
		//list user activities
		try {
			List<UserActivity> allUsersActivities = getDB(client).getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(UserActivity.class);
			if (allUsersActivities == null || allUsersActivities.isEmpty())
				return null;
			List allActivities =  allUsersActivities.stream()
			.filter(activity -> (activity.getId() != null && userId.equals(activity.getUserId())))
			.collect(Collectors.toList());
			return UserActivityUtils.mapUserActivityToResponse(allActivities);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error " + e.getMessage());
			return null;
		}
	}

	@PostMapping(path = "/user", consumes = "application/json", produces = "application/json")
	public @ResponseBody User createUser(@RequestBody User user) {
		if(user != null) {
			Response r=  getDB(client).post(user);
			return User.builder().id(user.getId())
					.address(user.getAddress())
					.firstName(user.getFirstName())
					.lastName(user.getLastName()).build();
		}
		return null;
	}
	
	@PostMapping(path = "/userActivity", consumes = "application/json", produces = "application/json")
	public @ResponseBody UserActivity createActivity(@RequestBody UserActivity userActivity) {
		if(userActivity != null) {
			Response r=  getDB(client).post(userActivity);
			return UserActivity.builder()
					.id(r.getId()).activityRewards(userActivity.getActivityRewards())
					.activityEndDate(userActivity.getActivityEndDate())
					.activityStartDate(userActivity.getActivityStartDate())
					.activityType(userActivity.getActivityType())
					.rewardsRedeemed(userActivity.getRewardsRedeemed()).build();
		}
		return null;
	}
	
	@Bean
	public Database getDB(CloudantClient cloudant) {
		return cloudant.database("travelbetter", true);
	}
}
