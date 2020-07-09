package org.codejudge.sb.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codejudge.sb.dto.Sucess;
import org.codejudge.sb.exception.CustomException;
import org.codejudge.sb.exception.CustomException404;
import org.codejudge.sb.model.User;

public interface UserService {

	boolean checkUsernameAlreadyExists(String username);

	User save(User user) throws CustomException;

	Sucess sendFrienRequest(String usera, String userb) throws CustomException;

	Map<String, List<String>> getFriendsRequests(String usera) throws CustomException, CustomException404;

	Map<String, List<String>> getFriends(String usera) throws CustomException, CustomException404;

	Map<String, Set<String>> getSuggestions(String usera) throws CustomException, CustomException404;

}
