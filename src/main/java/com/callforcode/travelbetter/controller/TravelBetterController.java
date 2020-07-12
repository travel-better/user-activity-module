package com.callforcode.travelbetter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.callforcode.travelbetter.exception.BadArgumentException;
import com.callforcode.travelbetter.exception.NotFoundException;
import com.callforcode.travelbetter.exception.TravelBetterException;
import com.callforcode.travelbetter.persistence.User;
import com.callforcode.travelbetter.persistence.UserActivity;
import com.callforcode.travelbetter.persistence.UserActivityResponse;
import com.callforcode.travelbetter.repo.Utils;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/travelbetter")
@Slf4j
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

	@PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
	public @ResponseBody User createUser(@RequestBody User user) {
		if(user == null) {
			throw new BadArgumentException("Null input.");
		}
		
		Response r=  getDB(client).post(user);
			return User.builder().id(user.getId())
					.address(user.getAddress())
					.firstName(user.getFirstName())
					.lastName(user.getLastName())
					.bio(user.getBio())
					.email(user.getEmail())
					.joinedDate(user.getJoinedDate()).build();
	}
	
	@PutMapping(path = "/users/{userId}", consumes = "application/json", produces = "application/json")
	public @ResponseBody User updateUser(@PathVariable String userId,
										 @RequestBody User user) {
		try {
			if(userId == null || user == null) {
				log.error("invalid input");
				throw new BadArgumentException("Invalid input.");
			}
			//get user
			List<User> allUsers = getDB(client).getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(User.class);
			if (allUsers == null || allUsers.isEmpty()) {
				log.error("User not found");
				throw new NotFoundException("User [" + userId +"] not found");
			}

			User internalUser =  allUsers.stream()
					.filter(u -> (u.getId() != null && u.getId().equals(userId)))
					.findFirst().get();
			
			if (internalUser == null) {
				log.error("User not found");
				throw new NotFoundException("User [" + userId +"] not found");
			}
			
			//update it with new user
			user = Utils.mapInternalUserToResponse(internalUser, user);
			if (user == null) {
				log.error("Error mapping user");
				throw new TravelBetterException("Error mapping user");
			}
	
			Response r=  getDB(client).post(user);
			return User.builder().id(userId)
					.address(user.getAddress())
					.firstName(user.getFirstName())
					.lastName(user.getLastName())
					.bio(user.getBio())
					.email(user.getEmail())
					.joinedDate(user.getJoinedDate()).build();
			
		} catch (Exception e) {
			log.error("error " + e.getMessage());
			throw new TravelBetterException("Something went wrong " + e.getMessage());
		}
	}
	
	@GetMapping("/users/{userId}")
	public @ResponseBody User getUser(@PathVariable String userId) {
		if(userId == null) {
			log.error("invalid input");
			throw new BadArgumentException("Invalid input.");
		}
		
		try {
			List<User> allUsers = getDB(client).getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(User.class);
			if (allUsers == null || allUsers.isEmpty()) {
					log.error("User not found");
					throw new NotFoundException("User [" + userId +"] not found");
			}
			return allUsers.stream()
			.filter(u -> (u.getId() != null && userId.equals(u.getId())))
					.findFirst().get();
		} catch (Exception e) {
			log.error("error " + e.getMessage());
			throw new TravelBetterException("Something went wrong " + e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/users/{userId}", consumes = "application/json", produces = "application/json")
	public @ResponseBody void deleteUser(@PathVariable String userId) {
		try {
			if(userId == null) {
				log.error("invalid input");
				throw new BadArgumentException("Invalid input.");
			}
			//get user
			List<User> allUsers = getDB(client).getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(User.class);
			if (allUsers == null || allUsers.isEmpty()) {
				log.error("User not found");
				throw new NotFoundException("User [" + userId +"] not found");
			}

			User internalUser =  allUsers.stream()
					.filter(u -> (u.getId() != null && u.getId().equals(userId)))
					.findFirst().get();
			
			if (internalUser == null) {
				log.error("User not found");
				throw new NotFoundException("User [" + userId +"] not found");
			}
			
			//delete user
			getDB(client).remove(internalUser);
			
		} catch (Exception e) {
			log.error("error " + e.getMessage());
			throw new TravelBetterException("Something went wrong " + e.getMessage());
		}
	}
	
	@PostMapping(path = "/userActivity", consumes = "application/json", produces = "application/json")
	public @ResponseBody UserActivity createActivity(@RequestBody UserActivity userActivity) {
		if(userActivity == null) {
			throw new BadArgumentException("Null input.");
		}
		
		Response r=  getDB(client).post(userActivity);
		return UserActivity.builder()
				.id(r.getId()).activityRewards(userActivity.getActivityRewards())
				.activityEndDate(userActivity.getActivityEndDate())
				.activityStartDate(userActivity.getActivityStartDate())
				.activityType(userActivity.getActivityType())
				.isRewardRedeemed(userActivity.getIsRewardRedeemed())
				.userId(userActivity.getUserId()).build();
	}
	
	@GetMapping("/userActivities/{userId}")
	public @ResponseBody UserActivityResponse getUserRewards(@PathVariable String userId , 
											   @RequestParam(required = false) Date startDate, 
											   @RequestParam(required = false) Date endDate) {
		//list user activities
		try {
			List<UserActivity> allUsersActivities = getDB(client).getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(UserActivity.class);
			if (allUsersActivities == null || allUsersActivities.isEmpty()) {
				log.error("No UserActivities found for user [" +userId +"]" );
				throw new NotFoundException("No UserActivities found for user [" +userId +"]" );
			}
			
			List allActivities =  allUsersActivities.stream()
			.filter(activity -> (activity.getId() != null && userId.equals(activity.getUserId())))
			.collect(Collectors.toList());
			//TODO: add date criteria logic
			return Utils.mapUserActivityToResponse(allActivities);
		} catch (Exception e) {
			log.error("error " + e.getMessage());
			throw new TravelBetterException("Something went wrong " + e.getMessage());
		}
	}
	
	@Bean
	public Database getDB(CloudantClient cloudant) {
		return cloudant.database("travelbetter", true);
	}
}
