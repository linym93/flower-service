package com.linym.flowerservice.repositories;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.linym.flowerservice.models.UserDoc;

@Repository
public class UserDocRepository {
	
	static final  Logger logger=LoggerFactory.getLogger(UserDocRepository.class);
	
	private final String feedUrl;
	RestTemplate restTemplate= new RestTemplate();
	private List<UserDoc> userDocRepository;
	
	
	@Autowired
	public UserDocRepository(@Value("${feed.url:http://jsonplaceholder.typicode.com/posts}") String feedUrl) {
		this.feedUrl=feedUrl;
		this.userDocRepository=retrieveUserDocsFromFeed(feedUrl);
		
	}
	
	
	
	public List<UserDoc> getUserDocRepository() {
		return userDocRepository;
	}



	public void setUserDocRepository(List<UserDoc> userDocRepository) {
		this.userDocRepository = userDocRepository;
	}



	private List<UserDoc> retrieveUserDocsFromFeed(String feedUrl){
		List<UserDoc> userDocs = new ArrayList<>();
		try {
		ResponseEntity<List<UserDoc>> response=restTemplate.exchange(
				feedUrl,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<UserDoc>>(){});
		userDocs=response.getBody();
			
		}catch(Exception e) {
			logger.error("Cannot get feed from "+feedUrl +" Error:" + e.getMessage());
			e.printStackTrace();
		}
		return userDocs;
		
	}

}
