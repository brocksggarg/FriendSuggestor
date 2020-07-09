package org.codejudge.sb.repo;

import java.util.List;

import org.codejudge.sb.model.Friends;
import org.codejudge.sb.model.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepo extends JpaRepository<Friends, FriendshipId> {

	@Query(nativeQuery = true, value = "select * from friends where usera=:usera and userb=:userb")
	Friends getById(@Param("usera") String usera, @Param("userb") String userb);

	@Query(nativeQuery = true, value = "select * from friends where userb=:usera and status='pending'")
	List<Friends> getPendingRequests(@Param("usera") String usera);
	
	@Query(nativeQuery = true, value = "select * from friends where usera=:usera and status='friends'")
	List<Friends> getFriends(@Param("usera") String usera);

}
