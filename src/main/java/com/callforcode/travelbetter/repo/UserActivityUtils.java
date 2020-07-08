package com.callforcode.travelbetter.repo;

import java.util.ArrayList;
import java.util.List;

import com.callforcode.travelbetter.persistence.UserActivity;
import com.callforcode.travelbetter.persistence.UserActivityResponse;

public class UserActivityUtils {
	
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
			if (ua.getRewardsRedeemed())
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

}
