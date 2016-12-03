package de.hska.exablog.backendapcore;

import de.hska.exablog.backendapcore.redis.RedisDatabase;

/**
 * Created by root on 03.12.2016.
 */
public class DatabaseFactory {

    public GenericDatabase createDatabase() {
        return this.createRedisDatabase();
    }

    public RedisDatabase createRedisDatabase() {

        return null; // TODO return an actual instance
    }

}
