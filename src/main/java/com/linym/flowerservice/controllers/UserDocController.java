package com.linym.flowerservice.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linym.flowerservice.models.UserDoc;
import com.linym.flowerservice.services.UserDocService;

@RestController
@RequestMapping("/userDocs")
@ExposesResourceFor(UserDoc.class)
public class UserDocController {
	@Autowired
	private UserDocService userDocService;
	@Autowired
	private EntityLinks entityLinks;
	
	@GetMapping
	public ResponseEntity<List<Resource<UserDoc>>> getUserDocs(
			@RequestParam(required = false) String orderby,
			@RequestParam(required=false) Integer userId){
		
		List<UserDoc> userDocs=userDocService.getUserDocRepository();
		if(userId != null) {
			userDocs=userDocService.getUserDocsByUserId(userId);
		}
		if("title".equals(orderby)) {
			userDocs.sort((userDoc1,userDoc2)->userDoc1.getTitle().compareTo(userDoc2.getTitle()));
		}
		List<Resource<UserDoc>> userDocsResources=new ArrayList<>(); 
		 
		for(UserDoc userDoc:userDocs) {
			Resource<UserDoc> resource= new Resource<UserDoc>(userDoc);
			resource.add(entityLinks.linkToSingleResource(UserDoc.class, userDoc.getId()));
			userDocsResources.add(resource);
		}
		
		
      
		return new ResponseEntity<List<Resource<UserDoc>>>(userDocsResources,HttpStatus.OK);

	}
	 
	
	@GetMapping("/userCount")
    public ResponseEntity<Long> getUniqUserIdCount() {
		return new ResponseEntity<Long>(userDocService.getCountUniqUserId(),HttpStatus.OK);

     }
	

	@GetMapping("{id}")
	public ResponseEntity<Resource<UserDoc>> getUserDocById(@PathVariable long id){
		UserDoc userDoc=userDocService.getUserDocByID(id);
		Resource<UserDoc> resource= new Resource<UserDoc>(userDoc);
		resource.add(entityLinks.linkToSingleResource(userDoc));
		return new ResponseEntity<Resource<UserDoc>>(resource,HttpStatus.OK);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<Resource<UserDoc>> partialUpdateUserDoc(@PathVariable long id, @RequestBody Map<String,Object> fields){
		UserDoc newUserDoc=userDocService.updatePartialUserDocById(id, fields);
		Resource<UserDoc> resource= new Resource<UserDoc>(newUserDoc);
		resource.add(entityLinks.linkToSingleResource(newUserDoc));
		return new ResponseEntity<Resource<UserDoc>>(resource,HttpStatus.OK);
	}
    
	@PutMapping("{id}")
	public ResponseEntity<Resource<UserDoc>> updateUserDoc(@PathVariable long id, @RequestBody UserDoc userDoc){
		UserDoc newUserDoc=userDocService.updateWholeUserDocById(id, userDoc);
		Resource<UserDoc> resource= new Resource<UserDoc>(newUserDoc);
		resource.add(entityLinks.linkToSingleResource(newUserDoc));
		return new ResponseEntity<Resource<UserDoc>>(resource,HttpStatus.OK);
	}


}
