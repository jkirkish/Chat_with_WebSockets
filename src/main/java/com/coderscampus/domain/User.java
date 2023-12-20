package com.coderscampus.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class User {

	@Id
	private String nickName;
	private String fullName;
	private Status status;

}