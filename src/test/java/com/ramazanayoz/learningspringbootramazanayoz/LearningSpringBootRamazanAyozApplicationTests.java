package com.ramazanayoz.learningspringbootramazanayoz;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.ramazanayoz.learningspringbootramazanayoz.clientproxy.IUserResourceV1;
import com.ramazanayoz.learningspringbootramazanayoz.model.User;
import com.ramazanayoz.learningspringbootramazanayoz.model.User.Gender;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class LearningSpringBootRamazanAyozApplicationTests {
	
	@Autowired
	private IUserResourceV1 userResourceV1;
	
	@Test
	public void testFetchAllUsers() {
		String userUid = null;
		List<User> response = userResourceV1.fetchUser(userUid);
		assertThat(response).hasSizeGreaterThan(0);
		
		User joe = new User(null,"Joe","Jones",Gender.MALE, 22, "joe@gmail.com");
		
		assertThat(response.get(0)).isEqualToIgnoringGivenFields(joe, "userUid");
		assertThat(response.get(0).getUserUid()).isInstanceOf(UUID.class);
		assertThat(response.get(0).getUserUid()).isNotNull();
	}
	
	@Test
	public void testInsertUser() throws Exception {
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid,"Joe","Jones",Gender.MALE, 22, "joe@gmail.com");
	
		userResourceV1.insertNewUser(user);
		
		User response = userResourceV1.fetchUser(userUid);
		assertThat(response).isEqualToComparingFieldByField(user);
	}
	
	@Test
	public void testDeleteUser() {
		UUID userUuid = UUID.randomUUID();
		User user = new User(userUuid,"Bill","Jones",Gender.FEMALE, 22, "joe@gmail.com");
		
		userResourceV1.insertNewUser(user);
		
		User response = userResourceV1.fetchUser(userUuid);
		assertThat(response).isEqualToComparingFieldByField(user);
		
		userResourceV1.deleteUser(userUuid);
		
		assertThatThrownBy(() -> userResourceV1.fetchUser(userUuid)).isInstanceOf(NotFoundException.class);
	}
	
	@Test
	public void shouldFetchUsersByGender() throws Exception {
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid,"Joe","Jones",Gender.MALE, 22, "joe@gmail.com");
		
		userResourceV1.insertNewUser(user);
		
		List<User> females = userResourceV1.fetchUser(Gender.FEMALE.name());
		
		assertThat(females).extracting("userUid").doesNotContain(user.getUserUid());
		assertThat(females).extracting("firstName").doesNotContain(user.getFirstName());
		assertThat(females).extracting("lastName").doesNotContain(user.getLastName());
		assertThat(females).extracting("gender").doesNotContain(user.getGender());
		assertThat(females).extracting("age").doesNotContain(user.getAge());
		assertThat(females).extracting("email").doesNotContain(user.getEmail());
		
		List<User> males = userResourceV1.fetchUser(Gender.MALE.name());

		assertThat(males).extracting("userUid").contains(user.getUserUid());
		assertThat(males).extracting("firstName").contains(user.getFirstName());
		assertThat(males).extracting("lastName").contains(user.getLastName());
		assertThat(males).extracting("gender").contains(user.getGender());
		assertThat(males).extracting("age").contains(user.getAge());
		assertThat(males).extracting("email").contains(user.getEmail());
	}
}
