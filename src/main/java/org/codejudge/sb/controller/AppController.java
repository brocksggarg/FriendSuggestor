package org.codejudge.sb.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.codejudge.sb.dto.Sucess;
import org.codejudge.sb.exception.CustomException;
import org.codejudge.sb.exception.CustomException404;
import org.codejudge.sb.model.User;
import org.codejudge.sb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping
public class AppController {

	@Autowired
	private UserService userService;

	@ApiOperation("This is the hello world api")
	@GetMapping("/")
	public String hello() {
		return "Hello World!!";
	}

	@PostMapping("/create")
	public ResponseEntity<Object> create(@Valid @RequestBody User user) throws CustomException {

		User savedUser = userService.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	@PostMapping("/add/{usera}/{userb}")
	public ResponseEntity<Object> friendRequest(@PathVariable String usera, @PathVariable String userb)
			throws CustomException {
		Sucess sucess = userService.sendFrienRequest(usera, userb);
		return new ResponseEntity<>(sucess, HttpStatus.ACCEPTED);
	}

	@GetMapping("/friendRequests/{usera}")
	public ResponseEntity<Object> getFriendsRequests(@PathVariable String usera)
			throws CustomException, CustomException404 {
		Map<String, List<String>> map = userService.getFriendsRequests(usera);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping("/friends/{usera}")
	public ResponseEntity<Object> getFriends(@PathVariable String usera) throws CustomException, CustomException404 {
		Map<String, List<String>> map = userService.getFriends(usera);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping("/suggestions/{usera}")
	public ResponseEntity<Object> getSuggestions(@PathVariable String usera)
			throws CustomException, CustomException404 {
		Map<String, Set<String>> map = userService.getSuggestions(usera);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
