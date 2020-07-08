package com.callforcode.travelbetter.persistence;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class UserActivityResponse {
	String userId;
	Long rewardsReedemed;
	Long rewardsNotReedemed;
	List<UserActivity> userActivities;
	
}
