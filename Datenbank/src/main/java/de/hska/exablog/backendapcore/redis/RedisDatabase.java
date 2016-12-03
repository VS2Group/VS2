package de.hska.exablog.backendapcore.redis;

import de.hska.exablog.backendapcore.GenericDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ehx-v1
 */
@Repository
public class RedisDatabase implements GenericDatabase {

    private final static String KEY_FOR_ALL_USERS = "all_users";
    private final static String KEY_FOR_LATEST_POSTS = "latest_post_ids";
    private final static int TIMELINE_LIMIT = 10;
    private final static long SESSION_TIMEOUT_MINUTES = 30;
    private StringRedisTemplate template;
    private HashOperations<String, String, String> userData;
    private SetOperations<String, String> allUsers;
    private HashOperations<String, String, String> postData;
    private SetOperations<String, String> userPosts;
    private SetOperations<String, String> latestPosts;
    private SetOperations<String, String> userFollowers;
    private SetOperations<String, String> userFollowed;
    private HashOperations<String, String, String> registeredLogins;
    private ValueOperations<String, String> currentLogins;
    private RedisAtomicLong postID;

    @Autowired
    public RedisDatabase(StringRedisTemplate stringTemplate) {
        this.template = stringTemplate;
        this.postID = new RedisAtomicLong("postid", stringTemplate.getConnectionFactory());
    }

    @PostConstruct
    private void init() {
        userData = template.opsForHash();
        allUsers = template.opsForSet();
        postData = template.opsForHash();
        userPosts = template.opsForSet();
        latestPosts = template.opsForSet();
        userFollowers = template.opsForSet();
        userFollowed = template.opsForSet();
        registeredLogins = template.opsForHash();
        currentLogins = template.opsForValue();
    }

    @Override
    public Timeline getGlobalTimeline() {
        Set<String> postIds = latestPosts.members(KEY_FOR_LATEST_POSTS);
        Timeline globalTimeline = new Timeline();
        for (String postId: postIds) {
            if (globalTimeline.size() >= TIMELINE_LIMIT) break;
            String postKey = "post:" + postId;
            globalTimeline.add(new Post(Long.parseLong(postId), postData.get(postKey, "content"), postData.get(postKey, "username"), Long.parseLong(postData.get(postKey, "timestamp"))));
        }
        return globalTimeline;
    }

    @Override
    public User getUserdata(String username) {
        String key = "user:" + username;
        if (!allUsers.isMember(KEY_FOR_ALL_USERS, key)) return null;
        return new User (userData.get(key, "username"), userData.get(key, "firstname"), userData.get(key, "lastname"));
    }

    @Override
    public Timeline getPersonalTimeline(User user) {
        Set<String> postIds = this.getUserPostIds(user);
        for (User followed : this.getFollowed(user)) {
            postIds.addAll(this.getUserPostIds(followed));
        }
        List<String> postIdList = new ArrayList<>();
        postIdList.addAll(postIds);
        postIdList.sort(new StringComparatorByContainedLong());
        Timeline personalTimeline = new Timeline();
        for (String postId: postIdList) {
            if (personalTimeline.size() >= TIMELINE_LIMIT) break;
            String postKey = "post:" + postId;
            personalTimeline.add(new Post(Long.parseLong(postId), postData.get(postKey, "content"), postData.get(postKey, "username"), Long.parseLong(postData.get(postKey, "timestamp"))));
        }
        return personalTimeline;
    }

    private Set<String> getUserPostIds (User user) {
        String key = user.name + ":posts";
        return userPosts.members(key);
    }

    @Override
    public boolean validateSession(User user) {
        String simpleLoginTokenKey = "session:" + user.name;
        if (currentLogins.get(simpleLoginTokenKey) == null) return false;
        template.expire(simpleLoginTokenKey, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES); // reset session timeout, since session still exists
        return true;
    }

    @Override
    public boolean validateSession(User user, String password) {
        if (this.validateSession(user)) return true;
        String loginKey = "login:" + user.name;
        return registeredLogins.get(loginKey, "password").equals(password);
    }

    @Override
    public Iterable<User> getFollowers(User user) {
        String key = user.name + ":follower";
        return this.extractUsers(userFollowers.members(key));
    }

    @Override
    public Collection<User> getFollowed(User user) {
        String key = user.name + ":following";
        return this.extractUsers(userFollowed.members(key));
    }

    private Set<User> extractUsers (Set<String> userNames) {
        Set<User> users = new HashSet<>();
        for (String userName : userNames) {
            users.add(this.getUserdata(userName));
        }
        return users;
    }

    @Override
    public boolean toggleFollowing(User follower, User following) {
        if (this.getFollowed(follower).contains(following)) {
            userFollowers.remove(following.name + ":follower", follower.name);
            userFollowed.remove(follower.name + ":following", following.name);
            return false;
        } else {
            userFollowers.add(following.name + ":follower", follower.name);
            userFollowed.add(follower.name + ":following", following.name);
            return true;
        }
    }

    @Override
    public User registerUser(String userName, String firstName, String lastName, String password, String backupCredentialQuestion, String backupCredentialAnswer) {
        String userKey = "user:" + userName;
        if (allUsers.isMember(KEY_FOR_ALL_USERS, userKey)) throw new AlreadyExistsException("User name " + userName + " is already registered. Please choose a different one.");
        allUsers.add(KEY_FOR_ALL_USERS, userKey);
        userData.put(userKey, "username", userName);
        userData.put(userKey, "firstname", firstName);
        userData.put(userKey, "lastname", lastName);
        String loginKey = "login:" + userName;
        registeredLogins.put(loginKey, "password", password); // OPTTODO encrypt passwords
        registeredLogins.put(loginKey, "bucquestion", backupCredentialQuestion);
        registeredLogins.put(loginKey, "bucanswer", backupCredentialAnswer);
        String simpleLoginTokenKey = "session:" + userName;
        String simpleLoginToken = userName + ":" + firstName + ":" + lastName;
        currentLogins.set(simpleLoginTokenKey, simpleLoginToken, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        return new User (userName, firstName, lastName);
    }

    @Override
    public Post createPost(User user, String content) {
        long postID = this.postID.incrementAndGet();
        long timestamp = System.currentTimeMillis();
        String postKey = "post:" + postID;
        postData.put(postKey, "id", Long.toString(postID));
        postData.put(postKey, "content", content);
        postData.put(postKey, "username", user.name);
        postData.put(postKey, "timestamp", Long.toString(timestamp));
        return new Post (postID, user.name, content, timestamp);
    }
}
