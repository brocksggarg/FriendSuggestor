package org.codejudge.sb.dto;

import lombok.Data;

@Data
public class Sucess {

	public Sucess(String string) {
		status=string;
	}

	private String status;
}
