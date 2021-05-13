package dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.User;
import model.User.Gender;

public class FakeDataDaoTest {
	
	private FakeDataDao fakeDataDao;

	@Before
	public  void setUp() throws Exception {
		fakeDataDao = new FakeDataDao();
	}

	@Test
	public void shouldSelectAllUsers() {
		List<User> users = fakeDataDao.selectAllUsers();
		assertThat(users).hasSize(1);
		User user = users.get(0);
		assertThat(user.getAge()).isEqualTo(22);
		assertThat(user.getFistName()).isEqualTo("Joe");
		assertThat(user.getLastName()).isEqualTo("Jones");
		assertThat(user.getGender()).isEqualTo(Gender.MALE);
		assertThat(user.getEmail()).isEqualTo("joe@gmail.com");
		assertThat(user.getUserUid()).isNotNull();
	}

	@Test
	public void shouldSelectUserByUserUid() {
		UUID annaIserUid = UUID.randomUUID();
		User anna = new User(annaIserUid,"anna","montana",Gender.FEMALE, 30, "anna@gmail.com");
		fakeDataDao.insertUser(annaIserUid, anna);
		assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
		
		Optional<User> annaOptional = fakeDataDao.selectUserByUserUid(annaIserUid);
		assertThat(annaOptional.isPresent()).isTrue();
		assertThat(annaOptional.get()).isEqualToComparingFieldByField(anna);
	}
	
	@Test
	public void shouldNotSelectUserByRandomUserUid() throws Exception{
		Optional<User> user = fakeDataDao.selectUserByUserUid(UUID.randomUUID());
		assertThat(user.isPresent()).isFalse();
	}

	@Test
	public void shouldUpdateUser() throws Exception {
		UUID joeUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
		User newJoe = new User(joeUserUid,"anna","montana",Gender.FEMALE,30,"anna@gmail.com");
		fakeDataDao.updateUser(newJoe);
		Optional<User> user = fakeDataDao.selectUserByUserUid(joeUserUid);
		
		assertThat(user.isPresent()).isTrue();
		assertThat(user.get()).isEqualToComparingFieldByField(newJoe);
	}

	@Test
	public void deleteUserByUserUid() {
		UUID joeUserUuid = fakeDataDao.selectAllUsers().get(0).getUserUid();
		
		fakeDataDao.deleteUserByUserUid(joeUserUuid);
		
		assertThat(fakeDataDao.selectUserByUserUid(joeUserUuid).isPresent()).isFalse();
		assertThat(fakeDataDao.selectAllUsers()).isEmpty();
	}

	@Test
	public void shouldInsertUser() {
		UUID userUuid = UUID.randomUUID();
		User user = new User(userUuid,"anna","montana",Gender.FEMALE,30,"anna@gmail.com");
		
		fakeDataDao.insertUser(userUuid, user);
		
		List<User> users = fakeDataDao.selectAllUsers();
		assertThat(users).hasSize(2);
		assertThat(fakeDataDao.selectUserByUserUid(userUuid).get()).isEqualToComparingFieldByField(user);
	}

}
