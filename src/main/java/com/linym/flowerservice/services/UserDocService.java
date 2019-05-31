package com.linym.flowerservice.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linym.flowerservice.models.UserDoc;
import com.linym.flowerservice.repositories.UserDocRepository;


@Service
public class UserDocService {
	
	
	
	@Autowired
	UserDocRepository userDocRepository;
	
	public List<UserDoc> getUserDocRepository(){
		return userDocRepository.getUserDocRepository();
	}
	
	
	public UserDoc getUserDocByID(long id) {
		for(UserDoc userDoc:userDocRepository.getUserDocRepository()) {
			if(userDoc.getId()==id) {
				return userDoc;
			}
		}
		return null;
		
	}
	
	public long getCountUniqUserId() {
		return userDocRepository.getUserDocRepository().stream().filter(distinctByKey(UserDoc::getUserId)).count();
	}
	
	public UserDoc updateWholeUserDocById(Long id, UserDoc userDoc) {
		List<UserDoc> repository = userDocRepository.getUserDocRepository();
		for(int i=0; i<repository.size();i++) {
			if(repository.get(i).getId()==id) {
				userDoc.setId(id);
				repository.set(i,userDoc);
				return userDoc;
			}
		}
		return null;
	}

	public UserDoc updatePartialUserDocById(Long id, Map<String,Object> fields) {
		List<UserDoc> repository = userDocRepository.getUserDocRepository();
		for(int i=0; i<repository.size();i++) {
			if(repository.get(i).getId()==id) {
				UserDoc userDoc=repository.get(i);
				if(fields.containsKey("title")) {userDoc.setTitle(fields.get("title").toString());}
				if(fields.containsKey("body")) {userDoc.setBody(fields.get("body").toString());}
				if(fields.containsKey("userId")) {userDoc.setUserId(Integer.parseInt(fields.get("userId").toString()));}
				 
				repository.set(i,userDoc);
				return userDoc;
				}
			}
		return null;
	
	}
	
	public List<UserDoc> getUserDocsByUserId(int userId){
		return userDocRepository.getUserDocRepository().stream().filter(userDoc->userDoc.getUserId()==userId).collect(Collectors.toList());
	}

    private  static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    
	

}
