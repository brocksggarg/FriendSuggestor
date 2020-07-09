package org.codejudge.sb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class Friends implements Serializable {

    private static final long serialVersionUID = -6840703970225099920L;

    public Friends(FriendshipId friendshipId2, String string) {
        friendshipId = friendshipId2;
        status = string;
    }

    public Friends() {
        super();
    }

    @EmbeddedId
    private FriendshipId friendshipId;

    @Column
    private String status;
}
