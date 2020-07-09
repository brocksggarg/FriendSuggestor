package org.codejudge.sb.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Embeddable
@Data
public class FriendshipId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7091023856570670785L;

	public FriendshipId(User u1, User u2) {
		userA = u1;
		userB = u2;
	}

	public FriendshipId() {
		super();
		// TODO Auto-generated constructor stub
	}

	@ManyToOne
	@JoinColumn(name = "userA")
	private User userA;

	@ManyToOne
	@JoinColumn(name = "userB")
	private User userB;
}
