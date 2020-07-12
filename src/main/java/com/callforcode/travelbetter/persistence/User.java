package com.callforcode.travelbetter.persistence;

import java.time.Instant;
import java.util.Date;

import org.springframework.data.annotation.ReadOnlyProperty;

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
    
    @NonNull
    private String email;
    
    @NonNull
    private String bio;
    
    @NonNull
    @ReadOnlyProperty
    @Builder.Default
    private Date joinedDate = Date.from(Instant.now());
 
}
