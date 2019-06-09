package com.linym.flowerservice.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

import com.linym.flowerservice.exceptions.UserDocNotFoundException;
import com.linym.flowerservice.models.UserDoc;
import com.linym.flowerservice.repositories.UserDocRepository;

@SpringBootTest
//@TestMethodOrder(OrderAnnotation.class)
class UserDocServiceTest {
	
	@Mock
	private UserDocRepository userDocRepository;
	
	@InjectMocks //auto inject UserDocRepository
	private UserDocService userDocService;
	
	static List<UserDoc> mockUserDocRepository = new ArrayList<>(); 
	static UserDoc expectedUserDoc;

	@BeforeAll
	static void init() {
		expectedUserDoc = new UserDoc(1L,1,"testTitle1","testBody one");
		
		mockUserDocRepository.add(expectedUserDoc);
		mockUserDocRepository.add(new UserDoc(2L,1,"testTitle2","testBody two"));
		mockUserDocRepository.add(new UserDoc(3L,2,"testTitle3","testBody three"));
		mockUserDocRepository.add(new UserDoc(4L,2,"testTitle4","testBody four"));
		mockUserDocRepository.add(new UserDoc(5L,3,"testTitle5","testBody five"));
		mockUserDocRepository.add(new UserDoc(6L,3,"testTitle6","testBody six"));
		mockUserDocRepository.add(new UserDoc(7L,4,"testTitle7","testBody seven"));
		mockUserDocRepository.add(new UserDoc(8L,4,"testTitle8","testBody eight"));
		mockUserDocRepository.add(new UserDoc(9L,5,"testTitle9","testBody nine"));
		mockUserDocRepository.add(new UserDoc(10L,5,"testTitle10","testBody ten"));
	}

//	@AfterAll
//	static void tearDownAfterClass() throws Exception {
//	}
//
	@BeforeEach
	void setUp() throws Exception {
		when(userDocRepository.getUserDocRepository()).thenReturn(mockUserDocRepository);
	}

//	@AfterEach
//	void tearDown() throws Exception {
//	}
	
    @DisplayName("Test UserDocService getUserDocRepository")
	@Test
	void testGetUserDocService() {
    	
		assertEquals(10,userDocService.getUserDocRepository().size());
	}

    @DisplayName("Test UserDocService getUserDocByID")
	@Test
	void testGetUserDocByID() throws UserDocNotFoundException {
    	assertEquals(expectedUserDoc,userDocService.getUserDocByID(1L));
		
	}

    @DisplayName("Test UserDocService getCountUniqUserId")
	@Test
	void testGetCountUniqUserId() {
    	assertEquals(5,userDocService.getCountUniqUserId());
	}

    @DisplayName("Test UserDocService updateWholeUserDocById")
	@Test
	void testUpdateWholeUserDocById() throws UserDocNotFoundException {
		UserDoc updatedUserDoc = new UserDoc(4L,2,"updatedFullTitle","updatedFullBody four");
		assertEquals(updatedUserDoc,userDocService.updateWholeUserDocById(4L, updatedUserDoc));
	}

    @DisplayName("Test UserDocService updatePartialUserDocById")
	@Test
	void testUpdatePartialUserDocById() throws UserDocNotFoundException {
		Map<String,Object> updatedFields = new HashMap<>();
		updatedFields.put("title", "updatedPartialTitle");
		updatedFields.put("body", "updatedPartialBody four");
		UserDoc updatedUserDoc = new UserDoc(2L,1,"updatedPartialTitle","updatedPartialBody four");
		assertEquals(updatedUserDoc,userDocService.updatePartialUserDocById(2L, updatedFields));
	}
   
    @DisplayName("Test UserDocService getUserDocsByUserId")
	@Test
	void testGetUserDocsByUserId() {
		assertEquals(2,userDocService.getUserDocsByUserId(1).size());
	}
    
    @DisplayName("Test UserDocService createUserDoc")
   	@Test
   	void testCreateUserDoc() {
    	Map<String,Object> newDocFields = new HashMap<>();
		newDocFields.put("title", "NewTitle");
		newDocFields.put("body", "New Body eleven");
		newDocFields.put("userId", 6);
		UserDoc expectedUserDoc=new UserDoc(11L,6,"NewTitle","New Body eleven");
   		assertEquals(expectedUserDoc,userDocService.createUserDoc(newDocFields));
   	}
    
    @DisplayName("Test UserDocService deleteDocsByUserId userDocNotFoundException")
   	@Test
   	void testDeleteUserDocsByUserId() {
    	 Assertions.assertThrows(UserDocNotFoundException.class,() -> userDocService.deleteUserDoc(11L));
    
   	}

}
