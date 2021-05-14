package com.ramazanayoz.learningspringbootramazanayoz.model;

import java.util.UUID;

public class User {
	
	//user id
	private UUID userUid;
	private final String fistName;
	private final String lastName;
	private final Gender gender;
	private final Integer age;
	private final String email;
	
	public User(UUID userUid, String fistName, String lastName, Gender gender, Integer age, String email) {
		this.userUid = userUid;
		this.fistName = fistName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.email = email;
	}
	
	public enum Gender{
		MALE,
		FEMALE
	}

	public UUID getUserUid() {
		return userUid;
	}
	
	public void setUserUid(UUID userUid) {
		this.userUid = userUid;
	}

	public String getFistName() {
		return fistName;
	}

	public String getLastName() {
		return lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public Integer getAge() {
		return age;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "User [userUid=" + userUid + ", fistName=" + fistName + ", lastName=" + lastName + ", gender=" + gender
				+ ", age=" + age + ", email=" + email + "]";
	}
	
	

}
