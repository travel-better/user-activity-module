//package com.callforcode.travelbetter.repo;
//
//import java.sql.Date;
//import java.util.List;
//
//import org.springframework.data.couchbase.core.query.ViewIndexed;
//import org.springframework.data.couchbase.repository.CouchbaseRepository;
//
//import com.callforcode.travelbetter.persistence.UserActivity;
//
//@ViewIndexed(designDoc = "userActivity")
//public interface UserActivityRepository extends CouchbaseRepository<UserActivity, String> {
//
//	List<UserActivity> findByUserId(String userId);
//
//	List<UserActivity> findByActivityStartAndEndDate(Date startDate, Date endDate);
//}
