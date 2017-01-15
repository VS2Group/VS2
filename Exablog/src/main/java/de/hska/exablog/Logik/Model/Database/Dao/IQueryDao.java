package de.hska.exablog.Logik.Model.Database.Dao;

import de.hska.exablog.Logik.Model.Entity.User;

import java.util.Collection;

/**
 * Created by root on 07.12.2016.
 */
public interface IQueryDao {

    Collection<User> doSearch (String query);

}
