package me.dio.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import me.dio.domain.model.User;
import me.dio.domain.model.dto.UserDTO;
import me.dio.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		List<User> users = userService.FindAll();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable long id){
		var user = userService.findById(id);
		return ResponseEntity.ok(user);
	}
	
	 @PostMapping
	 public ResponseEntity<User> create(@RequestBody UserDTO userToCreate) {
		 var userCreated = userService.create(userToCreate.toModel());
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	         .path("/{id}")
	                .buildAndExpand(userCreated.getId())
	                .toUri();
	   return ResponseEntity.created(location).body(userCreated);
	 }
	 
	 @PutMapping("/{id}")
	 public ResponseEntity<User> update(@PathVariable long id, @RequestBody UserDTO userChanges){
		 var user = userService.update(id, userChanges);
		 
		 
		 return ResponseEntity.ok(user);
	 }
	 
	 @DeleteMapping("/{id}")
	 public ResponseEntity<Void> deleteById(@PathVariable long id){
		 userService.delete(id);
		 return ResponseEntity.noContent().build();
	 }
	 
	 
}