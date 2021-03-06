package com.linym.flowerservice.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linym.flowerservice.exceptions.UserDocNotFoundException;
import com.linym.flowerservice.models.UserDoc;
import com.linym.flowerservice.repositories.UserDocRepository;


@Service
public class UserDocService {
	
	static final  Logger logger=LoggerFactory.getLogger(UserDocService.class);
	
	@Autowired
	UserDocRepository userDocRepository;
	
	public List<UserDoc> getUserDocRepository(){
		return userDocRepository.getUserDocRepository();
	}
	
	
	public UserDoc getUserDocByID(long id) throws UserDocNotFoundException {
		for(UserDoc userDoc:userDocRepository.getUserDocRepository()) {
			if(userDoc.getId()==id) {
				return userDoc;
			}
		}
		
		String msg="cannot find userDoc with id="+id;
		logger.warn(msg);
		throw new UserDocNotFoundException(msg);
		
	}
	
	public long getCountUniqUserId() {
		// Retrieve number of distinct userId from repository
		return userDocRepository.getUserDocRepository().stream().filter(distinctByKey(UserDoc::getUserId)).count();
	}
	
	public UserDoc updateWholeUserDocById(Long id, UserDoc userDoc) throws UserDocNotFoundException{
		List<UserDoc> repository = userDocRepository.getUserDocRepository();
		for(int i=0; i<repository.size();i++) {
			if(repository.get(i).getId()==id) {
				//should keep the origin id
				userDoc.setId(id);
				repository.set(i,userDoc);
				return userDoc;
			}
		}
		String msg="cannot update whole userDoc with id="+id;
		logger.warn(msg);
		throw new UserDocNotFoundException(msg);
	}

	public UserDoc updatePartialUserDocById(Long id, Map<String,Object> fields) throws UserDocNotFoundException {
		List<UserDoc> repository = userDocRepository.getUserDocRepository();
		for(int i=0; i<repository.size();i++) {
			if(repository.get(i).getId()==id) {
				UserDoc userDoc=repository.get(i);
				//should not change id filed
				if(fields.containsKey("title")) {userDoc.setTitle(fields.get("title").toString());}
				if(fields.containsKey("body")) {userDoc.setBody(fields.get("body").toString());}
				if(fields.containsKey("userId")) {userDoc.setUserId(Integer.parseInt(fields.get("userId").toString()));}
				 
				repository.set(i,userDoc);
				return userDoc;
				}
			}
		String msg="cannot update partial userDoc with id="+id;
		logger.warn(msg);
		throw new UserDocNotFoundException(msg);
	}
	
	public List<UserDoc> getUserDocsByUserId(int userId){
		
		return userDocRepository.getUserDocRepository().stream().filter(userDoc->userDoc.getUserId()==userId).collect(Collectors.toList());
	}
	
	public UserDoc createUserDoc(Map<String,Object> fields) {
		UserDoc userDoc=new UserDoc();
		Long newId = userDocRepository.getUserDocRepository().stream()
				     .max((userDoc1,userDoc2)->userDoc1.getId()
				     .compareTo(userDoc2.getId()))
				     .get().getId()+1;
		userDoc.setId(newId);
		if(fields.containsKey("title")) {userDoc.setTitle(fields.get("title").toString());}
		if(fields.containsKey("body")) {userDoc.setBody(fields.get("body").toString());}
		if(fields.containsKey("userId")) {userDoc.setUserId(Integer.parseInt(fields.get("userId").toString()));}
		return userDoc;
	}
	
	public void deleteUserDoc(Long id) throws UserDocNotFoundException {
		boolean deleted=false;
		List<UserDoc> userDocs = userDocRepository.getUserDocRepository();
		for(int i=0; i<userDocs.size(); i++) {
			if(userDocs.get(i).getId()==id) {
				userDocs.remove(i);
				deleted=true;
				break;
			}
		}
		if(!deleted){
			String msg="cannot delete userDoc with id="+id;
			logger.warn(msg);
			throw new UserDocNotFoundException(msg);
		}
		
	}

    private  static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    
	

}
