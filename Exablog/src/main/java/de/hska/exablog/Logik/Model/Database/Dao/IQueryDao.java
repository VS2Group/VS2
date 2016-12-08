package de.hska.exablog.Logik.Model.Database.Dao;

import de.hska.exablog.Logik.Model.Entity.User;

/**
 * Created by root on 07.12.2016.
 */
public interface IQueryDao {

    Iterable<User> doSearch (String query);

}
