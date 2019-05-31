package com.linym.flowerservice.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import com.linym.flowerservice.models.UserDoc;
import com.linym.flowerservice.services.UserDocService;

@RestController
@RequestMapping("/userDocs")
class UserDocControllerTest {
	
	@InjectMocks
	UserDocController userDocController;
	
	@Mock
	UserDocService userDocService;
	
	@Mock
	EntityLinks entityLinks;

	
	List<UserDoc> mockUserDocRepository = new ArrayList<>(); 
	UserDoc mockUserDoc;
	Link mockLink;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockUserDoc=new UserDoc(1L,1,"testTitle1","testBody one");
		
		mockUserDocRepository.add(mockUserDoc);
		mockUserDocRepository.add(new UserDoc(2L,1,"testTitle2","testBody two"));
		mockUserDocRepository.add(new UserDoc(3L,2,"testTitle3","testBody three"));
		mockUserDocRepository.add(new UserDoc(4L,2,"testTitle4","testBody four"));
		mockUserDocRepository.add(new UserDoc(5L,3,"testTitle5","testBody five"));
		mockUserDocRepository.add(new UserDoc(6L,3,"testTitle6","testBody six"));
		mockUserDocRepository.add(new UserDoc(7L,4,"testTitle7","testBody seven"));
		mockUserDocRepository.add(new UserDoc(8L,4,"testTitle8","testBody eight"));
		mockUserDocRepository.add(new UserDoc(9L,5,"testTitle9","testBody nine"));
		mockUserDocRepository.add(new UserDoc(10L,5,"testTitle10","testBody ten"));
		
		mockLink=new Link("http://localhost:8080/userDocs/1L");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@DisplayName("Test UserDocController getUserDocs")
	@Test
	void testGetUserDocs() {
		when(userDocService.getUserDocRepository()).thenReturn(mockUserDocRepository);
		when(entityLinks.linkToSingleResource(any(Class.class), anyLong())).thenReturn(mockLink);
		ResponseEntity<List<Resource<UserDoc>>> responseEntity = userDocController.getUserDocs();
		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		assertEquals(10,responseEntity.getBody().size());
	}

	@DisplayName("Test UserDocController getUniqUserIdCount")
	@Test
	void testGetUniqUserIdCount() {
		when(userDocService.getCountUniqUserId()).thenReturn(5L);
		ResponseEntity<Long> responseEntity=userDocController.getUniqUserIdCount();
		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		assertEquals(5L,responseEntity.getBody().longValue());
	}
	
    
	@DisplayName("Test UserDocController getUserDocById")
	@Test
	void testGetUserDocById() {
		when(userDocService.getUserDocByID(1L)).thenReturn(mockUserDoc);
		when(entityLinks.linkToSingleResource(any())).thenReturn(mockLink);
		ResponseEntity<Resource<UserDoc>> responseEntity = userDocController.getUserDocById(1L);
		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		assertEquals(mockUserDoc,responseEntity.getBody().getContent());
		assertEquals(mockLink,responseEntity.getBody().getId());
	}

	@DisplayName("Test UserDocController partialUpdateUserDoc")
	@Test
	void testPartialUpdateUserDoc() {
		
		Map<String,Object> updateFields=new HashMap<>();
		updateFields.put("title", "updatedTitle");
		updateFields.put("body", "updatedBody");
		
		UserDoc updatedUserDoc=new UserDoc(1L,1,"updatedTitle","updatedBody");
		
		when(userDocService.updatePartialUserDocById(1L, updateFields)).thenReturn(updatedUserDoc);
		when(entityLinks.linkToSingleResource(any())).thenReturn(mockLink);
		
		ResponseEntity<Resource<UserDoc>> responseEntity = userDocController.partialUpdateUserDoc(1L,updateFields);
		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		assertEquals(updatedUserDoc,responseEntity.getBody().getContent());
		assertEquals(mockLink,responseEntity.getBody().getId());
	}
	
    @DisplayName("Test UserDocController updateUserDoc")
	@Test
	void testUpdateUserDoc() {
        UserDoc updatedUserDoc=new UserDoc(1L,1,"updatedTitle","updatedBody");
		
		when(userDocService.updateWholeUserDocById(1L, updatedUserDoc)).thenReturn(updatedUserDoc);
		when(entityLinks.linkToSingleResource(any())).thenReturn(mockLink);
		
		ResponseEntity<Resource<UserDoc>> responseEntity = userDocController.updateUserDoc(1L, updatedUserDoc);
		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		assertEquals(updatedUserDoc,responseEntity.getBody().getContent());
		assertEquals(mockLink,responseEntity.getBody().getId());
	}

}
