package org.codejudge.sb.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2305974510902252309L;
	@Id
	@NotEmpty
	private String username;

}
