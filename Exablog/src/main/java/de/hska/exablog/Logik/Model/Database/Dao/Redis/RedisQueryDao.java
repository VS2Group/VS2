package de.hska.exablog.Logik.Model.Database.Dao.Redis;

import de.hska.exablog.Logik.Config.RedisConfig;
import de.hska.exablog.Logik.Model.Database.Dao.IQueryDao;
import de.hska.exablog.Logik.Model.Database.RedisDatabase;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 07.12.2016.
 */
@Repository
@Qualifier("RedisDatabase")
public class RedisQueryDao implements IQueryDao {

	@Autowired
	private RedisDatabase database;
	@Autowired
	private UserService userService;

	@Override
	public Iterable<User> doSearch(String query) {
		query = "username:" + query;
		RedisZSetCommands.Range range = RedisZSetCommands.Range.range().gte(query).lt(this.nextUpper(query));
		Set<String> foundUsers = database.getAllUsersSortedOps().rangeByLex(RedisConfig.KEY_FOR_SORTED_USERS, range);
		Set<User> result = new HashSet<>();
		for (String username : foundUsers) {
			String userKey = "user:" + username;
			result.add(User.getBuilder()
					.setUsername(username)
					.setFirstName(database.getUserDataOps().get(userKey, "firstname"))
					.setLastName(database.getUserDataOps().get(userKey, "lastname"))
					.setImageUrl(database.getUserDataOps().get(userKey, "imageurl"))
					.setPassword(database.getUserDataOps().get(userKey, "password"))
					.setUserService(userService)
					.build());
		}
		return result;
	}

	private String nextUpper(String original) {
		String nextString = original.substring(0, original.length() - 1); // entire String except last character
		nextString += original.charAt(original.length() - 1) + 1; // append successor of original last character
		return nextString;
	}

}
