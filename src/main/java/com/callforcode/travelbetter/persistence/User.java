package com.callforcode.travelbetter.persistence;

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
public class User {
	
	@NonNull
    private String id;
 
    @NonNull
    private String firstName;
 
    @NonNull
    private String lastName;
    
    @NonNull
    private String address;
 
}
