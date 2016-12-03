package de.hska.exablog.backendapcore;

        import java.util.ArrayList;
        import java.util.Collection;
        import java.util.Comparator;

public interface GenericDatabase {

    class User {
        public final String name;
        public final String firstName;
        public final String lastName;
        // TODO add other metadata and set in constructor

        public User (String name, String firstName, String lastName) {
            this.name = name;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    class Post{
        public final long postID;
        public String content;
        public final String username;
        public final long timestamp;

        public Post (long id, String content, String username, long timestamp) {
            this.postID = id;
            this.username = username;
            this.timestamp = timestamp;
            this.content = content;
        }
    }

    class StringComparatorByContainedLong implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return this.mapLongToInt(Long.parseLong(o1) - Long.parseLong(o2));
        }

        private int mapLongToInt (long l) {
            if (l > Integer.MAX_VALUE) return Integer.MAX_VALUE;
            if (l < Integer.MIN_VALUE) return Integer.MIN_VALUE;
            return (int) l;
        }

    }

    class Timeline extends ArrayList<Post> {

        /**
         *
         */
        private static final long serialVersionUID = 3760009921889581657L;

        public Timeline () {
            super();
        }

        public Timeline (Collection<Post> posts) {
            this();
            this.addAll(posts);
        }

    }

    class AlreadyExistsException extends RuntimeException {
        public AlreadyExistsException() {
            super();
        }

        public AlreadyExistsException(String message) {
            super(message);
        }
    }

    Timeline getGlobalTimeline();

    User getUserdata (String username);

    Timeline getPersonalTimeline (User user);

    boolean validateSession (User user);

    boolean validateSession (User user, String password);

    Iterable<User> getFollowers (User user);

    Iterable<User> getFollowed (User user);

    boolean toggleFollowing (User follower, User following);

    User registerUser(String userName, String firstName, String lastName, String password, String backupCredentialQuestion, String backupCredentialAnswer);

    Post createPost (User user, String content);

}