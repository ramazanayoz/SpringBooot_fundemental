package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import model.User;
import model.User.Gender;

@Repository
public class FakeDataDao implements UserDao {
	
	private static Map<UUID, User> database;
	
	public FakeDataDao() {
		database = new HashMap<>();
		UUID joeUserUuid = UUID.randomUUID();
		database.put(joeUserUuid, new User(joeUserUuid,"Joe", "Jones",Gender.MALE, 22, "joe@gmail.com"));
	}

	@Override
	public List<User> selectAllUsers() {
		return new ArrayList<User>(database.values());
	}

	@Override
	public Optional<User> selectUserByUserUid(UUID userUid) {
		return Optional.ofNullable(database.get(userUid));
	}

	@Override
	public int updateUser(User user) {
		database.put(user.getUserUid(), user);
		return 1;
	}

	@Override
	public int deleteUserByUserUid(UUID userUid) {
		database.remove(userUid);
		return 1;
	}

	@Override
	public int insertUser(UUID userUid, User user) {
		database.put(userUid, user);
		return 1;
	}
	
	
}
