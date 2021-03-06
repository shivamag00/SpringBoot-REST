package com.example.webservices.restfulwebservices.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.webservices.restfulwebservices.bean.User;
import com.example.webservices.restfulwebservices.exception.UserNotFoundException;
import com.example.webservices.restfulwebservices.service.UserDaoService;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if(user==null)
			throw new UserNotFoundException("id-"+ id);
		
		return user;
	}
	
	//
	// input - details of user
	// output - CREATED & Return the created URI
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody User user){
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
			
		return ResponseEntity.created(location).build();
	}
	
}








