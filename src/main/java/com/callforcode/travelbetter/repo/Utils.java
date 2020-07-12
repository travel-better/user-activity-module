package com.callforcode.travelbetter.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.callforcode.travelbetter.persistence.User;
import com.callforcode.travelbetter.persistence.UserActivity;
import com.callforcode.travelbetter.persistence.UserActivityResponse;

public class Utils {
	
	public static UserActivityResponse mapUserActivityToResponse(List<UserActivity> userActivities) {
		if (userActivities == null || userActivities.isEmpty())
			return null;
		List<UserActivity> newActivities = new ArrayList<UserActivity>();
		Long totalRewardsReedemed = 0L;
		Long totalRewardsNotReedemed = 0L;
		String userId = null;
		for (UserActivity ua : userActivities) {
			if (ua == null)
				continue;
			userId = ua.getUserId();
			if (ua.getIsRewardRedeemed())
				totalRewardsReedemed += ua.getActivityRewards();
			else
				totalRewardsNotReedemed += ua.getActivityRewards();
			
			newActivities.add(UserActivity.builder().id(ua.getId())
					.userId(ua.getUserId())
					.activityType(ua.getActivityType())
					.activityRewards(ua.getActivityRewards())
					.activityStartDate(ua.getActivityStartDate())
					.activityEndDate(ua.getActivityEndDate()).build());
		}
		return UserActivityResponse.builder()
				.userId(userId).userActivities(newActivities)
				.rewardsNotReedemed(totalRewardsNotReedemed)
				.rewardsReedemed(totalRewardsReedemed).build();
	}

	public static User mapInternalUserToResponse(User internalUser, User updatedUser) {
		if (updatedUser == null && internalUser == null)
			return null;

		return User.builder().id(internalUser.getId())
				.address(StringUtils.isEmpty(updatedUser.getAddress()) ? internalUser.getAddress() :updatedUser.getAddress())
				.firstName(StringUtils.isEmpty(updatedUser.getFirstName()) ? internalUser.getFirstName() :updatedUser.getFirstName())
				.lastName(StringUtils.isEmpty(updatedUser.getLastName()) ? internalUser.getLastName() :updatedUser.getLastName())
				.bio(StringUtils.isEmpty(updatedUser.getBio()) ? internalUser.getBio() :updatedUser.getBio())
				.email(StringUtils.isEmpty(updatedUser.getEmail()) ? internalUser.getEmail() :updatedUser.getEmail()).build();
	}

}
