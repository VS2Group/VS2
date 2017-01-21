package de.hska.exablog.Logik.Model.Database.Dao.Redis;

import de.hska.exablog.Logik.Config.RedisConfig;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
import de.hska.exablog.Logik.Model.Database.Dao.IQueryDao;
import de.hska.exablog.Logik.Model.Database.RedisDatabase;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;


@Repository
@Qualifier("RedisDatabase")
public class RedisQueryDao implements IQueryDao {

	@Autowired
	private RedisDatabase database;
	@Autowired
	private UserService userService;

	@Override
	public Collection<User> doSearch(String query) {
		query = "user:" + query;
		RedisZSetCommands.Range range = RedisZSetCommands.Range.range().gte(query).lt(this.nextUpper(query));
		Set<String> foundUsers = database.getAllUsersSortedOps().rangeByLex(RedisConfig.KEY_FOR_SORTED_USERS, range);
		Set<User> result = new TreeSet<>();
		for (String username : foundUsers) {
			username = username.substring("user:".length());
			try {
				result.add(userService.getUserByName(username));
			} catch (UserDoesNotExistException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private String nextUpper(String original) {
		String nextString = original.substring(0, original.length() - 1); // entire String except last character
		nextString += (char) (original.charAt(original.length() - 1) + 1); // append successor of original last character
		return nextString;
	}

}
