package com.callforcode.travelbetter.persistence;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.Setter;


@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class UserActivity {
	
	@NonNull
    private String id;
 
    @NonNull
    private String userId;
 
    @NonNull
    private Date activityStartDate;
    
    private Date activityEndDate;
 
    @NonNull
    private String activityType;
    
    private Integer activityRewards;
    
    private Boolean rewardsRedeemed;
 
}
