package service;



import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import net.bytebuddy.asm.Advice.Argument;

import com.google.common.collect.ImmutableList;
import com.ramazanayoz.learningspringbootramazanayoz.dao.FakeDataDao;
import com.ramazanayoz.learningspringbootramazanayoz.model.User;
import com.ramazanayoz.learningspringbootramazanayoz.model.User.Gender;
import com.ramazanayoz.learningspringbootramazanayoz.service.UserService;


public class UserServiceTest {

	@Mock
	private FakeDataDao fakeDataDao;
	
	private UserService userService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		userService = new UserService(fakeDataDao);
	}

	@Test
	public void testUserService() {
	}

	@Test
	public void testGetAllUsers() {
		UUID annaUserUuid = UUID.randomUUID();
		
		User anna = new User(annaUserUuid,"anna","montana",Gender.FEMALE, 30, "anna@gmail.com");

		ImmutableList<User> users = new ImmutableList.Builder<User>()
				.add(anna)
				.build();
		
		BDDMockito.given(fakeDataDao.selectAllUsers()).willReturn(users);
		
		//service
		List<User> allUsers = userService.getAllUsers();
		assertThat(allUsers).hasSize(1);
	}

	@Test
	public void testGetUser() {
		UUID annaUuid = UUID.randomUUID();
		User anna = new User(annaUuid,"anna","montana",Gender.FEMALE, 30, "anna@gmail.com");		
		BDDMockito.given(fakeDataDao.selectUserByUserUid(annaUuid)).willReturn(Optional.of(anna));

		Optional<User> userOptional = userService.getUser(annaUuid);
		
		assertThat(userOptional.isPresent()).isTrue();
		User user = userOptional.get();
		assertThat(user.getFistName()).isEqualTo("anna");
	}

	@Test
	public void testUpdateUser() {
		//variables
		UUID annaUid = UUID.randomUUID();
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		User anna = new User(annaUid,"anna","montana",Gender.FEMALE, 30, "anna@gmail.com");		
		
		
		BDDMockito.given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));

		userService.insertUser(anna);

		int updateResult = userService.updateUser(anna);
				
		verify(fakeDataDao).updateUser(captor.capture());

		User user = captor.getValue();
		
		assertThat(user.getFistName()).isEqualTo("anna");
		
		assertThat(anna).isEqualToComparingFieldByField(captor.getValue());

		assertThat(updateResult).isEqualTo(1);
		
		verify(fakeDataDao).selectUserByUserUid(annaUid);
	}

	@Test
	public void testRemoveUser() {
		//variables
		UUID annaUid = UUID.randomUUID();
		User anna = new User(annaUid,"anna","montana",Gender.FEMALE, 30, "anna@gmail.com");		
		
		
		BDDMockito.given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));
		BDDMockito.given(fakeDataDao.deleteUserByUserUid(annaUid)).willReturn(1);
		
		
		int deleteResult = userService.removeUser(annaUid);
				
		verify(fakeDataDao).selectUserByUserUid(annaUid);
		verify(fakeDataDao).deleteUserByUserUid(annaUid);

		assertThat(deleteResult).isEqualTo(1);
		
	}

	@Test
	public void testInsertUser() {
		//variables
		UUID annaUid = UUID.randomUUID();
		User anna = new User(annaUid,"anna","montana",Gender.FEMALE, 30, "anna@gmail.com");		
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

		
		BDDMockito.given(fakeDataDao.insertUser(any(UUID.class), eq(anna))).willReturn(1);
		
		int insertResult = userService.insertUser(anna);
		
		verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());
		
		User user = captor.getValue();
		
		assertThat(user.getFistName()).isEqualTo(anna.getFistName());
		
		assertThat(insertResult).isEqualTo(1);
		
		assertThat(user.getUserUid()).isEqualTo(anna.getUserUid());

		assertThat(user.getUserUid()).isNotNull();
	}

}
