package org.codejudge.sb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codejudge.sb.dto.Sucess;
import org.codejudge.sb.exception.CustomException;
import org.codejudge.sb.exception.CustomException404;
import org.codejudge.sb.model.Friends;
import org.codejudge.sb.model.FriendshipId;
import org.codejudge.sb.model.User;
import org.codejudge.sb.repo.FriendRepo;
import org.codejudge.sb.repo.UserRepository;
import org.codejudge.sb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FriendRepo friendRepo;

	private Set<String> usernames = new HashSet<>();

	@Override
	public boolean checkUsernameAlreadyExists(String username) {
		return usernames.contains(username);
	}

	@Override
	public User save(User user) throws CustomException {
		if (usernames.contains(user.getUsername())) {
			throw new CustomException();
		}
		usernames.add(user.getUsername());
		return userRepository.save(user);
	}

	@Override
	public Sucess sendFrienRequest(String usera, String userb) throws CustomException {
		User u1 = userRepository.findOne(usera);
		User u2 = userRepository.findOne(userb);
		if (u1 == null || u2 == null) {
			throw new CustomException();
		}
		FriendshipId friendshipId = new FriendshipId(u1, u2);
		Friends friends = friendRepo.findOne(friendshipId);
		if (friends == null) {
			Friends newFriend = new Friends(friendshipId, "pending");
			friendshipId = new FriendshipId(u2, u1);
			friends = friendRepo.findOne(friendshipId);

			if (friends == null) {
				friendRepo.save(newFriend);
			} else {
				friends.setStatus("friends");
				newFriend.setStatus("friends");
				friendRepo.save(friends);
				friendRepo.save(newFriend);

			}
			return new Sucess("success");
		} else {
			throw new CustomException();
		}
	}

	@Override
	public Map<String, List<String>> getFriendsRequests(String usera) throws CustomException, CustomException404 {
		if (usernames.contains(usera)) {
			List<Friends> list = friendRepo.getPendingRequests(usera);
			if (list.size() == 0) {
				throw new CustomException404();
			} else {
				Map<String, List<String>> map = new HashMap<>();
				List<String> l = new ArrayList<String>();
				for (Friends friends : list) {
					l.add(friends.getFriendshipId().getUserA().getUsername());
				}
				map.put("friend_requests", l);
				return map;
			}
		} else {
			throw new CustomException();
		}
	}

	@Override
	public Map<String, List<String>> getFriends(String usera) throws CustomException, CustomException404 {
		if (usernames.contains(usera)) {
			List<Friends> list = friendRepo.getFriends(usera);
			if (list.size() == 0) {
				throw new CustomException404();
			} else {
				Map<String, List<String>> map = new HashMap<>();
				List<String> l = new ArrayList<String>();
				for (Friends friends : list) {
					l.add(friends.getFriendshipId().getUserB().getUsername());
				}
				map.put("friends", l);
				return map;
			}
		} else {
			throw new CustomException();
		}
	}

	@Override
	public Map<String, Set<String>> getSuggestions(String usera) throws CustomException, CustomException404 {
		if (usernames.contains(usera)) {
			List<String> friendsList = getFriends(usera).get("friends");
			Set<String> suggestions = new HashSet<>();
			// degree 1
			for (String w : friendsList) {
				List<String> friendsListDegree1 = getFriends(w).get("friends");
				for (String q : friendsListDegree1) {
					suggestions.addAll(getFriends(q).get("friends"));
				}
				suggestions.addAll(friendsListDegree1);
			}
			suggestions.removeAll(friendsList);
			suggestions.remove(usera);
			Map<String, Set<String>> map = new HashMap<>();
			if (suggestions.size() == 0) {
				throw new CustomException404();
			} else {
				map.put("suggestions", suggestions);
				return map;
			}
		} else {
			throw new CustomException();
		}
	}

}
