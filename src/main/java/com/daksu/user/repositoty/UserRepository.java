package com.daksu.user.repositoty;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;

import com.daksu.user.domain.User;

@Repository
public class UserRepository {
	
	@Autowired
    private WebApplicationContext context;
	
	public void addUser(User user) {
		MongoTemplate mongoTemplate = getMongoTemplate();
		if (!mongoTemplate.collectionExists(User.class)) {
			mongoTemplate.createCollection(User.class);
		}		
		mongoTemplate.insert(user, User.COLLECTION_NAME);
	}
	
	public User findUserByUsername(String emailId) {
		return getMongoTemplate().findOne(query(where("username").is(emailId)), User.class);
	}
	
	private MongoTemplate getMongoTemplate() {
		return (MongoTemplate) context.getBean("mongoTemplate");
	}


}
